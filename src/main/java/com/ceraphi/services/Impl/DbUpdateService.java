package com.ceraphi.services.Impl;

import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.entities.Demo.ChangeSet;
import com.ceraphi.entities.Demo.ProDataBaseAuditLog;
import com.ceraphi.entities.MasterDataTables.*;
import com.ceraphi.repository.*;
import com.ceraphi.repository.DemoRepo.ChangeSetRepository;
import com.ceraphi.repository.DemoRepo.ProDataBaseAuditLogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DbUpdateService {
    @Autowired
    private ProDataBaseRepository repository;

    @Autowired
    private ProDataBaseAuditLogRepository logRepository;

    @Autowired
    private ChangeSetRepository changeSetRepository;
    @Autowired
    private ProDataBaseRepository proDataBaseRepository;
    @Autowired
    private EstimatedCostCapexDeepRepo estimatedCostCapexDeepRepo;
    @Autowired
    private EstimatedCostCapexHPRepo estimatedCostCapexHPRepo;

    @Autowired
    private EstimatedCostOpexDeepRepo estimatedCostOpexDeepRepo;
    @Autowired
    private EstimatedCostOpexHPRepo estimatedCostOpexHPRepo;
    @Autowired
    private GelDataWellRepo gelDataWellRepo;
    @Autowired
    private HeatLoadFuelsRepo heatLoadFuelsRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ChangeLogRepo changeLogRepository;
    public void addNewData(ProDataBaseModelDto proDataBaseModelDto) {
        ProDataBaseModel proDataBaseModel = mapToEntityProData(proDataBaseModelDto);
        proDataBaseRepository.save(proDataBaseModel);
    }
    @Transactional
    public void updateExistingData(ProDataBaseModelDto proDataBaseModelDto) {
        ProDataBaseModel proDataBaseModel = mapToEntityProData(proDataBaseModelDto);
        proDataBaseRepository.save(proDataBaseModel);
    }

    public List<ProDataBaseModelDto> getTheDataList() {
        List<ProDataBaseModel> all = proDataBaseRepository.findAll();
        return all.stream().map(this::mapToDtoProData).collect(Collectors.toList());

    }

    @Transactional
    public void estimated_cost_Capex_Deep(EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        EstimatedCostCapexDeep estimatedCostCapexDeep = mapToEntityEst_Cost_Capex_Deep(estimatedCostCapexDeepDto);
        estimatedCostCapexDeepRepo.save(estimatedCostCapexDeep);
    }

    public List<EstimatedCostCapexDeepDto> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeep> all = estimatedCostCapexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_Deep).collect(Collectors.toList());
    }


    @Transactional
    public void estimated_cost_Capex_HP(EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
        EstimatedCostCapexHP estimatedCostCapexHP = mapToEntityEst_Cost_Capex_HP(estimatedCostCapexHPDto);
        estimatedCostCapexHPRepo.save(estimatedCostCapexHP);
    }

    public List<EstimatedCostCapexHPDto> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHP> all = estimatedCostCapexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_HP).collect(Collectors.toList());
    }


    @Transactional
    public void estimated_cost_Opex_Deep(EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
        EstimatedCostOpexDeep estimatedCostOpexDeep = mapToEntityEst_Cost_Opex_Deep(estimatedCostOpexDeepDto);
        estimatedCostOpexDeepRepo.save(estimatedCostOpexDeep);
    }

    public List<EstimatedCostOpexDeepDto> getTheDataListEst_Cost_Opex_Deep() {
        List<EstimatedCostOpexDeep> all = estimatedCostOpexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_Deep).collect(Collectors.toList());
    }
    @Transactional
    public void estimated_cost_Opex_HP(EstimatedCostOpexHpDto estimatedCostOpexHpDto) {
        EstimatedCostOpexHP estimatedCostOpexHp = mapToEntityEst_Cost_Opex_Hp(estimatedCostOpexHpDto);
        estimatedCostOpexHPRepo.save(estimatedCostOpexHp);
    }

    public List<EstimatedCostOpexHpDto> getTheDataListEst_Cost_Opex_HP() {
        List<EstimatedCostOpexHP> all = estimatedCostOpexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_HP).collect(Collectors.toList());
    }

    @Transactional
    public void gelDataWell(GelDataWellDto gelDataWellDto) {
        GelDataWell gelDataWell = mapToEntityGel_Data_Well(gelDataWellDto);
        gelDataWellRepo.save(gelDataWell);
    }

    public List<GelDataWellDto> getTheDataListGelDataWell() {
        List<GelDataWell> all = gelDataWellRepo.findAll();
        return all.stream().map(this::mapToDtoGel_Data_Well).collect(Collectors.toList());
    }



    @Transactional
    public void heatLoadFuelsData(HeatLoadFuelsDto heatLoadFuelsDto) {
        HeatLoadFuels heatLoadFuels = mapToEntityHeatLoadFuel(heatLoadFuelsDto);
        heatLoadFuelsRepo.save(heatLoadFuels);
    }

    public List<HeatLoadFuelsDto> getTheHeatLoadFuelList() {
        List<HeatLoadFuels> all = heatLoadFuelsRepo.findAll();
        return all.stream().map(this::mapToDtoHeatLoadFuel).collect(Collectors.toList());
    }

    @Transactional
    public void updateRecords(List<ProDataBaseModelDto> updatedModels) {
        // Create a new change set
        ChangeSet changeSet = new ChangeSet();
        changeSet.setTimestamp(LocalDateTime.now());
        changeSetRepository.save(changeSet);
        List<ProDataBaseModel> entityList = updatedModels.stream()
                .map(this::mapToEntityProData)
                .collect(Collectors.toList());


        // Update each record and save the corresponding audit logs
        for (ProDataBaseModel updatedModel : entityList) {
            ProDataBaseModel currentModel = repository.findById(updatedModel.getId()).orElse(null);

            if (currentModel != null) {
                // Create a copy of the current version for auditing
                ProDataBaseModel auditModel = new ProDataBaseModel(currentModel);

                // Update the current version with the new values
                currentModel.setGeothermalGradient(updatedModel.getGeothermalGradient());
                currentModel.setSteadyStateTemp(updatedModel.getSteadyStateTemp());
                currentModel.setKWt(updatedModel.getKWt());
                currentModel.setFlowRate(updatedModel.getFlowRate());
                currentModel.setPumpingPower(updatedModel.getPumpingPower());
                currentModel.setDepth(updatedModel.getDepth());
                currentModel.setDelta(updatedModel.getDelta());
                currentModel.setPressureLoss(updatedModel.getPressureLoss());
                currentModel.setBHT(updatedModel.getBHT());
                currentModel.setReturnValue(updatedModel.getReturnValue());






                // Update other fields as needed...

                // Save the updated entity to create a new version
                repository.save(currentModel);

                // Save the audit log associated with the change set
                saveAuditLog(currentModel, auditModel, changeSet);
            }
        }
    }
    @Transactional
    public void saveAuditLog(ProDataBaseModel currentModel, ProDataBaseModel auditModel, ChangeSet changeSet) {
        for (Field field : ProDataBaseModel.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    logRepository.save(new ProDataBaseAuditLog(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now()
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    @Transactional
    public void revertChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<ProDataBaseAuditLog> auditLogs = logRepository.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (ProDataBaseAuditLog auditLog : auditLogs) {
            ProDataBaseModel currentModel = repository.findById(auditLog.getProdata().getId()).orElse(null);

            if (currentModel != null) {
                // Restore the historical version
                restoreHistoricalVersion(currentModel, auditLog);
            }
        }

        // Delete the change set and associated logs
        changeSetRepository.deleteById(changeSetId);
    }

    private void restoreHistoricalVersion(ProDataBaseModel currentModel, ProDataBaseAuditLog auditLog) {
        // Restore each field to its historical value
        switch (auditLog.getFieldName()) {
            case "geothermalGradient":
                currentModel.setGeothermalGradient(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "steadyStateTemp":
                currentModel.setSteadyStateTemp(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "kWt":
                currentModel.setKWt(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "flowRate":
                currentModel.setFlowRate(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "pumpingPower":
                currentModel.setPumpingPower(Double.parseDouble(auditLog.getOldValue()));
                break;
            case "depth":
                currentModel.setDepth(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "delta":
                currentModel.setDelta(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "pressureLoss":
                currentModel.setPressureLoss(Double.parseDouble(auditLog.getOldValue()));
                break;
            case "BHT":
                currentModel.setBHT(Integer.parseInt(auditLog.getOldValue()));
                break;
            case "returnValue":
                currentModel.setReturnValue(Double.parseDouble(auditLog.getOldValue()));
                break;
            // Add cases for other fields as needed
        }

        // Save the restored entity
        repository.save(currentModel);
    }





    //===================================Model Mappers================================

    public ProDataBaseModel mapToEntityProData(ProDataBaseModelDto proDataBaseModelDto) {
        ProDataBaseModel proDataBaseModel = modelMapper.map(proDataBaseModelDto, ProDataBaseModel.class);
        return proDataBaseModel;
    }

    public ProDataBaseModelDto mapToDtoProData(ProDataBaseModel proDataBaseModel) {
        ProDataBaseModelDto proDataBaseModelDto = new ProDataBaseModelDto();
        proDataBaseModelDto.setId(proDataBaseModel.getId());
        proDataBaseModelDto = modelMapper.map(proDataBaseModel, ProDataBaseModelDto.class);
        return proDataBaseModelDto;
    }

    //===============================Estimated Cost Capex Deep=======================
    public EstimatedCostCapexDeep mapToEntityEst_Cost_Capex_Deep(EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        EstimatedCostCapexDeep est_cost_capex_deep = modelMapper.map(estimatedCostCapexDeepDto, EstimatedCostCapexDeep.class);
        return est_cost_capex_deep;
    }

    public EstimatedCostCapexDeepDto mapToDtoEst_Cost_Capex_Deep(EstimatedCostCapexDeep est_cost_capex_deep) {
        EstimatedCostCapexDeepDto estimatedCostCapexDeepDto = new EstimatedCostCapexDeepDto();
        estimatedCostCapexDeepDto.setId(est_cost_capex_deep.getId());
        estimatedCostCapexDeepDto = modelMapper.map(est_cost_capex_deep, EstimatedCostCapexDeepDto.class);
        return estimatedCostCapexDeepDto;
    }
    //=================================Estimated Cost Capex HP ================

    public EstimatedCostCapexHP mapToEntityEst_Cost_Capex_HP(EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
        EstimatedCostCapexHP est_cost_capex_HP = modelMapper.map(estimatedCostCapexHPDto, EstimatedCostCapexHP.class);
        return est_cost_capex_HP;
    }

    public EstimatedCostCapexHPDto mapToDtoEst_Cost_Capex_HP(EstimatedCostCapexHP est_cost_capex_HP) {
        EstimatedCostCapexHPDto estimatedCostCapexHPDto = new EstimatedCostCapexHPDto();
        estimatedCostCapexHPDto.setId(est_cost_capex_HP.getId());
        estimatedCostCapexHPDto = modelMapper.map(est_cost_capex_HP, EstimatedCostCapexHPDto.class);
        return estimatedCostCapexHPDto;
    }

    //=================================Estimated Cost Opex Deep ================
    public EstimatedCostOpexDeep mapToEntityEst_Cost_Opex_Deep(EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
        EstimatedCostOpexDeep est_cost_Opex_Deep = modelMapper.map(estimatedCostOpexDeepDto, EstimatedCostOpexDeep.class);
        return est_cost_Opex_Deep;
    }

    public EstimatedCostOpexDeepDto mapToDtoEst_Cost_Opex_Deep(EstimatedCostOpexDeep est_cost_opex_Deep) {
        EstimatedCostOpexDeepDto estimatedCostOpexDeepDto = new EstimatedCostOpexDeepDto();
        estimatedCostOpexDeepDto.setId(est_cost_opex_Deep.getId());
        estimatedCostOpexDeepDto = modelMapper.map(est_cost_opex_Deep, EstimatedCostOpexDeepDto.class);
        return estimatedCostOpexDeepDto;
    }
    //=================================Estimated Cost Opex HP ================

    public EstimatedCostOpexHP mapToEntityEst_Cost_Opex_Hp(EstimatedCostOpexHpDto estimatedCostOpexHPDto) {
        EstimatedCostOpexHP est_cost_Opex_Hp = modelMapper.map(estimatedCostOpexHPDto, EstimatedCostOpexHP.class);
        return est_cost_Opex_Hp;
    }

    public EstimatedCostOpexHpDto mapToDtoEst_Cost_Opex_HP(EstimatedCostOpexHP est_cost_opex_HP) {
        EstimatedCostOpexHpDto estimatedCostOpexHpDto = new EstimatedCostOpexHpDto();
        estimatedCostOpexHpDto.setId(est_cost_opex_HP.getId());
        estimatedCostOpexHpDto = modelMapper.map(est_cost_opex_HP, EstimatedCostOpexHpDto.class);
        return estimatedCostOpexHpDto;
    }
//=======================================Gel Data Well=====================================================
    public GelDataWell mapToEntityGel_Data_Well(GelDataWellDto gelDataWellDto) {
        GelDataWell gelDataWell = modelMapper.map(gelDataWellDto, GelDataWell.class);
        return gelDataWell;
    }

    public GelDataWellDto mapToDtoGel_Data_Well(GelDataWell gelDataWell) {
        GelDataWellDto gelDataWellDto = new GelDataWellDto();
        gelDataWellDto.setId(gelDataWell.getId());
        gelDataWellDto = modelMapper.map(gelDataWell, GelDataWellDto.class);
        return gelDataWellDto;
    }
    //============================Heat Load Fuel=======================================================
    public HeatLoadFuels mapToEntityHeatLoadFuel(HeatLoadFuelsDto heatLoadFuelsDto) {
        HeatLoadFuels heatLoadFuels = modelMapper.map(heatLoadFuelsDto, HeatLoadFuels.class);
        return heatLoadFuels;
    }

    public HeatLoadFuelsDto mapToDtoHeatLoadFuel(HeatLoadFuels heatLoadFuels) {
        HeatLoadFuelsDto heatLoadFuelsDto = new HeatLoadFuelsDto();
        heatLoadFuelsDto.setId(heatLoadFuels.getId());
        heatLoadFuelsDto = modelMapper.map(heatLoadFuels, HeatLoadFuelsDto.class);
        return heatLoadFuelsDto;
    }


}
