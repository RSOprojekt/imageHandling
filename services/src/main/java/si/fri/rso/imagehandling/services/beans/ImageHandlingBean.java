package si.fri.rso.imagehandling.services.beans;


import si.fri.rso.imagehandling.lib.ImageData;
import si.fri.rso.imagehandling.models.converters.DataConverter;
import si.fri.rso.imagehandling.models.enteties.ImageHandlingEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequestScoped
public class ImageHandlingBean {

    private final Logger logger = Logger.getLogger(ImageHandlingBean.class.getName());

    @Inject
    private EntityManager entMgr;

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

    public ImageData getImageData(Integer id){
        ImageHandlingEntity entity = entMgr.find(ImageHandlingEntity.class,id);
        if (entity == null) {
            logger.severe("Couldn't find image data with id " + id + "!");
            throw new NotFoundException();
        }
        return DataConverter.convertToDto(entity);
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
            logger.severe("Entity with id: " + entity.getId() + " not persisted");
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
            logger.severe("Update of entity with id: " + id + " was unsuccessful");
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
                logger.severe("Entity with id: " + id + " was not removed");
                rollbackT();
            }
        }
        else {
            return false;
        }
        return  true;
    }

}
