package com.innter.msdesigngarmentmaterialliner.services;

import com.innter.msdesigngarmentmaterialliner.dtos.DesignMaterialLinerEditedDto;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignMaterialLinerRequest;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignRequestStatus;
import com.innter.msdesigngarmentmaterialliner.dtos.response.DesignMaterialLinerResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IDesignMaterialLinerService {

    DesignMaterialLinerResponse saveDesignMaterialLiner (DesignMaterialLinerRequest designMaterialLinerRequest);

    List<DesignMaterialLinerResponse> getDesignMaterialLiner (Pageable pageable);

    DesignMaterialLinerResponse editedDesignMaterialLinerByStatus (DesignRequestStatus designRequestStatus, Long id);

    DesignMaterialLinerResponse editedDesignMaterialLiner (DesignMaterialLinerEditedDto designMaterialLinerEditedDto, Long id);
}
