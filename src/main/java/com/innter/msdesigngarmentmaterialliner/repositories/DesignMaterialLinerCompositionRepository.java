package com.innter.msdesigngarmentmaterialliner.repositories;

import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerCompositionEntity;
import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerCompositionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignMaterialLinerCompositionRepository extends JpaRepository<DesignMaterialLinerCompositionEntity, DesignMaterialLinerCompositionKey> {

    @Query("SELECT d FROM DesignMaterialLinerCompositionEntity d WHERE d.designMaterialLinerComposition.id = :id")
    List<DesignMaterialLinerCompositionEntity> findDesignMaterialLinerComposition (Long id);
}
