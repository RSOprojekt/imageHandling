package si.fri.rso.imagehandling.models.converters;

import si.fri.rso.imagehandling.lib.ImageData;
import si.fri.rso.imagehandling.models.enteties.ImageHandlingEntity;

public class DataConverter {
    public static ImageData convertToDto(ImageHandlingEntity entity){

        ImageData dto = new ImageData();
        dto.setImageId(entity.getId());
        dto.setImageDescription(entity.getImageDescription());
        dto.setImageTitle(entity.getImageTitle());
        dto.setImageHeight(entity.getImageHeight());
        dto.setImageWidth(entity.getImageWidth());
        dto.setImageFileFormat(entity.getImageFileFormat());
        dto.setTimeCreated(entity.getTimeCreated());
        dto.setTimeChanged(entity.getTimeChanged());
        dto.setImageUrl(entity.getImageUrl());
        //dto,setImageBinary(entity.getImageBinary());
        return dto;
    }

    public static ImageHandlingEntity convertToEntity(ImageData dto){
        ImageHandlingEntity entity = new ImageHandlingEntity();
        entity.setId(dto.getImageId());
        entity.setImageDescription(dto.getImageDescription());
        entity.setImageTitle(dto.getImageTitle());
        entity.setImageHeight(dto.getImageHeight());
        entity.setImageWidth(dto.getImageWidth());
        entity.setImageFileFormat(dto.getImageFileFormat());
        entity.setTimeCreated(dto.getTimeCreated());
        entity.setTimeChanged(dto.getTimeChanged());
        entity.setImageUrl(dto.getImageUrl());
        //entity,setImageBinary(dto.getImageBinary());
        return  entity;
    }
}
