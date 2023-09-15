package com.ceraphi.services.Impl;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.SubSurfaceDto;
import com.ceraphi.dto.SurfaceEquipmentDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.SubSurface;
import com.ceraphi.entities.SurfaceEquipment;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.SurfaceEquipmentRepository;
import com.ceraphi.services.SurfaceEquipmentService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SurfaceEquipmentServiceImpl implements SurfaceEquipmentService {
    public final SurfaceEquipmentRepository surfaceEquipRepo;
    public ModelMapper modelMapper;
    public final GeneralInformationRepository generalInformationRepo;

    public SurfaceEquipmentServiceImpl (SurfaceEquipmentRepository surfaceEquipRepo,ModelMapper modelMapper,GeneralInformationRepository generalInformationRepo){
        this.surfaceEquipRepo=surfaceEquipRepo;
        this.modelMapper=modelMapper;
        this.generalInformationRepo=generalInformationRepo;

    }

        @Override
        public ApiResponseData<SurfaceEquipmentDto> saveTheSurfaceEquip(SurfaceEquipmentDto surfaceEquip) {
        try{
            GeneralInformation generalInformation1 = generalInformationRepo.findById(surfaceEquip.getKey()).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", surfaceEquip.getKey()));
            if(surfaceEquipRepo.existsByGeneralInformationId(generalInformation1.getId())){
                return ApiResponseData.<SurfaceEquipmentDto>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with ID " + generalInformation1.getId() + " already exists.")
                        .build();
            }

            SurfaceEquipment surfaceEquipment = mapToEntity(surfaceEquip);
            surfaceEquipment.setGeneralInformation(generalInformation1);
            SurfaceEquipment surfaceEquip1 = surfaceEquipRepo.save(surfaceEquipment);
            SurfaceEquipmentDto surfaceEquipmentDto = mapToDto(surfaceEquip1);
            surfaceEquipmentDto.setKey(surfaceEquip1.getGeneralInformation().getId());
               return ApiResponseData.<SurfaceEquipmentDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("surfaceEquipment saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<SurfaceEquipmentDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + surfaceEquip.getKey() + " not found.")
                    .build();
        }

    }



    @Override
    public ApiResponseData<SurfaceEquipmentDto> updateSurfaceEquipment(long id, SurfaceEquipmentDto surfaceEquipmentDto) {
        GeneralInformation generalInformation = generalInformationRepo.findById(surfaceEquipmentDto.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", surfaceEquipmentDto.getKey()));

        SurfaceEquipment surfaceEquipment = surfaceEquipRepo.findByGeneralInformationId(id).orElse(null);

        if (surfaceEquipment == null) {
            // If SurfaceEquipment does not exist, create a new one
            surfaceEquipment = new SurfaceEquipment();
            surfaceEquipment.setGeneralInformation(generalInformation);
        }

        // Update the fields of SurfaceEquipment based on the provided DTO
        surfaceEquipment.setContingency(surfaceEquipmentDto.getContingency());
        surfaceEquipment.setSurfaceInstallationCost(surfaceEquipmentDto.getSurfaceInstallationCost());
        surfaceEquipment.setContingency_Amount(surfaceEquipmentDto.getContingency_Amount());
        surfaceEquipment.setHp_installation_cost(surfaceEquipmentDto.getHp_installation_cost());
        surfaceEquipment.setInstallationCost(surfaceEquipmentDto.getInstallationCost());
        surfaceEquipment.setInstalledHpCapacity(surfaceEquipmentDto.getInstalledHpCapacity());

        SurfaceEquipment updatedSurfaceEquipment = surfaceEquipRepo.save(surfaceEquipment);
        SurfaceEquipmentDto surfaceEquipmentDtoResponse = mapToDto(updatedSurfaceEquipment);

        return ApiResponseData.<SurfaceEquipmentDto>builder()
                .status(HttpStatus.OK.value())
                .message("SurfaceEquipment information " + (surfaceEquipment == null ? "added" : "updated") + " successfully")
                .data(surfaceEquipmentDtoResponse)
                .build();
    }


    @Override
    public ApiResponseData<SurfaceEquipmentDto> getDataById(Long id) {
        boolean b = surfaceEquipRepo.existsByGeneralInformationId(id);
        if (b == false) {
            SurfaceEquipmentDto surfaceEquipmentDto = new SurfaceEquipmentDto();
            return ApiResponseData.<SurfaceEquipmentDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(surfaceEquipmentDto)
                    .build();
        } else {
            SurfaceEquipment surfaceEquipment = surfaceEquipRepo.findByGeneralInformationId(id).orElseThrow(() -> new ResourceNotFoundException("general", "id", id));
            SurfaceEquipmentDto surfaceEquipmentDto = mapToDto(surfaceEquipment);
            return ApiResponseData.<SurfaceEquipmentDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(surfaceEquipmentDto)
                    .build();
        }


    }

    @Override
    public void deleteByGeneralInformationId(Long id) {

        SurfaceEquipment surfaceEquipment = surfaceEquipRepo.findByGeneralInformationId(id).orElse(null);
        if (surfaceEquipment != null) {
            surfaceEquipment.setIs_deleted(true);
            surfaceEquipRepo.save(surfaceEquipment);
        }
    }




    public SurfaceEquipment mapToEntity(SurfaceEquipmentDto surfaceEquipmentDto) {
        SurfaceEquipment entity = modelMapper.map(surfaceEquipmentDto, SurfaceEquipment.class);
        return entity ;

    }

    public SurfaceEquipmentDto mapToDto(SurfaceEquipment surfaceEquipment) {
        SurfaceEquipmentDto dto = modelMapper.map(surfaceEquipment, SurfaceEquipmentDto.class);
        return dto;
    }


}
