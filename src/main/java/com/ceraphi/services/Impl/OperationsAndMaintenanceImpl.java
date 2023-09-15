package com.ceraphi.services.Impl;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.HeatConnectionCapexDto;
import com.ceraphi.dto.OperationsAndMaintenanceDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.HeatConnectionCapex;
import com.ceraphi.entities.OperationsAndMaintenance;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.OperationsAndMaintenanceRepository;
import com.ceraphi.services.OperationsAndMaintenanceService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import java.util.Optional;

@Service
public class OperationsAndMaintenanceImpl implements OperationsAndMaintenanceService {
    private GeneralInformationRepository generalInformationRepository;
    private OperationsAndMaintenanceRepository operationsAndMaintenanceRepo;
    private ModelMapper modelMapper;
    public OperationsAndMaintenanceImpl(GeneralInformationRepository generalInformationRepository,ModelMapper modelMapper,OperationsAndMaintenanceRepository operationsAndMaintenanceRepo){
        this.generalInformationRepository=generalInformationRepository;
        this.modelMapper=modelMapper;
        this.operationsAndMaintenanceRepo=operationsAndMaintenanceRepo;
    }


    @Override
    public ApiResponseData<OperationsAndMaintenanceDto>  saveTheOperationsAndMaintenance(OperationsAndMaintenanceDto operationsAndMaintenanceDto) {
        try {
            GeneralInformation generalInformation1 = generalInformationRepository.findById(operationsAndMaintenanceDto.getKey()).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", operationsAndMaintenanceDto.getKey()));
            if (operationsAndMaintenanceRepo.existsByGeneralInformationId(generalInformation1.getId())) {
                return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with ID " + generalInformation1.getId() + " already exists.")
                        .build();
            }
            OperationsAndMaintenance operationsAndMaintenance = mapToEntity(operationsAndMaintenanceDto);
            operationsAndMaintenance.setGeneralInformation(generalInformation1);
            OperationsAndMaintenance operationsAndMaintenance1 = operationsAndMaintenanceRepo.save(operationsAndMaintenance);

            OperationsAndMaintenanceDto operationsAndMaintenanceDto1 = mapToDto(operationsAndMaintenance1);
            operationsAndMaintenanceDto1.setKey(operationsAndMaintenance1.getGeneralInformation().getId());
            return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("Operations and Maintenance saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + operationsAndMaintenanceDto.getKey() + " not found.")
                    .build();
        }
        }

    @Override
    public ApiResponseData<OperationsAndMaintenanceDto> updateOperationsAndMaintenance(long id, OperationsAndMaintenanceDto operationsAndMaintenanceDto) {
        
            GeneralInformation generalInformation = generalInformationRepository.findById(operationsAndMaintenanceDto.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", operationsAndMaintenanceDto.getKey()));

        OperationsAndMaintenance operationsAndMaintenance = operationsAndMaintenanceRepo.findByGeneralInformationId(id).orElse(null);
        if (operationsAndMaintenance == null) {
            operationsAndMaintenance = new OperationsAndMaintenance();
            operationsAndMaintenance.setGeneralInformation(generalInformation);
        }

        operationsAndMaintenance.setTotalFixed_O_M(operationsAndMaintenanceDto.getTotalFixed_O_M());
        operationsAndMaintenance.setVariableOMPerUnit(operationsAndMaintenanceDto.getVariableOMPerUnit());
        operationsAndMaintenance.setTotal_variable_O_M(operationsAndMaintenanceDto.getTotal_variable_O_M());
        operationsAndMaintenance.setO_M_cost_per_unit(operationsAndMaintenanceDto.getO_M_cost_per_unit());
        operationsAndMaintenance.setTotal_fixed_OM_per_unit(operationsAndMaintenanceDto.getTotal_fixed_OM_per_unit());
        operationsAndMaintenance.setTotal_O_M(operationsAndMaintenanceDto.getTotal_O_M());

        OperationsAndMaintenance save = operationsAndMaintenanceRepo.save(operationsAndMaintenance);
        OperationsAndMaintenanceDto operationsAndMaintenanceDto1 = mapToDto(save);

        return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                .status(HttpStatus.OK.value())
                .message("operations_data information " + (operationsAndMaintenance == null ? "added" : "updated") + " successfully")
                .data(operationsAndMaintenanceDto1)
                .build();
    }



    @Override
    public ApiResponseData<OperationsAndMaintenanceDto> getOperationsDataById(Long id) {
        boolean b = operationsAndMaintenanceRepo.existsByGeneralInformationId(id);
        if (b == false) {
            OperationsAndMaintenanceDto operationsAndMaintenanceDto = new OperationsAndMaintenanceDto();
            return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(operationsAndMaintenanceDto)
                    .build();
        } else {
            OperationsAndMaintenance operationsAndMaintenance = operationsAndMaintenanceRepo.findByGeneralInformationId(id).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", id));
            OperationsAndMaintenanceDto operationsAndMaintenanceDto = mapToDto(operationsAndMaintenance);
            return ApiResponseData.<OperationsAndMaintenanceDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(operationsAndMaintenanceDto)
                    .build();
        }

    }

    @Override
    public void deleteByGeneralInformationId(Long id) {
        OperationsAndMaintenance operationsAndMaintenance = operationsAndMaintenanceRepo.findByGeneralInformationId(id).orElse(null);
        if (operationsAndMaintenance != null) {
            operationsAndMaintenance.setIs_deleted(true);
            operationsAndMaintenanceRepo.save(operationsAndMaintenance);
        }
    }



    public OperationsAndMaintenance mapToEntity(OperationsAndMaintenanceDto operationsAndMaintenanceDto) {
        OperationsAndMaintenance operationsAndMaintenance = modelMapper.map(operationsAndMaintenanceDto, OperationsAndMaintenance.class);
        return operationsAndMaintenance ;

    }

    public OperationsAndMaintenanceDto mapToDto(OperationsAndMaintenance operationsAndMaintenance) {
        OperationsAndMaintenanceDto operationsAndMaintenanceDto = modelMapper.map(operationsAndMaintenance, OperationsAndMaintenanceDto.class);
        return operationsAndMaintenanceDto;
    }
}
