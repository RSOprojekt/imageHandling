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




}
