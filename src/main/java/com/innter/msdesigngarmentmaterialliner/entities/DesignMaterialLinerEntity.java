package com.innter.msdesigngarmentmaterialliner.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "tb_design_materials_liners")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DesignMaterialLinerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fi_id")
    private Long id;

    @Column(name = "fc_name")
    private String name;

    @Column(name = "fc_description")
    private String description;

    @Column(name = "fi_width")
    private int width;

    @Column(name = "fc_color")
    private String color;

    @Column(name = "fc_code_color")
    private String codeColor;

    @ManyToOne
    @JoinColumn(name = "fi_provider")
    private ProviderEntity provider;

    @Column(name = "ft_image_path")
    private String imagePath;

    @Column(name = "fb_status")
    private Boolean status = true;

    @OneToMany(mappedBy = "designMaterialLinerComposition", cascade= CascadeType.ALL)
    private Set<DesignMaterialLinerCompositionEntity> designMaterialComposition;

}
