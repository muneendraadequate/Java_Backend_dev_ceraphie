package com.ceraphi.services.Impl;

import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.dto.InputsDto;
import com.ceraphi.dto.NewWellCreationDto;
import com.ceraphi.dto.WellSummaryDto;
import com.ceraphi.entities.ClientDetails;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.Inputs;
import com.ceraphi.entities.WellSummaryData;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.ClientDetailsRepository;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.InputsRepository;
import com.ceraphi.repository.SummaryRepository;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.Status;
import io.swagger.annotations.ApiResponse;
import org.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class NewWellsProjectService {
    @Autowired
    private ClientDetailsRepository clientDetailsRepository;
    @Autowired
    private GeneralInformationRepository generalInformationRepository;
    @Autowired
    private InputsRepository inputsRepository;
    @Autowired
    private SummaryRepository summaryRepository;

    @Transactional
    public ApiResponseData saveTheNewWellProject(NewWellCreationDto newWellCreationDto) {
        InputsDto inputsDto = newWellCreationDto.getInputsDto();
        WellSummaryDto wellSummaryDto = newWellCreationDto.getWellSummaryDto();
        GeneralInformationDto generalInformationDto = newWellCreationDto.getGeneralInformationDto();
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
            Inputs inputs = mapToEntityInputs(inputsDto);

            GeneralInformation generalInformation = generalInformationRepository.findById(generalInfo1.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("generalInfo", "id", generalInfo1.getId()));
            inputs.setGeneralInformation(generalInformation);

            inputs.setCreatedAt(new Date());
            inputsRepository.save(inputs);
            WellSummaryData wellSummaryData = mapToEntitySummary(wellSummaryDto);
            wellSummaryData.setGeneralInformation(generalInfo1);
            summaryRepository.save(wellSummaryData);
            return ApiResponseData.<GeneralInformationDto>builder()
                    .status(HttpStatus.OK.value())
                    .message("project saved successfully")
                    .build();

        } else {
            return ApiResponseData.<GeneralInformationDto>builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Client not found")
                    .build();
        }
    }

    public ApiResponseData updateTheNewWellProject(NewWellCreationDto newWellCreationDto, Long id) {
        GeneralInformationDto generalInformationDto = newWellCreationDto.getGeneralInformationDto();
        Optional<GeneralInformation> byId = generalInformationRepository.findById(id);
        if (byId.isPresent()) {
            GeneralInformation generalInfo = byId.get();
            Optional<ClientDetails> client = clientDetailsRepository.findById(Long.parseLong(generalInformationDto.getClientId()));
            ClientDetails clientDetails = client.get();
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
            generalInfo.setProjectType(generalInformationDto.getProjectType());
            generalInformationRepository.save(generalInfo);
            Inputs dBiInputs = inputsRepository.findByGeneralInformationId(id);
            if (dBiInputs != null) {
                InputsDto inputsDto = newWellCreationDto.getInputsDto();
                dBiInputs.setCapacityRequired(inputsDto.getCapacityRequired());
                dBiInputs.setRequiredProcessTemp(inputsDto.getRequiredProcessTemp());
                dBiInputs.setNetworkLength(inputsDto.getNetworkLength());
                dBiInputs.setGeothermalGradient(inputsDto.getGeothermalGradient());
                dBiInputs.setMinOperationalHours(inputsDto.getMinOperationalHours());
                dBiInputs.setAmbientTemperature(inputsDto.getAmbientTemperature());
                dBiInputs.setLifeTimeYears(inputsDto.getLifeTimeYears());
                dBiInputs.setSellingPrice(inputsDto.getSellingPrice());
                dBiInputs.setProcessDelta(inputsDto.getProcessDelta());
                dBiInputs.setPumpEfficiency(inputsDto.getPumpEfficiency());
                dBiInputs.setDeepWellFlowRates(inputsDto.getDeepWellFlowRates());
                dBiInputs.setDeepDelta(inputsDto.getDeepDelta());
                dBiInputs.setConnectionPipeThermalConductivity(inputsDto.getConnectionPipeThermalConductivity());
                dBiInputs.setConnectionPipeThickness(inputsDto.getConnectionPipeThickness());
                dBiInputs.setFuelType(inputsDto.getFuelType());
                dBiInputs.setDiscountRate_percent(inputsDto.getDiscountRate_percent());
                dBiInputs.setElectricalPrice(inputsDto.getElectricalPrice());
                inputsRepository.save(dBiInputs);
            } else {

            }
            WellSummaryData wellSummaryData = summaryRepository.findByGeneralInformationId(id);
            if (wellSummaryData != null) {
                WellSummaryDto wellSummaryDto = newWellCreationDto.getWellSummaryDto();

                wellSummaryData.setHeatCapacityRequired(wellSummaryDto.getHeatCapacityRequired());
                wellSummaryData.setDeepCapacityRequired(wellSummaryDto.getDeepCapacityRequired());
                wellSummaryData.setHeatInitialInvestment(wellSummaryDto.getHeatInitialInvestmentCapex());
                wellSummaryData.setDeepInitialInvestment(wellSummaryDto.getDeepInitialInvestmentCapex());
                wellSummaryData.setHeatAnnualO_M(wellSummaryDto.getHeatAnnualO_M_Opex());
                wellSummaryData.setDeepAnnualO_M(wellSummaryDto.getDeepAnnualO_M_OPex());
                wellSummaryData.setHeatNpv(wellSummaryDto.getHeatNpv());
                wellSummaryData.setDeepNpv(wellSummaryDto.getDeepNpv());
                wellSummaryData.setHeatIRR(wellSummaryDto.getHeatIRR());
                wellSummaryData.setDeepIRR(wellSummaryDto.getDeepIRR());
                wellSummaryData.setHeatP_I(wellSummaryDto.getHeatP_I());
                wellSummaryData.setDeepP_I(wellSummaryDto.getDeepP_I());
                wellSummaryData.setHeatPaybackPeriod(wellSummaryDto.getHeatPaybackPeriod());
                wellSummaryData.setDeepPaybackPeriod(wellSummaryDto.getDeepPaybackPeriod());
                wellSummaryData.setHeatLcoh(wellSummaryDto.getHeatLcoh());
                wellSummaryData.setDeepLcoh(wellSummaryDto.getDeepLcoh());
                wellSummaryData.setHeatSelected(wellSummaryDto.isHeatSelected());
                wellSummaryData.setDeepSelected(wellSummaryDto.isDeepSelected());
                wellSummaryData.setHeatWells(wellSummaryDto.getHeatWells());
                wellSummaryData.setDeepWells(wellSummaryDto.getDeepWells());
                summaryRepository.save(wellSummaryData);

            } else {

            }
            return ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .message("project updated successfully")
                    .build();

        } else {
            return ApiResponseData.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("General Information not found for the given ID!")
                    .build();


        }


    }

   public ApiResponseData getProjectById(Long id){
       Optional<GeneralInformation> byId = generalInformationRepository.findById(id);
       GeneralInformation generalInformation = byId.get();
       Inputs inputs = inputsRepository.findByGeneralInformationId(id);
       WellSummaryData summaryData = summaryRepository.findByGeneralInformationId(id);

       GeneralInformationDto generalInformationDto = mapToDtoGeneralInformation(generalInformation);
       WellSummaryDto wellSummaryDto = mapToDtoSummary(summaryData);
       InputsDto inputsDto = mapToDtoInputs(inputs);
       NewWellCreationDto NewWellCreationDto = new NewWellCreationDto();
       NewWellCreationDto.setWellSummaryDto(wellSummaryDto);
       NewWellCreationDto.setInputsDto(inputsDto);
       NewWellCreationDto.setGeneralInformationDto(generalInformationDto);

       return ApiResponseData.builder().status(HttpStatus.OK.value()).data(NewWellCreationDto).build();
   }


    public  GeneralInformationDto mapToDtoGeneralInformation(GeneralInformation generalInformation){

        GeneralInformationDto generalInformationDto = new GeneralInformationDto();
        generalInformationDto.setUser(generalInformation.getUser());
        generalInformationDto.setClientName(generalInformation.getClientName());
        generalInformationDto.setProjectName(generalInformation.getProjectName());
        generalInformationDto.setPreferredUnits(generalInformation.getPreferredUnits());
        generalInformationDto.setProjectCurrency(generalInformation.getProjectCurrency());
        generalInformationDto.setClientId(String.valueOf(generalInformation.getClientId()));
        generalInformationDto.setGeoPoliticalData(generalInformation.getGeoPoliticalData());
        generalInformationDto.setRestrictionDetails(generalInformation.getRestrictionDetails());
        generalInformationDto.setAddress(generalInformation.getAddress());
        generalInformationDto.setCity(generalInformation.getCity());
        generalInformationDto.setCountry(generalInformation.getCountry());
        generalInformationDto.setPostalCode(generalInformation.getPostalCode());
        generalInformationDto.setProjectType(generalInformation.getProjectType());
        return generalInformationDto;
    }

    public Inputs mapToEntityInputs(InputsDto inputsDto) {
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


    public InputsDto mapToDtoInputs(Inputs inputs) {
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

    public WellSummaryData mapToEntitySummary(WellSummaryDto wellSummaryDto) {
        WellSummaryData wellSummaryData = new WellSummaryData();
        wellSummaryData.setHeatCapacityRequired(wellSummaryDto.getHeatCapacityRequired());
        wellSummaryData.setDeepCapacityRequired(wellSummaryDto.getDeepCapacityRequired());
        wellSummaryData.setHeatInitialInvestment(wellSummaryDto.getHeatInitialInvestmentCapex());
        wellSummaryData.setDeepInitialInvestment(wellSummaryDto.getDeepInitialInvestmentCapex());
        wellSummaryData.setHeatAnnualO_M(wellSummaryDto.getHeatAnnualO_M_Opex());
        wellSummaryData.setDeepAnnualO_M(wellSummaryDto.getDeepAnnualO_M_OPex());
        wellSummaryData.setHeatNpv(wellSummaryDto.getHeatNpv());
        wellSummaryData.setDeepNpv(wellSummaryDto.getDeepNpv());
        wellSummaryData.setHeatIRR(wellSummaryDto.getHeatIRR());
        wellSummaryData.setDeepIRR(wellSummaryDto.getDeepIRR());
        wellSummaryData.setHeatP_I(wellSummaryDto.getHeatP_I());
        wellSummaryData.setDeepP_I(wellSummaryDto.getDeepP_I());
        wellSummaryData.setHeatPaybackPeriod(wellSummaryDto.getHeatPaybackPeriod());
        wellSummaryData.setDeepPaybackPeriod(wellSummaryDto.getDeepPaybackPeriod());
        wellSummaryData.setHeatLcoh(wellSummaryDto.getHeatLcoh());
        wellSummaryData.setDeepLcoh(wellSummaryDto.getDeepLcoh());
        wellSummaryData.setHeatSelected(wellSummaryDto.isHeatSelected());
        wellSummaryData.setDeepSelected(wellSummaryDto.isDeepSelected());
        wellSummaryData.setHeatWells(wellSummaryDto.getHeatWells());
        wellSummaryData.setDeepWells(wellSummaryDto.getDeepWells());
        return wellSummaryData;

    }
    public WellSummaryDto mapToDtoSummary(WellSummaryData wellSummaryData) {
        WellSummaryDto wellSummaryDto = new WellSummaryDto();
        wellSummaryDto.setHeatCapacityRequired(wellSummaryData.getHeatCapacityRequired());
        wellSummaryDto.setDeepCapacityRequired(wellSummaryData.getDeepCapacityRequired());
        wellSummaryDto.setHeatInitialInvestmentCapex(wellSummaryData.getHeatInitialInvestment());
        wellSummaryDto.setDeepInitialInvestmentCapex(wellSummaryData.getDeepInitialInvestment());
        wellSummaryDto.setHeatAnnualO_M_Opex(wellSummaryData.getHeatAnnualO_M());
        wellSummaryDto.setDeepAnnualO_M_OPex(wellSummaryData.getDeepAnnualO_M());
        wellSummaryDto.setHeatNpv(wellSummaryData.getHeatNpv());
        wellSummaryDto.setDeepNpv(wellSummaryData.getDeepNpv());
        wellSummaryDto.setHeatIRR(wellSummaryData.getHeatIRR());
        wellSummaryDto.setDeepIRR(wellSummaryData.getDeepIRR());
        wellSummaryDto.setHeatP_I(wellSummaryData.getHeatP_I());
        wellSummaryDto.setDeepP_I(wellSummaryData.getDeepP_I());
        wellSummaryDto.setHeatPaybackPeriod(wellSummaryData.getHeatPaybackPeriod());
        wellSummaryDto.setDeepPaybackPeriod(wellSummaryData.getDeepPaybackPeriod());
        wellSummaryDto.setHeatLcoh(wellSummaryData.getHeatLcoh());
        wellSummaryDto.setDeepLcoh(wellSummaryData.getDeepLcoh());
        wellSummaryDto.setHeatSelected(wellSummaryData.isHeatSelected());
        wellSummaryDto.setDeepSelected(wellSummaryData.isDeepSelected());
        wellSummaryDto.setHeatWells(wellSummaryData.getHeatWells());
        wellSummaryDto.setDeepWells(wellSummaryData.getDeepWells());
        return wellSummaryDto;

    }


}
