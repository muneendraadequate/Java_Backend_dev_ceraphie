package com.ceraphi.services.Impl;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.OperationsAndMaintenanceDto;
import com.ceraphi.dto.OutputCalculatorDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.OperationsAndMaintenance;
import com.ceraphi.entities.OutputCalculator;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.OutPutCalculatorRepository;
import com.ceraphi.services.OutputCalculatorService;
import com.ceraphi.utils.ApiResponseData;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OutputCalculatorServiceImpl implements OutputCalculatorService {
    private OutPutCalculatorRepository outPutCalculatorRepo;
   private GeneralInformationRepository generalInformationRepository;
    private ModelMapper modelMapper;

    public OutputCalculatorServiceImpl(GeneralInformationRepository generalInformationRepository,OutPutCalculatorRepository outPutCalculatorRepo, ModelMapper modelMapper) {
        this.outPutCalculatorRepo = outPutCalculatorRepo;
        this.modelMapper = modelMapper;
        this.generalInformationRepository=generalInformationRepository;
    }

    @Override
    public ApiResponseData<?> saveOutPutCalculator(OutputCalculatorDto outputCalculatorDto) {
            Long generalInfoId = outputCalculatorDto.getKey();

            GeneralInformation generalInformation = generalInformationRepository.findById(generalInfoId)
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", generalInfoId));

            if (outPutCalculatorRepo.existsByGeneralInformationId(generalInfoId)) {
                return ApiResponseData.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with id " + generalInfoId + " already exists")
                        .build();
            } else {
                OutputCalculator outputCalculator = mapToEntity(outputCalculatorDto);
                outputCalculator.setGeneralInformation(generalInformation);
                outPutCalculatorRepo.save(outputCalculator);

                return ApiResponseData.builder()
                        .status(HttpStatus.OK.value())
                        .build();
            }
        }
    @Override
    public void deleteByGeneralInformationId(Long id) {
        OutputCalculator outputCalculator = outPutCalculatorRepo.findByGeneralInformationId(id).orElse(null);
        if (outputCalculator != null) {
            outputCalculator.setIs_deleted(true);
            outPutCalculatorRepo.save(outputCalculator);
        }
    }

    public OutputCalculator mapToEntity(OutputCalculatorDto outputCalculatorDto) {
        OutputCalculator outPutCalculator = modelMapper.map(outputCalculatorDto, OutputCalculator.class);
        return outPutCalculator;

    }

    public OutputCalculatorDto mapToDto(OutputCalculator outPutCalculator) {
        OutputCalculatorDto map = modelMapper.map(outPutCalculator, OutputCalculatorDto.class);
        return map;
    }}
