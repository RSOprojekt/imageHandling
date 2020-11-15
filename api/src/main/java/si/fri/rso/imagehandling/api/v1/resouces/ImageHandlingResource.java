package si.fri.rso.imagehandling.api.v1.resouces;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import si.fri.rso.imagehandling.lib.ImageData;
import si.fri.rso.imagehandling.services.beans.ImageHandlingBean;

import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
@Path("/gallery")
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

}
