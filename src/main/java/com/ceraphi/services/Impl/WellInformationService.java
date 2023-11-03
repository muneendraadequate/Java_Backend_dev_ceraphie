package com.ceraphi.services.Impl;
import com.ceraphi.dto.WellDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellDetails;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.*;
import com.ceraphi.services.WellService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WellInformationService implements WellService {
    @Autowired
    private WellDetailsRepo wellDetailsRepo;
    @Autowired
    private WellInformationRepository wellInformationRepository;
    @Autowired
    private GeneralInformationRepository generalInformationRepository;
    @Autowired
    private CostCalculatorRepository costCalculatorRepository;
    @Autowired
    private HeatConnectionRepo heatConnectionRepo;
    @Autowired
    private OperationsAndMaintenanceRepository operationsAndMaintenanceRepository;
    @Autowired
    private SubSurfaceRepository subSurfaceRepository;
    @Autowired
    private SurfaceEquipmentRepository surfaceEquipmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponseData<WellInfoDto> saveWellInformation(Long id, WellInfoDto wellInformationDto) {
        try {
            GeneralInformation generalInformation1 = generalInformationRepository.findById(wellInformationDto.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", wellInformationDto.getKey()));
            if (wellInformationRepository.existsByGeneralInformationId(generalInformation1.getId())) {
                return ApiResponseData.<WellInfoDto>builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .message("Data with ID " + generalInformation1.getId() + " already exists.")
                        .build();
            }
            String wellSiteName = wellInformationDto.getSiteName();
            String coordinates = wellInformationDto.getCoordinates_latitude();
            Long userId = wellInformationDto.getUserId();
            String coordinatesLongitude = wellInformationDto.getCoordinates_longitude();
            Optional<GeneralInformation> generalInfoById = generalInformationRepository.findById(id);
            GeneralInformation generalInformation = generalInfoById.get();
            WellInformation wellInformation = new WellInformation();
            wellInformation.setGeneralInformation(generalInformation);
            wellInformation.setSiteName(wellSiteName);
            wellInformation.setCoordinates_latitude(coordinates);
            wellInformation.setUserId(userId);
            wellInformation.setCoordinates_longitude(coordinatesLongitude);
            for (WellDto wellDetailDto : wellInformationDto.getWells()) {
                WellDetails wellDetail = new WellDetails();
                wellDetail.setWellName(wellDetailDto.getName());
                wellDetail.setWellType(wellDetailDto.getType());
                wellInformation.addWellDetail(wellDetail);
            }
            WellInformation save = wellInformationRepository.save(wellInformation);

            WellInfoDto wellInfoDto = mapToDto(save);
            wellInfoDto.setKey(save.getGeneralInformation().getId());
            return ApiResponseData.<WellInfoDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("wellInformation saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<WellInfoDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + wellInformationDto.getKey() + " not found.")
                    .build();
        }
    }


    @Override
    public ApiResponseData<List<WellInfoDto>> getWellInformationByGeneralInformationId(long id) {
        boolean b = wellInformationRepository.existsByGeneralInformationId(id);
        if (b == false) {
            List<WellInfoDto> emptyList = new ArrayList<>();
            return ApiResponseData.<List<WellInfoDto>>builder()
                    .status(HttpStatus.OK.value())
                    .data(emptyList)
                    .build();
        } else {
            List<WellInformation> byGeneralInformationId = wellInformationRepository.findByGeneralInformationId(id);

            List<WellInfoDto> collect = byGeneralInformationId.stream().map(s -> (mapToDto(s))).collect(Collectors.toList());
            return ApiResponseData.<List<WellInfoDto>>builder()
                    .status(HttpStatus.OK.value())
                    .data(collect)
                    .build();
        }
    }
    @Override
    public ApiResponseData<WellInfoDto> updateWellsData(long generalInformationId, WellInfoDto wellInfoDto) {


        GeneralInformation generalInformation = generalInformationRepository.findById(generalInformationId)
                .orElseThrow(() -> new ResourceNotFoundException("generalinfo ", "id", generalInformationId));

        if (generalInformation==null) {
            return ApiResponseData.<WellInfoDto>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("GeneralInformation not found with id: " + generalInformationId)
                    .build();
        }

        WellInformation wellInformation1 = generalInformation.getWellInfo();

        if (wellInformation1 == null) {
            // If WellInformation does not exist, create a new one
            wellInformation1 = new WellInformation();
            wellInformation1.setGeneralInformation(generalInformation);
        }

        // Update the fields of WellInformation based on the provided DTO
        wellInformation1.setSiteName(wellInfoDto.getSiteName());
        wellInformation1.setCoordinates_longitude(wellInfoDto.getCoordinates_longitude());
        wellInformation1.setCoordinates_latitude(wellInfoDto.getCoordinates_latitude());
        wellInformation1.setUserId(wellInfoDto.getUserId());

        // Clear existing WellDetails and add new ones from the DTO
        wellInformation1.getWellDetails().clear();
        for (WellDto wellDto : wellInfoDto.getWells()) {
            WellDetails wellDetails = new WellDetails();
            wellDetails.setWellName(wellDto.getName());
            wellDetails.setWellType(wellDto.getType());
            wellDetails.setWellInformation(wellInformation1);
            wellInformation1.addWellDetail(wellDetails);
        }

        WellInformation updatedWellInformation = wellInformationRepository.save(wellInformation1);

        WellInfoDto wellInfoDtoResponse = mapToDto(updatedWellInformation);
        return ApiResponseData.<WellInfoDto>builder()
                .status(HttpStatus.OK.value())
                .message("WellInformation updated successfully")
                .data(wellInfoDtoResponse)
                .build();
    }

        @Override
        public void deleteByGeneralInformationId (Long id) {
            WellInformation wellInformation = wellInformationRepository.getByGeneralInformationId(id).orElse(null);
            if (wellInformation != null) {
                wellInformation.setIs_deleted(true);
//            for (WellInformation wellInformation : wellInformations) {
                wellInformationRepository.save(wellInformation);
            }
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
