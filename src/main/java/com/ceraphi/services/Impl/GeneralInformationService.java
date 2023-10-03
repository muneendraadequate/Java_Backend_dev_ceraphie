package com.ceraphi.services.Impl;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.ClientDetails;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.ClientDetailsRepository;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.WellInformationRepository;
import com.ceraphi.services.*;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    private WellInformationRepository wellInformationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OutputCalculatorService outputCalculator;
    @Autowired
   private  ClientDetailsRepository clientDetailsRepository;



//    @Override
//    public ApiResponseData<GeneralInformationDto> saveGeneralInformation(GeneralInformationDto generalInformationDto) {
//
//
//        GeneralInformation generalInfo = mapToEntity(generalInformationDto);
//        String clientId = generalInformationDto.getClientId();
//        Optional<ClientDetails> byId = clientDetailsRepository.findById(Long.parseLong(clientId));
//        ClientDetails clientDetails = byId.get();
//        if (clientDetails != null) {
//            generalInfo.setStatus(Status.ACTIVE);
//            GeneralInformation generalInfo1 = generalInformationRepository.save(generalInfo);
//            GeneralInformationDto generalInformationDto1 = mapToDto1(generalInfo1);
//            generalInformationDto1.setKey(generalInfo1.getId());
//            return ApiResponseData.<GeneralInformationDto>builder().status(HttpStatus.OK.value()).message("GeneralInformationData saved successfully").id(generalInformationDto1.getKey()).build();
//        } else {
//            return ApiResponseData.<GeneralInformationDto>builder().status(HttpStatus.NOT_FOUND.value()).message("Client not found").build();
//        }
//        }
@Override
public ApiResponseData<GeneralInformationDto> saveGeneralInformation(GeneralInformationDto generalInformationDto) {

    // Map DTO to entity
    GeneralInformation generalInfo = mapToEntity(generalInformationDto);
//    String clientId = generalInformationDto.getClientId();

    // Check if a client with the given clientId exists
//    Optional<ClientDetails> byId = clientDetailsRepository.findById(Long.parseLong(clientId));

//    if (byId.isPresent()) {
        // The client exists, set the clientName
//        generalInfo.setClientName(byId.get().getClientName());

        // Set status and save the general information as a new project
        generalInfo.setStatus(Status.ACTIVE);
        GeneralInformation generalInfo1 = generalInformationRepository.save(generalInfo);
        GeneralInformationDto generalInformationDto1 = mapToDto1(generalInfo1);
        generalInformationDto1.setKey(generalInfo1.getId());

        return ApiResponseData.<GeneralInformationDto>builder()
                .status(HttpStatus.OK.value())
                .message("GeneralInformationData saved successfully")
                .id(generalInformationDto1.getKey())
                .build();
//    } else {
//        return ApiResponseData.<GeneralInformationDto>builder()
//                .status(HttpStatus.NOT_FOUND.value())
//                .message("Client not found")
//                .build();
//    }
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


        List<GeneralInformation> sortedData = collect1.stream()
                .sorted(Comparator.comparing(GeneralInformation::getId).reversed())
                .collect(Collectors.toList());
        List<GeneralInformationDto> collect = sortedData.stream().map(s -> mapToDto(s)).collect(Collectors.toList());
        return collect;
    }

//    @Override
//    public List<GeneralInformationDto> getByUserId(Long userId) {
//        // Step 1: Fetch the list of GeneralInformation objects based on userId
//        List<GeneralInformation> allByUser = generalInformationRepository.findAllByUser(userId);
//
//        // Step 2: Filter out GeneralInformation objects where is_deleted is false
//        List<GeneralInformation> validGeneralInformation = allByUser.stream()
//                .filter(generalInfo -> !generalInfo.getIs_deleted())
//                .collect(Collectors.toList());
//
//        // Step 3: Extract clientId values from the filtered GeneralInformation objects
//        List<String> clientIds = validGeneralInformation.stream()
//                .map(GeneralInformation::getClientId)
//                .collect(Collectors.toList());
//
//        // Step 4: Query the Client table to check if these clientIds exist
//        List<ClientDetails> existingClients = clientDetailsRepository.findByClientIdIn(Collections.singletonList(Long.parseLong(clientIds.toString())));
//
//        // Step 5: Filter GeneralInformation objects with matching clientIds
//        List<GeneralInformation> result = validGeneralInformation.stream()
//                .filter(generalInfo -> existingClients.stream()
//                        .anyMatch(client -> client.getId().equals(generalInfo.getClientId())))
//                .collect(Collectors.toList());
//
//        // Map to DTO and return the result
//        return result.stream().map(this::mapToDto).collect(Collectors.toList());
//    }
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
    public List<WellInfoDto> getWellInformationByGeneralInformationId(long id) {

            List<WellInformation> byGeneralInformationId = wellInformationRepository.findByGeneralInformationId(id);

            List<WellInfoDto> collect = byGeneralInformationId.stream().map(s -> (mapToDto(s))).collect(Collectors.toList());
            return collect;
        }
    public WellInfoDto mapToDto (WellInformation wellInformation){
        WellInfoDto wellInfoDto = modelMapper.map(wellInformation, WellInfoDto.class);
        return wellInfoDto;
    }

    public WellInformation mapToEntity (WellInfoDto wellInfoDto){
        WellInformation wellInformation = modelMapper.map(wellInfoDto, WellInformation.class);
        return wellInformation;
    }
    }
