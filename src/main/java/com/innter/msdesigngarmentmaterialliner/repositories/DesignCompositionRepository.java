package com.innter.msdesigngarmentmaterialliner.repositories;

import com.innter.msdesigngarmentmaterialliner.entities.DesignCompositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignCompositionRepository extends JpaRepository<DesignCompositionEntity, Long> {
}
