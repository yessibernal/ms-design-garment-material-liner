package com.innter.msdesigngarmentmaterialliner.mappers;

import com.innter.msdesigngarmentmaterialliner.dtos.DesignCompositionDto;
import com.innter.msdesigngarmentmaterialliner.dtos.response.DesignMaterialLinerResponse;
import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerEntity;
import com.innter.msdesigngarmentmaterialliner.repositories.DesignCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DesignMaterialLinerMapper implements IDesignMaterialLinerMapper {

    @Autowired
    private DesignCompositionRepository designCompositionRepository;

    @Override
    public DesignMaterialLinerResponse designMaterialLinerToDesignMaterialLinerResponse(DesignMaterialLinerEntity designMaterialLinerEntity) {
        List<DesignCompositionDto> designCompositionDtos = new ArrayList<>();
        DesignCompositionDto designCompositionDto = new DesignCompositionDto();
        designMaterialLinerEntity.getDesignMaterialComposition().forEach(designLiner -> {
            designCompositionRepository.findById(designLiner.getDesignMaterialLinerComposition().getId());
            designCompositionDto.setId_composition(designLiner.getDesignComposition().getId());
            designCompositionDto.setPercent(designLiner.getPercent());
        });
        designCompositionDtos.add(designCompositionDto);
        DesignMaterialLinerResponse designMaterialLinerResponse = getDesignMaterialLinerResponse(designMaterialLinerEntity, designCompositionDtos);
        return designMaterialLinerResponse;
    }

    private DesignMaterialLinerResponse getDesignMaterialLinerResponse(DesignMaterialLinerEntity designMaterialLinerEntity, List<DesignCompositionDto> designCompositionDtos) {
        DesignMaterialLinerResponse designMaterialLinerResponse = new DesignMaterialLinerResponse();
        designMaterialLinerResponse.setId(designMaterialLinerEntity.getId());
        designMaterialLinerResponse.setDesignComposition(designCompositionDtos);
        designMaterialLinerResponse.setName(designMaterialLinerEntity.getName());
        designMaterialLinerResponse.setDescription(designMaterialLinerEntity.getDescription());
        designMaterialLinerResponse.setWidth(designMaterialLinerEntity.getWidth());
        designMaterialLinerResponse.setColor(designMaterialLinerEntity.getColor());
        designMaterialLinerResponse.setCodeColor(designMaterialLinerEntity.getCodeColor());
        designMaterialLinerResponse.setProviderId(designMaterialLinerEntity.getProvider().getId());
        designMaterialLinerResponse.setImagePath(designMaterialLinerEntity.getImagePath());
        return designMaterialLinerResponse;
    }
}
