package com.ceraphi.services.Impl;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.InputsDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.*;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.*;
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
    private SurfaceEquipmentService surfaceEquipmentService;
    @Autowired
    private WellService wellService;
    @Autowired
    private WellInformationRepository wellInformationRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OutputCalculatorService outputCalculator;
    @Autowired
    private ClientDetailsRepository clientDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InputsRepository inputsRepository;


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
        GeneralInformation generalInfo = new GeneralInformation();
        String clientId = generalInformationDto.getClientId();

        Optional<ClientDetails> byId = clientDetailsRepository.findById(Long.parseLong(clientId));

        if (byId.isPresent()) {
            ClientDetails clientDetails = byId.get();
            generalInfo.setUser(generalInformationDto.getUser());
            generalInfo.setClientName(clientDetails.getClientName());
            generalInfo.setProjectName(generalInformationDto.getProjectName());
            generalInfo.setPreferredUnits(generalInformationDto.getPreferredUnits());
            generalInfo.setProjectCurrency(generalInformationDto.getProjectCurrency());
            generalInfo.setClientId(String.valueOf(clientDetails.getId()));
            generalInfo.setGeoPoliticalData(generalInformationDto.getGeoPoliticalData());
            generalInfo.setRestrictionDetails(generalInformationDto.getRestrictionDetails());
            generalInfo.setAddress(generalInformationDto.getAddress());
            generalInfo.setCity(generalInformationDto.getCity());
            generalInfo.setCountry(generalInformationDto.getCountry());
            generalInfo.setPostalCode(generalInformationDto.getPostalCode());
            generalInfo.setIs_deleted(false);
            generalInfo.setProjectType(generalInformationDto.getProjectType());
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
        } else {
            return ApiResponseData.<GeneralInformationDto>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Client not found")
                    .build();
        }
    }

    @Override
    public ApiResponseData<GeneralInformationDto> updateGeneralInformation(Long id, GeneralInformationDto updateGeneralInformation) {
        Optional<GeneralInformation> byId = generalInformationRepository.findById(id);
        GeneralInformation generalInfo = byId.get();
        if (byId.isPresent() && updateGeneralInformation.getClientId().equals(generalInfo.getClientId()) && updateGeneralInformation.getUser().equals(generalInfo.getUser())) {
            String clientId = updateGeneralInformation.getClientId();
            Optional<ClientDetails> clientById = clientDetailsRepository.findById(Long.parseLong(clientId));
            ClientDetails clientDetails = clientById.get();
            generalInfo.setUser(updateGeneralInformation.getUser());
            generalInfo.setClientName(clientDetails.getClientName());
            generalInfo.setProjectName(updateGeneralInformation.getProjectName());
            generalInfo.setPreferredUnits(updateGeneralInformation.getPreferredUnits());
            generalInfo.setProjectCurrency(updateGeneralInformation.getProjectCurrency());
            generalInfo.setClientId(String.valueOf(clientDetails.getId()));
            generalInfo.setGeoPoliticalData(updateGeneralInformation.getGeoPoliticalData());
            generalInfo.setRestrictionDetails(updateGeneralInformation.getRestrictionDetails());
            generalInfo.setAddress(updateGeneralInformation.getAddress());
            generalInfo.setCity(updateGeneralInformation.getCity());
            generalInfo.setCountry(updateGeneralInformation.getCountry());
            generalInfo.setPostalCode(updateGeneralInformation.getPostalCode());
            // Set status and save the general information as a new project
            GeneralInformation generalInfo1 = generalInformationRepository.save(generalInfo);
            GeneralInformationDto generalInformationDto = mapToDto1(generalInfo1);
            generalInformationDto.setKey(generalInfo1.getId());

            return ApiResponseData.<GeneralInformationDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("GeneralInformationData saved successfully")
                    .id(generalInformationDto.getKey())
                    .build();

        } else {
            return ApiResponseData.<GeneralInformationDto>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Client not found" + "or" + "user not found")
                    .build();
        }
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
        GeneralInformation generalInformation = generalInformationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", id));
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

    @Override
    public void saveInputs(InputsDto inputsDto) {
        Inputs inputs = inputsMapToEntity(inputsDto);
//
//        if (inputsDto.getProjectId() != null) {
//            GeneralInformation generalInformation = generalInformationRepository.findById(inputsDto.getProjectId())
//                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", inputsDto.getProjectId()));
//            inputs.setGeneralInformation(generalInformation);
//        }
        inputs.setCreatedAt(new Date());
        inputsRepository.save(inputs);
    }

    @Override
    public InputsDto findLastSavedData() {
        Inputs inputs = inputsRepository.findFirstByOrderByCreatedAtDesc();

        InputsDto inputsDto = inputsMapToDto(inputs);

        return inputsDto;
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

    public WellInfoDto mapToDto(WellInformation wellInformation) {
        WellInfoDto wellInfoDto = modelMapper.map(wellInformation, WellInfoDto.class);
        return wellInfoDto;
    }

    public WellInformation mapToEntity(WellInfoDto wellInfoDto) {
        WellInformation wellInformation = modelMapper.map(wellInfoDto, WellInformation.class);
        return wellInformation;
    }

    public Inputs inputsMapToEntity(InputsDto inputsDto) {
        Inputs inputs = new Inputs();
        inputs.setCapacityRequired(inputsDto.getCapacityRequired());
        inputs.setRequiredProcessTemp(inputsDto.getRequiredProcessTemp());
        inputs.setNetworkLength(inputsDto.getNetworkLength());
        inputs.setGeothermalGradient(inputsDto.getGeothermalGradient());
        inputs.setMinOperationalHours(inputsDto.getMinOperationalHours());
        inputs.setAmbientTemperature(inputsDto.getAmbientTemperature());
        inputs.setLifeTimeYears(inputsDto.getLifeTimeYears());
        inputs.setSellingPrice(inputsDto.getSellingPrice());
        inputs.setProcessDelta(inputsDto.getProcessDelta());
        inputs.setPumpEfficiency(inputsDto.getPumpEfficiency());
        inputs.setDeepWellFlowRates(inputsDto.getDeepWellFlowRates());
        inputs.setDeepDelta(inputsDto.getDeepDelta());
        inputs.setConnectionPipeThermalConductivity(inputsDto.getConnectionPipeThermalConductivity());
        inputs.setConnectionPipeThickness(inputsDto.getConnectionPipeThickness());
        inputs.setFuelType(inputsDto.getFuelType());
        inputs.setDiscountRate_percent(inputsDto.getDiscountRate_percent());
        inputs.setElectricalPrice(inputsDto.getElectricalPrice());
        return inputs;

    }

    public InputsDto inputsMapToDto(Inputs inputs) {
        InputsDto inputsDto = new InputsDto();
        inputsDto.setCapacityRequired(inputs.getCapacityRequired());
        inputsDto.setRequiredProcessTemp(inputs.getRequiredProcessTemp());
        inputsDto.setNetworkLength(inputs.getNetworkLength());
        inputsDto.setGeothermalGradient(inputs.getGeothermalGradient());
        inputsDto.setMinOperationalHours(inputs.getMinOperationalHours());
        inputsDto.setAmbientTemperature(inputs.getAmbientTemperature());
        inputsDto.setLifeTimeYears(inputs.getLifeTimeYears());
        inputsDto.setSellingPrice(inputs.getSellingPrice());
        inputsDto.setProcessDelta(inputs.getProcessDelta());
        inputsDto.setPumpEfficiency(inputs.getPumpEfficiency());
        inputsDto.setDeepWellFlowRates(inputs.getDeepWellFlowRates());
        inputsDto.setDeepDelta(inputs.getDeepDelta());
        inputsDto.setConnectionPipeThermalConductivity(inputs.getConnectionPipeThermalConductivity());
        inputsDto.setConnectionPipeThickness(inputs.getConnectionPipeThickness());
        inputsDto.setFuelType(inputs.getFuelType());
        inputsDto.setDiscountRate_percent(inputs.getDiscountRate_percent());
        inputsDto.setElectricalPrice(inputs.getElectricalPrice());
        return inputsDto;

    }
}