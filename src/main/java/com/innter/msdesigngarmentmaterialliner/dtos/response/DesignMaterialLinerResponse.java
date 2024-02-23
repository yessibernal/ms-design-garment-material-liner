package com.innter.msdesigngarmentmaterialliner.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innter.msdesigngarmentmaterialliner.dtos.DesignCompositionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesignMaterialLinerResponse {

    @JsonProperty("designMaterialLinerId")
    private Long id;

    @JsonProperty("designCompositions")
    private List<DesignCompositionDto> designComposition;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("width")
    private int width;

    @JsonProperty("color")
    private String color;

    @JsonProperty("codeColor")
    private String codeColor;

    @JsonProperty("providerId")
    private Long providerId;

    @JsonProperty("imagePath")
    private String imagePath;

}
