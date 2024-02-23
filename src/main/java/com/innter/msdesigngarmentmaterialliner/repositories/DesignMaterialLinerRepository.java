package com.innter.msdesigngarmentmaterialliner.repositories;

import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignMaterialLinerRepository extends JpaRepository<DesignMaterialLinerEntity, Long> {

    @Query("SELECT m FROM DesignMaterialLinerEntity m WHERE m.status = true")
    List<DesignMaterialLinerEntity> findDesignMaterialClothEntity(Pageable pageable);

    @Query("SELECT m FROM DesignMaterialLinerEntity m WHERE m.id = :id AND m.status = true")
    DesignMaterialLinerEntity findDesingMaterialLinerStatusById(Long id);
}
