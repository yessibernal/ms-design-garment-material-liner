package com.innter.msdesigngarmentmaterialliner.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_desing_materials_liners_compositions")
public class DesignMaterialLinerCompositionEntity {

    @EmbeddedId
    private DesignMaterialLinerCompositionKey id = new DesignMaterialLinerCompositionKey();

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("idMaterialLiners")
    @JoinColumn(name = "fi_id_material_liners")
    private DesignMaterialLinerEntity designMaterialLinerComposition;

    @ManyToOne(fetch= FetchType.LAZY)
    @MapsId("idCompositions")
    @JoinColumn(name = "fi_id_compositions")
    private DesignCompositionEntity designComposition;

    @Column(name = "fi_percent")
    private int percent;
}
