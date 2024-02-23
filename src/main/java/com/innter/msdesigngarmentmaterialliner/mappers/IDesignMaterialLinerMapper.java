package com.innter.msdesigngarmentmaterialliner.mappers;

import com.innter.msdesigngarmentmaterialliner.dtos.response.DesignMaterialLinerResponse;
import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerEntity;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface IDesignMaterialLinerMapper {

    DesignMaterialLinerResponse designMaterialLinerToDesignMaterialLinerResponse (DesignMaterialLinerEntity designMaterialLinerEntity);

}
