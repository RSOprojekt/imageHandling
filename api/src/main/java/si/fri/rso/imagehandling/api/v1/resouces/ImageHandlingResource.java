package si.fri.rso.imagehandling.api.v1.resouces;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import si.fri.rso.imagehandling.lib.ImageData;
import si.fri.rso.imagehandling.services.beans.ImageHandlingBean;

import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/images")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@CrossOrigin(supportedMethods = "GET, POST, HEAD, DELETE, OPTIONS")
public class ImageHandlingResource {

    private final Logger logger = Logger.getLogger(ImageHandlingResource.class.getName());

    @Inject
    private ImageHandlingBean imageHandlingBean;

    @Context
    protected UriInfo uriInfo;

    @GET
    public Response getImageData(){
        List<ImageData> imageData = imageHandlingBean.getImageDataFilter(uriInfo);
        return Response.status(Response.Status.OK).entity(imageData).build();
    }

    @GET
    @Path("/{imageDataId}")
    public Response getImageData(@PathParam("imageDataId") Integer imageDataId){
        ImageData data = imageHandlingBean.getImageData(imageDataId);
        if (data == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return  Response.status(Response.Status.OK).entity(data).build();
    }

    @PUT
    @Path("{imageDataId}")
    public Response putImageData(@PathParam("imageDataId") Integer imageDataId, ImageData data){
        data = imageHandlingBean.updateImageData(imageDataId, data);
        if (data == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
            return Response.status(Response.Status.NOT_MODIFIED).build();
    }

    @POST
    public Response createImageData(ImageData data){
        if (data.getImageUrl() == null || data.getImageTitle() == null || data.getImageDescription() == null){
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            data = imageHandlingBean.createImageData(data);
        }
        return Response.status(Response.Status.OK).entity(data).build();
    }

    @DELETE
    @Path("{imageDataId}")
    public Response deleteImageData(@PathParam("imageDataId") Integer imageDataId){
        boolean delSuccess = imageHandlingBean.deleteImageData(imageDataId);
        if (delSuccess) return  Response.status(Response.Status.NO_CONTENT).build();
        else return Response.status(Response.Status.NOT_FOUND).build();
    }
}
