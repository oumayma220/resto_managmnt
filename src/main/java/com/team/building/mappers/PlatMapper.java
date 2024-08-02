package com.team.building.mappers;

import com.team.building.dtos.PlatDto;
import com.team.building.model.Plat;

public class PlatMapper {
    public void mapFromRest(PlatDto source , Plat target){
        target.setId(source.getId());
        if(source.getPlatName() != null)
            target.setPlatName(source.getPlatName());
        if(source.getDescription() != null)
            target.setDescription(source.getDescription());
        if(source.getPrice() != null)
            target.setPrice(source.getPrice());
        if(source.getImage() != null)
            target.setImage(source.getImage());
    }

    public void mapToRest(Plat source , PlatDto target){
        target.setId(source.getId());
        target.setPlatName(source.getPlatName());
        target.setDescription(source.getDescription());
        target.setPrice(source.getPrice());
        target.setImage(source.getImage());
    }
}
