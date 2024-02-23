package com.innter.msdesigngarmentmaterialliner.services.IMPL;

import com.innter.msdesigngarmentmaterialliner.dtos.DesignCompositionDto;
import com.innter.msdesigngarmentmaterialliner.dtos.DesignMaterialLinerEditedDto;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignMaterialLinerRequest;
import com.innter.msdesigngarmentmaterialliner.dtos.request.DesignRequestStatus;
import com.innter.msdesigngarmentmaterialliner.dtos.response.DesignMaterialLinerResponse;
import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerCompositionEntity;
import com.innter.msdesigngarmentmaterialliner.entities.DesignMaterialLinerEntity;
import com.innter.msdesigngarmentmaterialliner.entities.ProviderEntity;
import com.innter.msdesigngarmentmaterialliner.exceptions.BadRequestTextil;
import com.innter.msdesigngarmentmaterialliner.exceptions.NotFoundTextil;
import com.innter.msdesigngarmentmaterialliner.mappers.DesignMaterialLinerMapper;
import com.innter.msdesigngarmentmaterialliner.repositories.DesignCompositionRepository;
import com.innter.msdesigngarmentmaterialliner.repositories.DesignMaterialLinerCompositionRepository;
import com.innter.msdesigngarmentmaterialliner.repositories.DesignMaterialLinerRepository;
import com.innter.msdesigngarmentmaterialliner.repositories.ProviderRepository;
import com.innter.msdesigngarmentmaterialliner.services.IDesignMaterialLinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DesignMaterialLinerService implements IDesignMaterialLinerService {
    @Autowired
    private DesignMaterialLinerRepository designMaterialLinerRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private DesignCompositionRepository designCompositionRepository;
    @Autowired
    private DesignMaterialLinerCompositionRepository designMaterialLinerCompositionRepository;
    @Autowired
    private DesignMaterialLinerMapper designMaterialLinerMapper;

    @Transactional
    @Override
    public DesignMaterialLinerResponse saveDesignMaterialLiner(DesignMaterialLinerRequest designMaterialLinerRequest) {
        ProviderEntity providerEntity = providerRepository.findProviderStatusById(designMaterialLinerRequest.getProviderId());

        try {
            DesignMaterialLinerEntity designMaterialLiner = getDesignMaterialLinerEntity(designMaterialLinerRequest, providerEntity);
            designMaterialLinerRepository.save(designMaterialLiner);
            designMaterialLinerRequest.getDesignComposition().forEach(composition -> designCompositionRepository.findById(composition.getId_composition()).ifPresent(designComposition -> {
                DesignMaterialLinerCompositionEntity designMaterialLinerComposition = new DesignMaterialLinerCompositionEntity();
                designMaterialLinerComposition.setDesignComposition(designComposition);
                designMaterialLinerComposition.setDesignMaterialLinerComposition(designMaterialLiner);
                designMaterialLinerComposition.setPercent(composition.getPercent());
                designMaterialLinerCompositionRepository.save(designMaterialLinerComposition);
            }));
            DesignMaterialLinerResponse designMaterialLinerResponse = getDesignMaterialLinerResponse(designMaterialLinerRequest, designMaterialLiner, providerEntity);
            return designMaterialLinerResponse;
        } catch (Exception e) {
            log.error("error: {}", e);
            throw new BadRequestTextil("P-400", HttpStatus.BAD_REQUEST, "El Material de los forros no se pudo crear.");
        }
    }

    @Override
    public List<DesignMaterialLinerResponse> getDesignMaterialLiner(Pageable pageable) {
        try {
            List<DesignMaterialLinerEntity> designMaterialLinerEntities = designMaterialLinerRepository.findDesignMaterialClothEntity(pageable);
            List<DesignMaterialLinerResponse> designMaterialLinerResponses = new ArrayList<>();
            designMaterialLinerEntities.stream().forEach(designMaterialLiner -> designMaterialLinerResponses.add(designMaterialLinerMapper.designMaterialLinerToDesignMaterialLinerResponse(designMaterialLiner)));
            return designMaterialLinerResponses;
        } catch (Exception e) {
            log.error("error: {}", e);
            throw new BadRequestTextil("P-400", HttpStatus.BAD_REQUEST, "El Material de los forros no se pudieron encontrar.");
        }
    }

    @Override
    public DesignMaterialLinerResponse editedDesignMaterialLinerByStatus(DesignRequestStatus designRequestStatus, Long id) {
        try {
            DesignMaterialLinerEntity designMaterialLiner = designMaterialLinerRepository.findDesingMaterialLinerStatusById(id);
            designMaterialLiner.setStatus(designRequestStatus.getStatus());
            designMaterialLinerRepository.save(designMaterialLiner);
            return designMaterialLinerMapper.designMaterialLinerToDesignMaterialLinerResponse(designMaterialLiner);
        } catch (Exception e) {
            log.error("error: {}", e);
            throw new BadRequestTextil("P-400", HttpStatus.BAD_REQUEST, "El Material de los forros no fue encontrado.");
        }
    }

    @Transactional
    @Override
    public DesignMaterialLinerResponse editedDesignMaterialLiner(DesignMaterialLinerEditedDto designMaterialLinerEditedDto, Long id) {
        ProviderEntity providerEntity = providerRepository.findProviderStatusById(designMaterialLinerEditedDto.getProviderId());
        List<Long> listCompositionRequest = new ArrayList<>();
        List<Long> listCompositionUpdate = new ArrayList<>();
        List<Long> listCompositionDelete = new ArrayList<>();
        List<Long> listCompositionAdd = new ArrayList<>();

        try {
            DesignMaterialLinerEntity designMaterialLiner = findDesignMaterialLinerById(designMaterialLinerRepository.findById(id));
            designMaterialLiner.setId(id);
            designMaterialLiner.setName(designMaterialLiner.getName());
            designMaterialLiner.setDescription(designMaterialLinerEditedDto.getDescription());
            designMaterialLiner.setWidth(designMaterialLinerEditedDto.getWidth());
            designMaterialLiner.setColor(designMaterialLinerEditedDto.getColor());
            designMaterialLiner.setCodeColor(designMaterialLinerEditedDto.getCodeColor());
            designMaterialLiner.setProvider(providerEntity);
            designMaterialLiner.setImagePath(designMaterialLinerEditedDto.getImagePath());
            designMaterialLinerRepository.save(designMaterialLiner);
            designMaterialLinerEditedDto.getDesignComposition().forEach(composition -> listCompositionRequest.add(composition.getId_composition()));

            List<Long> listCompositionExist = getListCompositionAdd(id);
            listCompositionRequest.forEach(listComposition -> {
                if (!listCompositionExist.contains(listComposition)) {
                    listCompositionAdd.add(listComposition);
                }
            });

            designMaterialLinerEditedDto.getDesignComposition().forEach(designComposition -> listCompositionAdd.forEach(listSave -> {
                if (designComposition.getId_composition().equals(listSave)) {
                    designCompositionRepository.findById(listSave).ifPresent(newListComposition -> {
                        DesignMaterialLinerCompositionEntity designMaterialLinerComposition = new DesignMaterialLinerCompositionEntity();
                        designMaterialLinerComposition.setDesignComposition(newListComposition);
                        designMaterialLinerComposition.setDesignMaterialLinerComposition(designMaterialLiner);
                        designMaterialLinerComposition.setPercent(designComposition.getPercent());
                        designMaterialLinerCompositionRepository.save(designMaterialLinerComposition);
                    });
                }
            }));

            listCompositionRequest.forEach(listComposition -> {
                if (listCompositionExist.contains(listComposition)) {
                    listCompositionUpdate.add(listComposition);
                }
            });

            List<DesignMaterialLinerCompositionEntity> desingMaterialLinerCompositionUpdateList = designMaterialLinerCompositionRepository.findDesignMaterialLinerComposition(id);
            desingMaterialLinerCompositionUpdateList.forEach(designLinerUpdate -> designMaterialLinerEditedDto.getDesignComposition().forEach(composition -> {
                listCompositionRequest.add(composition.getId_composition());
                DesignMaterialLinerCompositionEntity desingMaterialLinerComposition = new DesignMaterialLinerCompositionEntity();
                desingMaterialLinerComposition.setId(designLinerUpdate.getId());
                desingMaterialLinerComposition.setDesignMaterialLinerComposition(designLinerUpdate.getDesignMaterialLinerComposition());
                desingMaterialLinerComposition.setDesignComposition(designLinerUpdate.getDesignComposition());

                if (composition.getId_composition() == designLinerUpdate.getDesignComposition().getId()) {
                    desingMaterialLinerComposition.setPercent(composition.getPercent());
                    designMaterialLinerCompositionRepository.save(desingMaterialLinerComposition);
                }
            }));

            listCompositionExist.forEach(listComposition -> {
                if (!listCompositionUpdate.contains(listComposition)) {
                    listCompositionDelete.add(listComposition);
                }
            });

            List<DesignMaterialLinerCompositionEntity> desingMaterialLinerCompositionList = designMaterialLinerCompositionRepository.findDesignMaterialLinerComposition(id);
            desingMaterialLinerCompositionList.forEach(compositionGroup -> listCompositionDelete.forEach(listDeleteComposition -> {
                if (compositionGroup.getDesignComposition().getId() == listDeleteComposition) {
                    designMaterialLinerCompositionRepository.delete(compositionGroup);
                }
            }));

            DesignMaterialLinerResponse designMaterialLinerResponse = getDesignMaterialLinerResponse(designMaterialLinerEditedDto, designMaterialLiner);
            return designMaterialLinerResponse;
        } catch (Exception e) {
            log.error("error: {}", e);
            throw new BadRequestTextil("P-400", HttpStatus.BAD_REQUEST, "El Material de los forros no fue encontrado.");
        }
    }

    private static DesignMaterialLinerEntity getDesignMaterialLinerEntity(DesignMaterialLinerRequest designMaterialLinerRequest, ProviderEntity providerEntity) {
        DesignMaterialLinerEntity designMaterialLiner = new DesignMaterialLinerEntity();
        designMaterialLiner.setName(designMaterialLinerRequest.getName());
        designMaterialLiner.setDescription(designMaterialLinerRequest.getDescription());
        designMaterialLiner.setWidth(designMaterialLinerRequest.getWidth());
        designMaterialLiner.setColor(designMaterialLinerRequest.getColor());
        designMaterialLiner.setCodeColor(designMaterialLinerRequest.getCodeColor());
        designMaterialLiner.setProvider(providerEntity);
        designMaterialLiner.setImagePath(designMaterialLinerRequest.getImagePath());
        return designMaterialLiner;
    }

    private static DesignMaterialLinerResponse getDesignMaterialLinerResponse(DesignMaterialLinerRequest designMaterialLinerRequest, DesignMaterialLinerEntity designMaterialLiner, ProviderEntity providerEntity) {
        DesignMaterialLinerResponse designMaterialLinerResponse = new DesignMaterialLinerResponse();
        designMaterialLinerResponse.setId(designMaterialLiner.getId());
        designMaterialLinerResponse.setDesignComposition(designMaterialLinerRequest.getDesignComposition());
        designMaterialLinerResponse.setName(designMaterialLinerRequest.getName());
        designMaterialLinerResponse.setDescription(designMaterialLinerRequest.getDescription());
        designMaterialLinerResponse.setWidth(designMaterialLinerRequest.getWidth());
        designMaterialLinerResponse.setColor(designMaterialLinerRequest.getColor());
        designMaterialLinerResponse.setCodeColor(designMaterialLinerRequest.getCodeColor());
        designMaterialLinerResponse.setProviderId(providerEntity.getId());
        designMaterialLinerResponse.setImagePath(designMaterialLinerRequest.getImagePath());
        return designMaterialLinerResponse;
    }

    private static DesignMaterialLinerEntity findDesignMaterialLinerById(Optional<DesignMaterialLinerEntity> optionalDesignMaterialLiner) {
        return optionalDesignMaterialLiner.orElseThrow(() -> new NotFoundTextil("P-404", HttpStatus.NOT_FOUND, "El Material de los forros no fue encontrado."));
    }
    private List<Long> getListCompositionAdd(Long id) {
        List<DesignCompositionDto> designCompositionDtos = new ArrayList<>();
        List<Long> listCompositionExist = new ArrayList<>();
        List<DesignMaterialLinerCompositionEntity> desingMaterialLinerCompositionEntities = designMaterialLinerCompositionRepository.findDesignMaterialLinerComposition(id);
        desingMaterialLinerCompositionEntities.forEach(compositionGroup -> {
            DesignCompositionDto designCompositionDto = new DesignCompositionDto();
            designCompositionDto.setId_composition(compositionGroup.getDesignComposition().getId());
            designCompositionDto.setPercent(compositionGroup.getPercent());
            designCompositionDtos.add(designCompositionDto);
            listCompositionExist.add(designCompositionDto.getId_composition());
        });
        return listCompositionExist;
    }
    private static DesignMaterialLinerResponse getDesignMaterialLinerResponse(DesignMaterialLinerEditedDto designMaterialLinerEditedDto, DesignMaterialLinerEntity designMaterialLiner) {
        DesignMaterialLinerResponse designMaterialLinerResponse = new DesignMaterialLinerResponse();
        designMaterialLinerResponse.setId(designMaterialLiner.getId());
        designMaterialLinerResponse.setName(designMaterialLiner.getName());
        designMaterialLinerResponse.setDescription(designMaterialLiner.getDescription());
        designMaterialLinerResponse.setWidth(designMaterialLiner.getWidth());
        designMaterialLinerResponse.setColor(designMaterialLiner.getColor());
        designMaterialLinerResponse.setCodeColor(designMaterialLiner.getCodeColor());
        designMaterialLinerResponse.setProviderId(designMaterialLiner.getProvider().getId());
        designMaterialLinerResponse.setImagePath(designMaterialLiner.getImagePath());
        designMaterialLinerResponse.setDesignComposition(designMaterialLinerEditedDto.getDesignComposition());
        return designMaterialLinerResponse;
    }
}
