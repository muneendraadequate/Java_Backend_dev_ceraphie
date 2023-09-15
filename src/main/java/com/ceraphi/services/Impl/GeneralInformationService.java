package com.ceraphi.services.Impl;

import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.entities.ClientDetails;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.OutputCalculator;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.OutPutCalculatorRepository;
import com.ceraphi.services.*;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.PageableResponse;
import com.ceraphi.utils.Status;
import io.swagger.annotations.Api;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GeneralInformationService implements GeneralInfoServices {
    @Autowired
    private WellInstallationCAPEXService wellInstallationCAPEXService;
    @Autowired
    private GeneralInformationRepository generalInformationRepository;
    @Autowired
    private CostCalculatorService costCalculatorService;
    @Autowired
    private HeatConnectionCapexService heatConnectionCapexService;
    @Autowired
    private OperationsAndMaintenanceService operationsAndMaintenanceService;
    @Autowired
    private SubSurfaceService subSurfaceService;
    @Autowired
    private  SurfaceEquipmentService surfaceEquipmentService;
    @Autowired
    private WellService wellService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OutputCalculatorService outputCalculator;

    @Override
    public ApiResponseData<GeneralInformationDto> saveGeneralInformation(GeneralInformationDto generalInformationDto) {
        GeneralInformation generalInfo = mapToEntity(generalInformationDto);
        generalInfo.setStatus(Status.ACTIVE);
        GeneralInformation generalInfo1 = generalInformationRepository.save(generalInfo);
        GeneralInformationDto generalInformationDto1 = mapToDto1(generalInfo1);
        generalInformationDto1.setKey(generalInfo1.getId());

        return ApiResponseData.<GeneralInformationDto>builder().status(HttpStatus.OK.value()).message("GeneralInformationData saved successfully").id(generalInformationDto1.getKey()).build();
    }


    @Override
    public GeneralInformationDto updateGeneralInformation(Long id, GeneralInformationDto updateGeneralInformation) {
        Optional<GeneralInformation> byId = generalInformationRepository.findById(id);
        GeneralInformation generalInfo = byId.get();

        generalInfo.setCountry(updateGeneralInformation.getCountry());
        generalInfo.setCity(updateGeneralInformation.getCity());
        generalInfo.setAddress(updateGeneralInformation.getAddress());
        generalInfo.setClientName(updateGeneralInformation.getClientName());
        generalInfo.setPostalCode(updateGeneralInformation.getPostalCode());
        generalInfo.setRestrictionDetails(updateGeneralInformation.getRestrictionDetails());
        generalInfo.setClientId(updateGeneralInformation.getClientId());
        generalInfo.setGeoPoliticalData(updateGeneralInformation.getGeoPoliticalData());
        generalInfo.setPreferredUnits(updateGeneralInformation.getPreferredUnits());
        generalInfo.setProjectCurrency(updateGeneralInformation.getProjectCurrency());
        generalInfo.setProjectName(updateGeneralInformation.getProjectName());
        generalInfo.setId(id);
        GeneralInformation generalInformationUpdate = generalInformationRepository.save(generalInfo);
        GeneralInformationDto generalInformationDto = new GeneralInformationDto();
        generalInformationDto.setKey(id);
        return generalInformationDto;

    }




    @Override
    public GeneralInformationDto getGeneralInformationById(Long id) {
        GeneralInformation generalInformation1 = generalInformationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", id));
        GeneralInformationDto generalInformationDto = mapToDto(generalInformation1);
        return generalInformationDto;

    }

    @Override
    public List<GeneralInformationDto> getByUserId(Long id) {
        List<GeneralInformation> allByUser = generalInformationRepository.findAllByUser(id);
        List<GeneralInformation> collect1 = allByUser.stream().filter(s -> s.getIs_deleted() == false).collect(Collectors.toList());
        List<GeneralInformationDto> collect = collect1.stream().map(s -> mapToDto(s)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deleteById(Long id) {
        GeneralInformation generalInformation = generalInformationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("general", "id", id));
     generalInformation.setIs_deleted(true);
        generalInformationRepository.save(generalInformation);
        costCalculatorService.deleteByGeneralInformationId(id);
        heatConnectionCapexService.deleteByGeneralInformationId(id);
        operationsAndMaintenanceService.deleteByGeneralInformationId(id);
        subSurfaceService.deleteByGeneralInformationId(id);
        surfaceEquipmentService.deleteByGeneralInformationId(id);
        wellService.deleteByGeneralInformationId(id);
        outputCalculator.deleteByGeneralInformationId(id);
        wellInstallationCAPEXService.deleteByGeneralInformationId(id);

    }


    public GeneralInformation mapToEntity(GeneralInformationDto generalInformationDto) {
        GeneralInformation generalInformation = modelMapper.map(generalInformationDto, GeneralInformation.class);
        return generalInformation;

    }

    public GeneralInformationDto mapToDto(GeneralInformation generalInformation) {
        GeneralInformationDto generalInformationDto = modelMapper.map(generalInformation, GeneralInformationDto.class);
        generalInformationDto.setKey(generalInformation.getId());
        generalInformationDto.setStatus(generalInformation.getStatus());
        generalInformationDto.setUser(generalInformation.getUser());
        return generalInformationDto;
    }

    public GeneralInformationDto mapToDto1(GeneralInformation generalInformation) {
        GeneralInformationDto generalInformationDto = modelMapper.map(generalInformation, GeneralInformationDto.class);
        return generalInformationDto;
    }
}