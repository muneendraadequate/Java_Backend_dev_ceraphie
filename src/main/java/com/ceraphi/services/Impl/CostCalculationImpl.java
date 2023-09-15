package com.ceraphi.services.Impl;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.OperationsAndMaintenanceDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.CostCalculator;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.OperationsAndMaintenance;
import com.ceraphi.exceptions.GlobalExceptionHandler;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.CostCalculatorRepository;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.services.CostCalculatorService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CostCalculationImpl implements CostCalculatorService {
    private GeneralInformationRepository generalInformationRepository;
    private ModelMapper modelMapper;
    private CostCalculatorRepository costCalculatorRepository;

    public CostCalculationImpl(GeneralInformationRepository generalInformationRepository, ModelMapper modelMapper, CostCalculatorRepository costCalculatorRepository) {
        this.generalInformationRepository = generalInformationRepository;
        this.costCalculatorRepository = costCalculatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponseData<CostCalculatorDto> saveCostCalculation(CostCalculatorDto costCalculatorDto) {
        try {

            GeneralInformation generalInformation = generalInformationRepository.findById(costCalculatorDto.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", costCalculatorDto.getKey()));

            if (costCalculatorRepository.existsByGeneralInformationId(generalInformation.getId())) {
                return ApiResponseData.<CostCalculatorDto>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with ID " + generalInformation.getId() + " already exists.")
                        .build();
            }
            CostCalculator costCalculator = mapToEntity(costCalculatorDto);
            costCalculator.setGeneralInformation(generalInformation);
            CostCalculator savedCostCalculator = costCalculatorRepository.save(costCalculator);

            CostCalculatorDto savedCostCalculatorDto = mapToDto(savedCostCalculator);
            savedCostCalculatorDto.setKey(savedCostCalculator.getGeneralInformation().getId());

            return ApiResponseData.<CostCalculatorDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("Cost calculation saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<CostCalculatorDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + costCalculatorDto.getKey() + " not found.")
                    .build();
        }
    }

    @Override
    public ApiResponseData<CostCalculatorDto> updateCostCalculation(long id, CostCalculatorDto costCalculatorDto) {
        GeneralInformation generalInformation = generalInformationRepository.findById(costCalculatorDto.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", costCalculatorDto.getKey()));

        CostCalculator costCalculator = costCalculatorRepository.findByGeneralInformationId(id).orElse(null);
        if (costCalculator == null) {
            costCalculator = new CostCalculator();
            costCalculator.setGeneralInformation(generalInformation);
        }

        costCalculator.setOperationalLifetime(costCalculatorDto.getOperationalLifetime());
        costCalculator.setOperatingIncome(costCalculatorDto.getOperatingIncome());
        costCalculator.setInflationRate(costCalculatorDto.getInflationRate());
        costCalculator.setPotentialRevenue(costCalculatorDto.getPotentialRevenue());
        costCalculator.setDiscountRate(costCalculatorDto.getDiscountRate());
        costCalculator.setLoanRepaymentPeriod(costCalculatorDto.getLoanRepaymentPeriod());
        costCalculator.setHeatSalePrice(costCalculatorDto.getHeatSalePrice());

        CostCalculator save = costCalculatorRepository.save(costCalculator);
        CostCalculatorDto costCalculatorDto1 = mapToDto(save);

        return ApiResponseData.<CostCalculatorDto>builder()
                .status(HttpStatus.OK.value())
                .message("operations_data information " + (costCalculator == null ? "added" : "updated") + " successfully")
                .data(costCalculatorDto1)
                .build();
    }


    @Override
    public ApiResponseData<CostCalculatorDto> getCostDataById(Long id) {
        boolean b = costCalculatorRepository.existsByGeneralInformationId(id);
        if (b == false) {
            CostCalculatorDto costCalculatorDto1=new CostCalculatorDto();
            return ApiResponseData.<CostCalculatorDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(costCalculatorDto1)
                    .build();
        } else {
        CostCalculator costCalculator = costCalculatorRepository.findByGeneralInformationId(id).orElseThrow(() -> new ResourceNotFoundException("costCalculationData", "id", id));
            CostCalculatorDto costCalculatorDto = mapToDto(costCalculator);
            return ApiResponseData.<CostCalculatorDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(costCalculatorDto)
                    .build();
        }
    }


    @Override
    public void deleteByGeneralInformationId(Long id) {

        CostCalculator costCalculator1 = costCalculatorRepository.findByGeneralInformationId(id).orElse(null);
        if (costCalculator1 != null) {
        costCalculator1.setIs_deleted(true);
            costCalculatorRepository.save(costCalculator1);
        }

    }


    public CostCalculator mapToEntity(CostCalculatorDto costCalculatorDto) {
        CostCalculator costCalculator = modelMapper.map(costCalculatorDto, CostCalculator.class);
        return costCalculator;

    }

    public CostCalculatorDto mapToDto(CostCalculator costCalculator) {
        CostCalculatorDto costCalculatorDto = modelMapper.map(costCalculator, CostCalculatorDto.class);
        return costCalculatorDto;
    }
}
