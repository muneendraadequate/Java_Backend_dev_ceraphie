package com.ceraphi.services.Impl;

import com.ceraphi.controller.WellInformation.SubSurfaceController;
import com.ceraphi.dto.*;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.OperationsAndMaintenance;
import com.ceraphi.entities.SubSurface;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.SubSurfaceRepository;
import com.ceraphi.services.SubSurfaceService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubsurfaceImpl implements SubSurfaceService {

    private final SubSurfaceRepository subSurfaceRepo;
    private final GeneralInformationRepository generalInformationRepo;
    private final ModelMapper modelMapper;

    public SubsurfaceImpl(SubSurfaceRepository subSurfaceRepo, ModelMapper modelMapper, GeneralInformationRepository generalInformationRepo) {
        this.subSurfaceRepo = subSurfaceRepo;
        this.generalInformationRepo = generalInformationRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public ApiResponseData<SubSurfaceDto> saveSubSurfaceData(SubSurfaceDto subSurface) {
        try {
            GeneralInformation generalInformation1 = generalInformationRepo.findById(subSurface.getKey()).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", subSurface.getKey()));
            if (subSurfaceRepo.existsByGeneralInformationId(generalInformation1.getId())) {
                return ApiResponseData.<SubSurfaceDto>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with ID " + generalInformation1.getId() + " already exists.")
                        .build();
            }
            SubSurface subSurface1 = mapToEntity(subSurface);
            subSurface1.setGeneralInformation(generalInformation1);
            SubSurface save = subSurfaceRepo.save(subSurface1);

            SubSurfaceDto subSurfaceDto = mapToDto(save);
            subSurfaceDto.setKey(save.getGeneralInformation().getId());
            return ApiResponseData.<SubSurfaceDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("subsurface saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<SubSurfaceDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + subSurface.getKey() + " not found.")
                    .build();
        }
    }

@Override
public ApiResponseData<SubSurfaceDto> updateSubSurface(long id, SubSurfaceDto subSurfaceDto) {
    GeneralInformation generalInformation = generalInformationRepo.findById(subSurfaceDto.getKey())
            .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", subSurfaceDto.getKey()));

    SubSurface subSurface = subSurfaceRepo.findByGeneralInformationId(id).orElse(null);

    if (subSurface == null) {
        // If SubSurface does not exist, create a new one
        subSurface = new SubSurface();
        subSurface.setGeneralInformation(generalInformation);
    }

    // Update the fields of SubSurface based on the provided DTO
    subSurface.setRigDownEstimate(subSurfaceDto.getRigDownEstimate());
    subSurface.setSubsurfaceInstallationCost(subSurfaceDto.getSubsurfaceInstallationCost());
    subSurface.setRigUpEstimateWithCranes_etc(subSurfaceDto.getRigUpEstimateWithCranes_etc());
    subSurface.setWorkOverRigDeMobilisation(subSurfaceDto.getWorkOverRigDeMobilisation());
    subSurface.setWorkOverRigMobilisation(subSurfaceDto.getWorkOverRigMobilisation());

    SubSurface updatedSubSurface = subSurfaceRepo.save(subSurface);
    SubSurfaceDto subSurfaceDtoResponse = mapToDto(updatedSubSurface);

    return ApiResponseData.<SubSurfaceDto>builder()
            .status(HttpStatus.OK.value())
            .message("SubSurface information " + (subSurface == null ? "added" : "updated") + " successfully")
            .data(subSurfaceDtoResponse)
            .build();
}

    @Override
    public ApiResponseData<SubSurfaceDto> getSubSurfaceData(Long id) {
        boolean b = subSurfaceRepo.existsByGeneralInformationId(id);
        if (b == false) {
            SubSurfaceDto subSurfaceDto = new SubSurfaceDto();
            return ApiResponseData.<SubSurfaceDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(subSurfaceDto)
                    .build();
        } else {
            SubSurface subSurface = subSurfaceRepo.findByGeneralInformationId(id).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", id));
            SubSurfaceDto subSurfaceDto = mapToDto(subSurface);
            return ApiResponseData.<SubSurfaceDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(subSurfaceDto)
                    .build();
        }


    }

    @Override
    public void deleteByGeneralInformationId(Long id) {


        SubSurface subSurface = subSurfaceRepo.findByGeneralInformationId(id).orElse(null);
        if (subSurface != null) {
            subSurface.setIs_deleted(true);
            subSurfaceRepo.save(subSurface);
        }
    }



    public SubSurface mapToEntity(SubSurfaceDto SubSurface) {
        SubSurface subSurface = modelMapper.map(SubSurface, SubSurface.class);
        return subSurface;

    }

    public SubSurfaceDto mapToDto(SubSurface subSurface) {
        SubSurfaceDto subSurfaceDto = modelMapper.map(subSurface, SubSurfaceDto.class);
        return subSurfaceDto;
    }
}
