package com.innter.msdesigngarmentmaterialliner.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.innter.msdesigngarmentmaterialliner.dtos.DesignCompositionDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignMaterialLinerRequest {

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
