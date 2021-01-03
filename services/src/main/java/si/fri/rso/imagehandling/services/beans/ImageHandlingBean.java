package si.fri.rso.imagehandling.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;


import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.annotation.Timed;
import si.fri.rso.imagehandling.lib.ImageData;
import si.fri.rso.imagehandling.models.converters.DataConverter;
import si.fri.rso.imagehandling.models.enteties.ImageHandlingEntity;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriInfo;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class ImageHandlingBean {

    private final Logger logger = Logger.getLogger(ImageHandlingBean.class.getName());

    @Inject
    private ImageHandlingBean imageHandlingBeanProxy;

    @Inject
    private EntityManager entMgr;

    private Client httpClient;
    private String baseUrl;

    @PostConstruct
    private void init(){
        httpClient = ClientBuilder.newClient();
        baseUrl ="http://localhost:8081";    //change according to your microservices
    }

    private void beginT(){
        if (entMgr.getTransaction().isActive())entMgr.getTransaction().begin();
    }

    private void commitT(){
        if (entMgr.getTransaction().isActive()) entMgr.getTransaction().commit();
    }

    private void rollbackT(){
        if (entMgr.getTransaction().isActive()) entMgr.getTransaction().rollback();
    }

    public List<ImageData> getImageData(){
        TypedQuery<ImageHandlingEntity> q =entMgr.createNamedQuery("ImageHandlingEntity.getAll", ImageHandlingEntity.class);
        List<ImageHandlingEntity> results = q.getResultList();
        return results.stream().map(DataConverter::convertToDto).collect(Collectors.toList());
    }

    @Timed
    public List<ImageData> getImageDataFilter(UriInfo uriInfo){
        QueryParameters q = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).build();
        return JPAUtils.queryEntities(entMgr,ImageHandlingEntity.class,q).stream().map(DataConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public ImageData getImageData(Integer id){
        ImageHandlingEntity entity = entMgr.find(ImageHandlingEntity.class,id);
        if (entity == null) {
            throw new NotFoundException();
        }

        ImageData imageData =DataConverter.convertToDto(entity);
        imageData.setCommentNumber(imageHandlingBeanProxy.getCommentNumber(id));
        //TODO make your own other service and get data like the comment above, change the baseUrl too.

        return imageData;
    }

    public ImageData createImageData(ImageData data){
        ImageHandlingEntity entity = DataConverter.convertToEntity(data);
        try {
            beginT();
            entMgr.persist(entity);
            commitT();
        }
        catch (Exception e){
            rollbackT();
        }
        if (entity.getId() == null) {
            throw new RuntimeException();
        }
        return DataConverter.convertToDto(entity);
    }

    public ImageData updateImageData(Integer id, ImageData data){

        ImageHandlingEntity entity = DataConverter.convertToEntity(data);
        ImageHandlingEntity found = entMgr.find(ImageHandlingEntity.class, id);
        if (found == null) return null;
        try {
            beginT();
            entity.setId(found.getId());
            entity = entMgr.merge(entity);
            commitT();
        }
        catch (Exception e){
            rollbackT();
        }
        return DataConverter.convertToDto(entity);
    }

    public boolean deleteImageData(Integer id){
        ImageHandlingEntity entity = entMgr.find(ImageHandlingEntity.class, id);
        if (entity != null) {
            try {
                beginT();
                entMgr.remove(entity);
                commitT();
            }
            catch (Exception e){
                rollbackT();
            }
        }
        else {
            return false;
        }
        return  true;
    }

    @Timeout(value = 2, unit = ChronoUnit.SECONDS)
    @CircuitBreaker(requestVolumeThreshold = 3)
    @Fallback(fallbackMethod = "getCommentNumberFallback")
    public Integer getCommentNumber(Integer imgId) {
        logger.info("calling comment service, getting comment number");

        try {
            return httpClient.target(baseUrl = "/v1/comments/count").queryParam("imageId", imgId)
                    .request().get(new GenericType<Integer>(){
                    });
        } catch (WebApplicationException | ProcessingException e){
            logger.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
    }

    public Integer getCommentNumberFallback(Integer imgId){
        return null;
    }

}
