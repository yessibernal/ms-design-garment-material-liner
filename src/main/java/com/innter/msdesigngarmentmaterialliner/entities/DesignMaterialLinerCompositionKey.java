package com.innter.msdesigngarmentmaterialliner.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class DesignMaterialLinerCompositionKey {

    @Column(name = "fi_id_material_liners", nullable = false)
    private Long idMaterialLiners;

    @Column(name = "fi_id_compositions", nullable = false)
    private Long idCompositions;
}
