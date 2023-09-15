package com.ceraphi.services.Impl;

import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.dto.HeatConnectionCapexDto;
import com.ceraphi.dto.SurfaceEquipmentDto;
import com.ceraphi.entities.CostCalculator;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.HeatConnectionCapex;
import com.ceraphi.entities.SurfaceEquipment;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.HeatConnectionRepo;
import com.ceraphi.services.HeatConnectionCapexService;
import com.ceraphi.utils.ApiResponseData;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeatConnectionCapexImpl  implements HeatConnectionCapexService {

private HeatConnectionRepo heatConnectionRepo;
private GeneralInformationRepository generalInformationRepository;
private ModelMapper modelMapper;
    public HeatConnectionCapexImpl (HeatConnectionRepo heatConnectionRepo,ModelMapper modelMapper,GeneralInformationRepository generalInformationRepository){
        this.heatConnectionRepo=heatConnectionRepo;
        this.modelMapper=modelMapper;
        this.generalInformationRepository=generalInformationRepository;

    }
    @Override
    public ApiResponseData<HeatConnectionCapexDto>saveHeatConnectionCapex(HeatConnectionCapexDto heatConnectionCapexDto) {
        try{
        GeneralInformation generalInformation1 = generalInformationRepository.findById(heatConnectionCapexDto.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", heatConnectionCapexDto.getKey()));
        if (heatConnectionRepo.existsByGeneralInformationId(generalInformation1.getId())) {
            return ApiResponseData.<HeatConnectionCapexDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Data with ID " + generalInformation1.getId() + " already exists.")
                    .build();
        }
        HeatConnectionCapex heatConnectionCapex = mapToEntity(heatConnectionCapexDto);
        heatConnectionCapex.setGeneralInformation(generalInformation1);
        HeatConnectionCapex heatConnection = heatConnectionRepo.save(heatConnectionCapex);
        HeatConnectionCapexDto heatConnectionCapexDto1 = mapToDto(heatConnection);
        heatConnectionCapexDto1.setKey(heatConnection.getGeneralInformation().getId());
            return ApiResponseData.<HeatConnectionCapexDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("HeatCalculation saved successfully")
                    .build();
        } catch (ResourceNotFoundException e) {
            return ApiResponseData.<HeatConnectionCapexDto>builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("General Information with ID " + heatConnectionCapexDto.getKey() + " not found.")
                    .build();
    }}

    @Override
    public ApiResponseData<HeatConnectionCapexDto> updateHeatConnection(long id, HeatConnectionCapexDto heatConnectionCapexDto) {
      
            GeneralInformation generalInformation = generalInformationRepository.findById(heatConnectionCapexDto.getKey())
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", heatConnectionCapexDto.getKey()));

        HeatConnectionCapex heatConnectionCapex = heatConnectionRepo.findByGeneralInformationId(id).orElse(null);
        if (heatConnectionCapex == null) {
            heatConnectionCapex = new HeatConnectionCapex();
            heatConnectionCapex.setGeneralInformation(generalInformation);
        }

        heatConnectionCapex.setHeatPipelineCost(heatConnectionCapexDto.getHeatPipelineCost());
        heatConnectionCapex.setDistanceToHeatNetwork(heatConnectionCapexDto.getDistanceToHeatNetwork());
        heatConnectionCapex.setHeatPipelineType(heatConnectionCapexDto.getHeatPipelineType());
        heatConnectionCapex.setTotalConnectionCapitalCost(heatConnectionCapexDto.getTotalConnectionCapitalCost());

        HeatConnectionCapex save = heatConnectionRepo.save(heatConnectionCapex);
        HeatConnectionCapexDto heatConnectionCapexDto1 = mapToDto(save);

        return ApiResponseData.<HeatConnectionCapexDto>builder()
                .status(HttpStatus.OK.value())
                .message("Heat_Connection information " + (heatConnectionCapex == null ? "added" : "updated") + " successfully")
                .data(heatConnectionCapexDto1)
                .build();
    }



    @Override
    public ApiResponseData<HeatConnectionCapexDto> getHeatDataWithId(Long id) {
        boolean b = heatConnectionRepo.existsByGeneralInformationId(id);
        if (b == false) {
            HeatConnectionCapexDto heatConnectionCapexDto = new HeatConnectionCapexDto();
            return ApiResponseData.<HeatConnectionCapexDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(heatConnectionCapexDto)
                    .build();
        } else {
            HeatConnectionCapex heatConnectionCapex = heatConnectionRepo.findByGeneralInformationId(id).orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", id));
            HeatConnectionCapexDto heatConnectionCapexDto = mapToDto(heatConnectionCapex);
            return ApiResponseData.<HeatConnectionCapexDto>builder()
                    .status(HttpStatus.OK.value())
                    .data(heatConnectionCapexDto)
                    .build();
        }
    }

    @Override
    public void deleteByGeneralInformationId(Long id) {
        HeatConnectionCapex heatConnectionCapex = heatConnectionRepo.findByGeneralInformationId(id).orElse(null);
        if (heatConnectionCapex != null) {
            heatConnectionCapex.setIs_deleted(true);
            heatConnectionRepo.save(heatConnectionCapex);
        }
    }


    public HeatConnectionCapex mapToEntity(HeatConnectionCapexDto heatConnectionCapexDto) {
        HeatConnectionCapex heatConnectionCapex = modelMapper.map(heatConnectionCapexDto, HeatConnectionCapex.class);
        return heatConnectionCapex ;

    }

    public HeatConnectionCapexDto mapToDto(HeatConnectionCapex heatConnectionCapex) {
        HeatConnectionCapexDto heatConnectionCapexDto = modelMapper.map(heatConnectionCapex, HeatConnectionCapexDto.class);
        return heatConnectionCapexDto;
    }
}
