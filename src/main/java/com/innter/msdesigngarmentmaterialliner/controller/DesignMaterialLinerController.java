package com.innter.msdesigngarmentmaterialliner.controller;

import com.innter.msdesigngarmentmaterialliner.dtos.DesignMaterialLinerEditedDto;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignMaterialLinerRequest;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignRequestStatus;
import com.innter.msdesigngarmentmaterialliner.services.IDesignMaterialLinerService;
import com.innter.msdesigngarmentmaterialliner.utils.ResponseUtils;
import com.innter.msdesigngarmentmaterialliner.utils.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/designMaterialLiner")
public class DesignMaterialLinerController {
    @Autowired
    private IDesignMaterialLinerService designMaterialLinerService;

    @Autowired
    private ResponseUtils responseUtils;


    @PreAuthorize("hasAnyRole ('ADMIN','DESIGN_WRITE')")
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDesignMaterialLiner(@RequestBody DesignMaterialLinerRequest designMaterialLinerRequest) {
        SuccessResponse responseSuccess = responseUtils.successResponseCreate(designMaterialLinerService.saveDesignMaterialLiner(designMaterialLinerRequest),

                "El Material de los forros se creo de manera exitosa.");
        return new ResponseEntity<>(responseSuccess, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole ('DESIGN_READ','ADMIN')")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDesignMaterialLiner(@RequestParam(required = false) Integer pageIndex,
                                                    @RequestParam(required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        SuccessResponse responseSuccess = responseUtils.successResponseOK(designMaterialLinerService.getDesignMaterialLiner(pageable),
                "Los Materiales de los forros se encontraron correctamente.");
        return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole ('ADMIN','DESIGN_WRITE')")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDesignMaterialLinerByStatus(@RequestBody DesignRequestStatus designRequestStatus, @PathVariable long id) {
        SuccessResponse responseSuccess = responseUtils.successResponseOK(designMaterialLinerService.editedDesignMaterialLinerByStatus(designRequestStatus,id),
                "El Material de los forros con el id:" + id + " cambio su status correctamente.");
        return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole ('ADMIN','DESIGN_WRITE')")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDesignMaterialLiner(@RequestBody DesignMaterialLinerEditedDto designMaterialLinerEditedDto, @PathVariable long id) {
        SuccessResponse responseSuccess = responseUtils.successResponseOK(
                designMaterialLinerService.editedDesignMaterialLiner(designMaterialLinerEditedDto, id),
                "El Material de forros con el id:" + id + " se actualizo correctamente.");
        return new ResponseEntity<>(responseSuccess, HttpStatus.OK);
    }
}
