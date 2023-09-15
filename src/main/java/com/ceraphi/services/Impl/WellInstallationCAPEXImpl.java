package com.ceraphi.services.Impl;

import com.ceraphi.dto.WellInstallationCAPEXDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.OutputCalculator;
import com.ceraphi.entities.WellInstallationCAPEX;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.WellInstallationCAPEXRepository;
import com.ceraphi.services.WellInstallationCAPEXService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class WellInstallationCAPEXImpl implements WellInstallationCAPEXService {
    private WellInstallationCAPEXRepository wellInstallationCAPEXRepository;
    private ModelMapper modelMapper;
    private GeneralInformationRepository generalInformationRepo;

    public WellInstallationCAPEXImpl(WellInstallationCAPEXRepository wellInstallationCAPEXRepository, ModelMapper modelMapper,GeneralInformationRepository generalInformationRepo) {
        this.wellInstallationCAPEXRepository = wellInstallationCAPEXRepository;
        this.modelMapper=modelMapper;
        this.generalInformationRepo=generalInformationRepo;
    }

    @Override
    public ApiResponseData<?> saveWellInstallationCAPEX(WellInstallationCAPEXDto wellInstallationCAPEXDto) {
        Long generalInfoId = wellInstallationCAPEXDto.getKey();

        GeneralInformation generalInformation = generalInformationRepo.findById(generalInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", generalInfoId));

        if (wellInstallationCAPEXRepository.existsByGeneralInformationId(generalInfoId)) {
            return ApiResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Data with id " + generalInfoId + " already exists")
                    .build();
        } else {
            WellInstallationCAPEX wellInstallationCAPEX = mapTOEntity(wellInstallationCAPEXDto);
            wellInstallationCAPEX.setGeneralInformation(generalInformation);
            WellInstallationCAPEX wellInstallationCAPEX1 = wellInstallationCAPEXRepository.save(wellInstallationCAPEX);
            return ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .build();
        }

    }

    @Override
    public void deleteByGeneralInformationId(Long id) {
        WellInstallationCAPEX wellInstallationCAPEX = wellInstallationCAPEXRepository.findByGeneralInformationId(id).orElse(null);
        if (wellInstallationCAPEX != null) {
            wellInstallationCAPEX.setIs_deleted(true);
            wellInstallationCAPEXRepository.save(wellInstallationCAPEX);
        }
    }

    public WellInstallationCAPEX mapTOEntity(WellInstallationCAPEXDto wellInstallationCAPEXDto) {
        WellInstallationCAPEX wellInstallationCAPEX = modelMapper.map(wellInstallationCAPEXDto, WellInstallationCAPEX.class);
        return wellInstallationCAPEX;
    }

    public WellInstallationCAPEXDto mapToDto(WellInstallationCAPEX wellInstallationCAPEX) {
        WellInstallationCAPEXDto wellInstallationCAPEXDto = modelMapper.map(wellInstallationCAPEX, WellInstallationCAPEXDto.class);
        return wellInstallationCAPEXDto;
    }
}
