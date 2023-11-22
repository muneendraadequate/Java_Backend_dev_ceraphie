package com.ceraphi.services.Impl;

import com.ceraphi.utils.ENUM.OperationType;
import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.entities.LogEntities.*;
import com.ceraphi.entities.MasterDataTables.*;
import com.ceraphi.repository.*;
import com.ceraphi.repository.LogRepo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DbUpdateService {
    @Autowired
    private ProDataBaseRepository repository;
    @Autowired
    private ChangesSetCapexDeepRepo changesSetCapexDeepRepo;
    @Autowired
    private CapexDeepAuditLogsRepo capexDeepAuditLogsRepo;
    @Autowired
    private ProDataBaseAuditLogRepository logRepository;
    @Autowired
    private OpexDeepChangesSetRepo opexDeepChangesSetRepo;
    @Autowired
    private OpexDeepAuditLogsRepo opexDeepAuditLogsRepo;
    @Autowired
    private EstimatedCostOpexDeepRepo estimatedCostOpexDeepRepo;
   @Autowired
    private CapexHpAuditLogsRepo capexHpAuditLogsRepo;
   @Autowired
   private ChangesSetCapexHpRepo changesSetCapexHpRepo;
    @Autowired
    private ChangeSetRepository changeSetRepository;
    @Autowired
    private ProDataBaseRepository proDataBaseRepository;
    @Autowired
    private EstimatedCostCapexDeepRepo estimatedCostCapexDeepRepo;
    @Autowired
    private EstimatedCostCapexHPRepo estimatedCostCapexHPRepo;


    @Autowired
    private EstimatedCostOpexHPRepo estimatedCostOpexHPRepo;
    @Autowired
   private OpexHpAuditLogsRepo opexHpAuditLogsRepo;
    @Autowired
    private OpexHpChangesSetRepo opexHpChangesSetRepo;





    @Autowired
    private GelDataWellRepo gelDataWellRepo;
    @Autowired
    private GelDataWellChangesSetRepo gelDataWellChangesSetRepo;
    @Autowired
    private GelDataWellAuditLogsRepo gelDataWellAuditLogsRepo;

    @Autowired
    private HeatLoadFuelsRepo heatLoadFuelsRepo;
    @Autowired
    private HeatLoadChangesSetRepo heatLoadChangesSetRepo;
    @Autowired
    private HeatLoadAuditLogsRepo heatLoadAuditLogsRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ChangeLogRepo changeLogRepository;

    public List<ChangeSetDto> getTheChangesSetList() {
        List<ChangeSet> changeSets = changeSetRepository.findAll();
        return changeSets.stream()
                .map(changeSet -> new ChangeSetDto(changeSet.getId(), changeSet.getTimestamp(), changeSet.getComment()))
                .collect(Collectors.toList());
    }
    public List<ChangesSetCapexDeep>getTheCapexDeepChangesList(){return changesSetCapexDeepRepo.findAll();}
    public List<ChangesSetCapexHp>getTheCapexHpChangesList(){return changesSetCapexHpRepo.findAll();}
    public List<OpexDeepChangesSet>getTheOpexDeepChangesList(){return opexDeepChangesSetRepo.findAll();}
    public List<OpexHpChangesSet>getTheOpexHpChangesList(){return opexHpChangesSetRepo.findAll();}
    public List<GelDataWellChangesSet>getTheGellDataChangesList(){return gelDataWellChangesSetRepo.findAll();}
    public List<HeatLoadChangesSet>getTheHeatLoadChangesList(){return heatLoadChangesSetRepo.findAll();}

    public List<ProDataBaseModel> getAllProData() {
        return proDataBaseRepository.findAll();
    }
    public List<EstimatedCostCapexDeep>getAllCapexDeep(){return estimatedCostCapexDeepRepo.findAll();}
    public List<EstimatedCostCapexHP>getAllCapexHp(){return estimatedCostCapexHPRepo.findAll();}
    public List<EstimatedCostOpexDeep>getAllOpexDeep(){return estimatedCostOpexDeepRepo.findAll();}
    public List<EstimatedCostOpexHP>getAllOpexHp(){return estimatedCostOpexHPRepo.findAll();}
    public List<GelDataWell>getAllGelData(){return gelDataWellRepo.findAll();}
    public List<HeatLoadFuels>getAllHeatLoadData(){return heatLoadFuelsRepo.findAll();}

    public ProDataBaseModelDto dataFindById(Long id) {
        ProDataBaseModel proDataBaseModel = proDataBaseRepository.findById(id).get();
        ProDataBaseModelDto proDataBaseModelDto = mapToDtoProData(proDataBaseModel);
        return proDataBaseModelDto;
    }
    public EstimatedCostOpexDeepDto opexDeepDataFindById(Long id) {
        EstimatedCostOpexDeep estimatedCostOpexDeep = estimatedCostOpexDeepRepo.findById(id).get();
        EstimatedCostOpexDeepDto estimatedCostOpexDeepDto = mapToDtoEst_Cost_Opex_Deep(estimatedCostOpexDeep);
        return estimatedCostOpexDeepDto;
    }
    public EstimatedCostCapexHPDto CapexHpDataFindById(Long id) {
        EstimatedCostCapexHP estimatedCostCapexHp = estimatedCostCapexHPRepo.findById(id).get();
        EstimatedCostCapexHPDto estimatedCostCapexHpDto = mapToDtoEst_Cost_Capex_HP(estimatedCostCapexHp);
        return estimatedCostCapexHpDto;
    }
    public EstimatedCostCapexDeepDto CapexDeepDataFindById(Long id) {
        EstimatedCostCapexDeep estimatedCostCapexDeep = estimatedCostCapexDeepRepo.findById(id).get();
        EstimatedCostCapexDeepDto estimatedCostCapexDeepDto = mapToDtoEst_Cost_Capex_Deep(estimatedCostCapexDeep);
        return estimatedCostCapexDeepDto;
    }
    public GelDataWellDto gelDataFindById(Long id) {
        GelDataWell gelDataWell = gelDataWellRepo.findById(id).get();
        GelDataWellDto gelDataWellDto = mapToDtoGel_Data_Well(gelDataWell);
        return gelDataWellDto;
    }
    public EstimatedCostOpexHpDto opexHpDataFindById(Long id) {
        EstimatedCostOpexHP estimatedCostOpexHP = estimatedCostOpexHPRepo.findById(id).get();
        EstimatedCostOpexHpDto estimatedCostOpexHpDto = mapToDtoEst_Cost_Opex_HP(estimatedCostOpexHP);
        return estimatedCostOpexHpDto;
    }
    public List<ProDataBaseModelDto> getTheDataList() {
        List<ProDataBaseModel> all = proDataBaseRepository.findAll();
        return all.stream().map(this::mapToDtoProData).collect(Collectors.toList());

    }
    public List<EstimatedCostCapexDeepDto> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeep> all = estimatedCostCapexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_Deep).collect(Collectors.toList());
    }

    public List<EstimatedCostCapexHPDto> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHP> all = estimatedCostCapexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Capex_HP).collect(Collectors.toList());
    }




    public List<EstimatedCostOpexDeepDto> getTheDataListEst_Cost_Opex_Deep() {
        List<EstimatedCostOpexDeep> all = estimatedCostOpexDeepRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_Deep).collect(Collectors.toList());
    }





    public List<EstimatedCostOpexHpDto> getTheDataListEst_Cost_Opex_HP() {
        List<EstimatedCostOpexHP> all = estimatedCostOpexHPRepo.findAll();
        return all.stream().map(this::mapToDtoEst_Cost_Opex_HP).collect(Collectors.toList());
    }



    public List<GelDataWellDto> getTheDataListGelDataWell() {
        List<GelDataWell> all = gelDataWellRepo.findAll();
        return all.stream().map(this::mapToDtoGel_Data_Well).collect(Collectors.toList());
    }




    public List<HeatLoadFuelsDto> getTheHeatLoadFuelList() {
        List<HeatLoadFuels> all = heatLoadFuelsRepo.findAll();
        return all.stream().map(this::mapToDtoHeatLoadFuel).collect(Collectors.toList());
    }

    public HeatLoadFuelsDto heatLoadDataFindById(Long id) {
        HeatLoadFuels heatLoadFuels = heatLoadFuelsRepo.findById(id).get();
        HeatLoadFuelsDto heatLoadFuelsDto = mapToDtoHeatLoadFuel(heatLoadFuels);
        return heatLoadFuelsDto;
    }



    //updates methods start ============================================================

    //proDatabase update start
@Transactional
public void updateRecords(List<ProDataBaseModelDto> updatedModels) {
    // Create a new change set
    ChangeSet changeSet = new ChangeSet();
    changeSet.setTimestamp(LocalDateTime.now());
    if (!updatedModels.isEmpty()) {
        ProDataBaseModelDto firstModel = updatedModels.get(0);
        changeSet.setComment(firstModel.getComment());
    }

    changeSetRepository.save(changeSet);

    // Update each record and save the corresponding audit logs
    for (ProDataBaseModelDto updatedModel : updatedModels) {
        if (updatedModel.getId() == null) {
            // This is a new record, handle accordingly
            handleNewRecord(updatedModel, changeSet,OperationType.ADD);
        } else {
            // This is an existing record, proceed with your existing logic
            handleExistingRecord(updatedModel, changeSet);
        }
    }
}

    private void handleExistingRecord(ProDataBaseModelDto updatedModel, ChangeSet changeSet) {
        ProDataBaseModel currentModel = repository.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            ProDataBaseModel auditModel = new ProDataBaseModel(currentModel);

            // Update the current version with the new values
            updateModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            repository.save(currentModel);

            // Save the audit log associated with the change set
            saveAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void saveAuditLog(ProDataBaseModel currentModel, ProDataBaseModel auditModel, ChangeSet changeSet, OperationType operationType) {
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
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleNewRecord(ProDataBaseModelDto newRecordDto, ChangeSet changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        ProDataBaseModel newRecord = mapToEntityProData(newRecordDto);

        // Save the new record to the database
        repository.save(newRecord);

        // Save the audit log associated with the change set for the new record
        saveAuditLog(newRecord, null, changeSet,operationType);
    }

    private void updateModelFields(ProDataBaseModel currentModel, ProDataBaseModelDto updatedModel) {
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
    }
//proDatabase base model update end
//==================================================================================================================//

// CAPEX DEEP start update
@Transactional
public void estimated_cost_Capex_Deep(List<EstimatedCostCapexDeepDto> estimatedCostCapexDeepDto) {
    ChangesSetCapexDeep changeSet = new ChangesSetCapexDeep();
    changeSet.setTimestamp(LocalDateTime.now());
    if (!estimatedCostCapexDeepDto.isEmpty()) {
        EstimatedCostCapexDeepDto firstModel = estimatedCostCapexDeepDto.get(0);
        changeSet.setComment(firstModel.getComment());
    }
    changesSetCapexDeepRepo.save(changeSet);

    // Update each record and save the corresponding audit logs
    for (EstimatedCostCapexDeepDto updatedModel : estimatedCostCapexDeepDto) {
        if (updatedModel.getId() == null||updatedModel.getId() == 0) {
            // This is a new record, handle accordingly
            handleCapexDeepNewRecord(updatedModel, changeSet,OperationType.ADD);
        } else {
            // This is an existing record, proceed with your existing logic
            handleCapexDeepExistingRecord(updatedModel, changeSet);
        }
    }
}
    private void handleCapexDeepExistingRecord(EstimatedCostCapexDeepDto updatedModel, ChangesSetCapexDeep changeSet) {
        EstimatedCostCapexDeep estimatedCostCapexDeep = estimatedCostCapexDeepRepo.findById(updatedModel.getId()).orElse(null);

        if (estimatedCostCapexDeep != null) {
            // Create a copy of the current version for auditing
            EstimatedCostCapexDeep auditModel = new EstimatedCostCapexDeep(estimatedCostCapexDeep);

            // Update the current version with the new values
            updateCapexDeepModelFields(estimatedCostCapexDeep, updatedModel);

            // Save the updated entity to create a new version
            estimatedCostCapexDeepRepo.save(estimatedCostCapexDeep);

            // Save the audit log associated with the change set
            saveCapexDeepAuditLog(estimatedCostCapexDeep, auditModel, changeSet,OperationType.EDIT);
        }
    }

    @Transactional
    public void saveCapexDeepAuditLog(EstimatedCostCapexDeep currentModel, EstimatedCostCapexDeep auditModel, ChangesSetCapexDeep changeSet, OperationType operationType) {
        for (Field field : EstimatedCostCapexDeep.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    capexDeepAuditLogsRepo.save(new CapexDeepAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleCapexDeepNewRecord(EstimatedCostCapexDeepDto newRecordDto, ChangesSetCapexDeep changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        EstimatedCostCapexDeep estimatedCostCapexDeep = mapToEntityEst_Cost_Capex_Deep(newRecordDto);

        // Save the new record to the database
        estimatedCostCapexDeepRepo.save(estimatedCostCapexDeep);

        // Save the audit log associated with the change set for the new record
        saveCapexDeepAuditLog(estimatedCostCapexDeep, null, changeSet,operationType);
    }
    private void updateCapexDeepModelFields(EstimatedCostCapexDeep currentModel, EstimatedCostCapexDeepDto updatedModel) {
        currentModel.setCost(updatedModel.getCost());
        currentModel.setOperation(updatedModel.getOperation());
        currentModel.setPerWell(updatedModel.getPerWell());
    }
// CAPEX DEEP end update ================================================
//CAPEX HP start update
@Transactional
public void estimated_cost_Capex_HP(List<EstimatedCostCapexHPDto> updatedModels) {
    // Create a new change set
    ChangesSetCapexHp changeSet = new ChangesSetCapexHp();
    changeSet.setTimestamp(LocalDateTime.now());
    if (!updatedModels.isEmpty()) {
        EstimatedCostCapexHPDto firstModel = updatedModels.get(0);
        changeSet.setComment(firstModel.getComment());
    }
    changesSetCapexHpRepo.save(changeSet);

    // Update each record and save the corresponding audit logs
    for (EstimatedCostCapexHPDto updatedModel : updatedModels) {
        if (updatedModel.getId() == null) {
            // This is a new record, handle accordingly
            handleCapexHpNewRecord(updatedModel, changeSet,OperationType.ADD);
        } else {
            // This is an existing record, proceed with your existing logic
            handleCapexHpExistingRecord(updatedModel, changeSet);
        }
    }
}  private void handleCapexHpExistingRecord(EstimatedCostCapexHPDto updatedModel, ChangesSetCapexHp changeSet) {
        EstimatedCostCapexHP currentModel = estimatedCostCapexHPRepo.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            EstimatedCostCapexHP auditModel = new EstimatedCostCapexHP(currentModel);

            // Update the current version with the new values
            updateCapexHpModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            estimatedCostCapexHPRepo.save(currentModel);

            // Save the audit log associated with the change set
            saveCapexHPAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void saveCapexHPAuditLog(EstimatedCostCapexHP currentModel, EstimatedCostCapexHP auditModel, ChangesSetCapexHp changeSet, OperationType operationType) {
        for (Field field : EstimatedCostCapexHP.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    capexHpAuditLogsRepo.save(new CapexHpAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleCapexHpNewRecord(EstimatedCostCapexHPDto newRecordDto, ChangesSetCapexHp changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        EstimatedCostCapexHP newRecord = mapToEntityEst_Cost_Capex_HP(newRecordDto);

        // Save the new record to the database
        estimatedCostCapexHPRepo.save(newRecord);

        // Save the audit log associated with the change set for the new record
        saveCapexHPAuditLog(newRecord, null, changeSet,operationType);
    }
    private void updateCapexHpModelFields(EstimatedCostCapexHP currentModel, EstimatedCostCapexHPDto updatedModel) {
        currentModel.setCost(updatedModel.getCost());
        currentModel.setOperation(updatedModel.getOperation());
        currentModel.setPerWell(updatedModel.getPerWell());
    }
//Capex HP end update

    //OPEX Deep start update

    @Transactional
//    estimatedCostOpexDeepRepo
//    opexDeepAuditLogsRepo
//    opexDeepChangesSetRepo
    public void estimated_cost_Opex_Deep(List<EstimatedCostOpexDeepDto> updatedModels) {
        // Create a new change set
        OpexDeepChangesSet changeSet = new OpexDeepChangesSet();
        changeSet.setTimestamp(LocalDateTime.now());
        if (!updatedModels.isEmpty()) {
            EstimatedCostOpexDeepDto firstModel = updatedModels.get(0);
            changeSet.setComment(firstModel.getComment());
        }
        opexDeepChangesSetRepo.save(changeSet);

        // Update each record and save the corresponding audit logs
        for (EstimatedCostOpexDeepDto updatedModel : updatedModels) {
            if (updatedModel.getId() == null) {
                // This is a new record, handle accordingly
                OpexDeepHandleNewRecord(updatedModel, changeSet,OperationType.ADD);
            } else {
                // This is an existing record, proceed with your existing logic
                opexDeepHandleExistingRecord(updatedModel, changeSet);
            }
        }
    }
    private void opexDeepHandleExistingRecord(EstimatedCostOpexDeepDto updatedModel, OpexDeepChangesSet changeSet) {
        EstimatedCostOpexDeep currentModel = estimatedCostOpexDeepRepo.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            EstimatedCostOpexDeep auditModel = new EstimatedCostOpexDeep(currentModel);

            // Update the current version with the new values
            opexDeepUpdateModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            estimatedCostOpexDeepRepo.save(currentModel);

            // Save the audit log associated with the change set
            opexDeepSaveAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void opexDeepSaveAuditLog(EstimatedCostOpexDeep currentModel, EstimatedCostOpexDeep auditModel, OpexDeepChangesSet changeSet, OperationType operationType) {
        for (Field field : EstimatedCostOpexDeep.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    opexDeepAuditLogsRepo.save(new OpexDeepAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void OpexDeepHandleNewRecord(EstimatedCostOpexDeepDto newRecordDto, OpexDeepChangesSet changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        EstimatedCostOpexDeep newRecord = mapToEntityEst_Cost_Opex_Deep(newRecordDto);

        // Save the new record to the database
        estimatedCostOpexDeepRepo.save(newRecord);

        // Save the audit log associated with the change set for the new record
        opexDeepSaveAuditLog(newRecord, null, changeSet,operationType);
    }

    private void opexDeepUpdateModelFields(EstimatedCostOpexDeep currentModel, EstimatedCostOpexDeepDto updatedModel) {
            currentModel.setCost(updatedModel.getCost());
            currentModel.setOperation(updatedModel.getOperation());
            currentModel.setPerWell(updatedModel.getPerWell());
        }
        // Update other fields as needed...



    //OPEX Deep end update


//Opex Hp start update
    @Transactional
//    estimatedCostOpexHPRepo
//    opexHpAuditLogsRepo
//    opexHpChangesSetRepo
    public void estimated_cost_Opex_HP(List<EstimatedCostOpexHpDto> updatedModels) {
        // Create a new change set
        OpexHpChangesSet changeSet = new OpexHpChangesSet();
        changeSet.setTimestamp(LocalDateTime.now());
        if (!updatedModels.isEmpty()) {
            EstimatedCostOpexHpDto firstModel = updatedModels.get(0);
            changeSet.setComment(firstModel.getComment());
        }
        opexHpChangesSetRepo.save(changeSet);

        // Update each record and save the corresponding audit logs
        for (EstimatedCostOpexHpDto updatedModel : updatedModels) {
            if (updatedModel.getId() == null) {
                // This is a new record, handle accordingly
                opexHpHandleNewRecord(updatedModel, changeSet,OperationType.ADD);
            } else {
                // This is an existing record, proceed with your existing logic
                opexHpHandleExistingRecord(updatedModel, changeSet);
            }
        }
    }

    private void opexHpHandleExistingRecord(EstimatedCostOpexHpDto updatedModel, OpexHpChangesSet changeSet) {
        EstimatedCostOpexHP currentModel = estimatedCostOpexHPRepo.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            EstimatedCostOpexHP auditModel = new EstimatedCostOpexHP(currentModel);

            // Update the current version with the new values
            opexHpUpdateModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            estimatedCostOpexHPRepo.save(currentModel);

            // Save the audit log associated with the change set
            opexHpSaveAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void opexHpSaveAuditLog(EstimatedCostOpexHP currentModel, EstimatedCostOpexHP auditModel, OpexHpChangesSet changeSet, OperationType operationType) {
        for (Field field : EstimatedCostOpexHP.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    opexHpAuditLogsRepo.save(new OpexHpAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void opexHpHandleNewRecord(EstimatedCostOpexHpDto newRecordDto, OpexHpChangesSet changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        EstimatedCostOpexHP newRecord = mapToEntityEst_Cost_Opex_Hp(newRecordDto);

        // Save the new record to the database
        estimatedCostOpexHPRepo.save(newRecord);

        // Save the audit log associated with the change set for the new record
        opexHpSaveAuditLog(newRecord, null, changeSet,operationType);
    }

    private void opexHpUpdateModelFields(EstimatedCostOpexHP currentModel, EstimatedCostOpexHpDto updatedModel) {
            currentModel.setCost(updatedModel.getCost());
            currentModel.setOperation(updatedModel.getOperation());
            currentModel.setPerWell(updatedModel.getPerWell());
        }
        // Update other fields as needed...

    //Opex Hp end update
//GelDataWell start update
//    gelDataWellRepo
//    gelDataWellChangesSetRepo
//    gelDataWellAuditLogsRepo
    @Transactional
    public void gelDataWell(List<GelDataWellDto> updatedModels) {
        // Create a new change set
        GelDataWellChangesSet changeSet = new GelDataWellChangesSet();
        changeSet.setTimestamp(LocalDateTime.now());
        if (!updatedModels.isEmpty()) {
            GelDataWellDto firstModel = updatedModels.get(0);
            changeSet.setComment(firstModel.getComment());
        }
        gelDataWellChangesSetRepo.save(changeSet);

        // Update each record and save the corresponding audit logs
        for (GelDataWellDto updatedModel : updatedModels) {
            if (updatedModel.getId() == null) {
                // This is a new record, handle accordingly
                gelDataWellHandleNewRecord(updatedModel, changeSet,OperationType.ADD);
            } else {
                // This is an existing record, proceed with your existing logic
                gelDataWellHandleExistingRecord(updatedModel, changeSet);
            }
        }
    }

    private void gelDataWellHandleExistingRecord(GelDataWellDto updatedModel, GelDataWellChangesSet changeSet) {
        GelDataWell currentModel = gelDataWellRepo.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            GelDataWell auditModel = new GelDataWell(currentModel);

            // Update the current version with the new values
            gelDataWellUpdateModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            gelDataWellRepo.save(currentModel);

            // Save the audit log associated with the change set
            gelDataWellSaveAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void gelDataWellSaveAuditLog(GelDataWell currentModel, GelDataWell auditModel, GelDataWellChangesSet changeSet, OperationType operationType) {
        for (Field field : GelDataWell.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    gelDataWellAuditLogsRepo.save(new GelDataWellAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void gelDataWellHandleNewRecord(GelDataWellDto newRecordDto, GelDataWellChangesSet changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        GelDataWell newRecord = mapToEntityGel_Data_Well(newRecordDto);

        // Save the new record to the database
        gelDataWellRepo.save(newRecord);

        // Save the audit log associated with the change set for the new record
        gelDataWellSaveAuditLog(newRecord, null, changeSet,operationType);
    }

    private void gelDataWellUpdateModelFields(GelDataWell currentModel, GelDataWellDto updatedModel) {
        currentModel.setTempRequired(updatedModel.getTempRequired());
        currentModel.setFlowRate(updatedModel.getFlowRate());
        currentModel.setCOP(updatedModel.getCOP());
        currentModel.setWellOutletTemp(updatedModel.getWellOutletTemp());
        currentModel.setCapacity(updatedModel.getCapacity());

        // Update other fields as needed...
    }


//GelDataWell end update
//HeatLoadFuel start update
@Transactional
//    heatLoadAuditLogsRepo
//    heatLoadChangesSetRepo
//    heatLoadFuelsRepo
public void heatLoadFuelsData(List<HeatLoadFuelsDto> updatedModels) {
    // Create a new change set
    HeatLoadChangesSet changeSet = new HeatLoadChangesSet();
    changeSet.setTimestamp(LocalDateTime.now());
    if (!updatedModels.isEmpty()) {
        HeatLoadFuelsDto firstModel = updatedModels.get(0);
        changeSet.setComment(firstModel.getComment());
    }
    heatLoadChangesSetRepo.save(changeSet);

    // Update each record and save the corresponding audit logs
    for (HeatLoadFuelsDto updatedModel : updatedModels) {
        if (updatedModel.getId() == null) {
            // This is a new record, handle accordingly
            heatLoadHandleNewRecord(updatedModel, changeSet,OperationType.ADD);
        } else {
            // This is an existing record, proceed with your existing logic
            heatLoadHandleExistingRecord(updatedModel, changeSet);
        }
    }
}

    private void heatLoadHandleExistingRecord(HeatLoadFuelsDto updatedModel, HeatLoadChangesSet changeSet) {
        HeatLoadFuels currentModel = heatLoadFuelsRepo.findById(updatedModel.getId()).orElse(null);

        if (currentModel != null) {
            // Create a copy of the current version for auditing
            HeatLoadFuels auditModel = new HeatLoadFuels(currentModel);

            // Update the current version with the new values
            heatLoadUpdateModelFields(currentModel, updatedModel);

            // Save the updated entity to create a new version
            heatLoadFuelsRepo.save(currentModel);

            // Save the audit log associated with the change set
            HeatLoadSaveAuditLog(currentModel, auditModel, changeSet,OperationType.EDIT);
        }
    }
    @Transactional
    public void HeatLoadSaveAuditLog(HeatLoadFuels currentModel, HeatLoadFuels auditModel, HeatLoadChangesSet changeSet, OperationType operationType) {
        for (Field field : HeatLoadFuels.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object currentValue = field.get(currentModel);
                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;

                if (!Objects.equals(currentValue, auditValue)) {
                    heatLoadAuditLogsRepo.save(new HeatLoadAuditLogs(
                            null,
                            currentModel,
                            changeSet,
                            field.getName(),
                            (auditValue != null) ? auditValue.toString() : null,
                            (currentValue != null) ? currentValue.toString() : null,
                            LocalDateTime.now(),
                            operationType
                    ));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    private void heatLoadHandleNewRecord(HeatLoadFuelsDto newRecordDto, HeatLoadChangesSet changeSet,OperationType operationType) {
        // Create a new ProDataBaseModel entity for the new record
        HeatLoadFuels newRecord = mapToEntityHeatLoadFuel(newRecordDto);

        // Save the new record to the database
        heatLoadFuelsRepo.save(newRecord);

        // Save the audit log associated with the change set for the new record
        HeatLoadSaveAuditLog(newRecord, null, changeSet,operationType);
    }

    private void heatLoadUpdateModelFields(HeatLoadFuels currentModel, HeatLoadFuelsDto updatedModel) {
        currentModel.setFuelType(updatedModel.getFuelType());
        currentModel.setEfficiency(updatedModel.getEfficiency());
        currentModel.setCarbon(updatedModel.getCarbon());
        currentModel.setNox(updatedModel.getNox());
        currentModel.setNoxN(updatedModel.getNoxN());
        currentModel.setGhg(updatedModel.getGhg());

        // Update other fields as needed...
    }
//HeatLoadFuel end update





//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// restore methods start

//proDatabase base model restore start
@Transactional
public void revertChangeSet(Long changeSetId) {
    // Fetch all logs for the change set
    List<ProDataBaseAuditLog> auditLogs = logRepository.findByChangeSetId(changeSetId);

    // Revert each change in the change set
    for (ProDataBaseAuditLog auditLog : auditLogs) {
        ProDataBaseModel currentModel = repository.findById(auditLog.getProdata().getId()).orElse(null);

        if (currentModel != null) {
            if (auditLog.getOperationType() == OperationType.ADD) {
                // If it's an ADD operation, delete the added record
                repository.deleteById(currentModel.getId());
            } else {
                // If it's an EDIT operation, restore the historical version
                restoreHistoricalVersion(currentModel, auditLog);
            }
        }
    }
    changeSetRepository.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(ProDataBaseModel currentModel, ProDataBaseAuditLog auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
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
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            repository.delete(currentModel);
        }

        // Save the restored entity
        repository.save(currentModel);
    }

//proDatabase restore end
    //============================================================================================================================

// CapexDeep start Restore =========================================


    @Transactional
    public void restoreDeepCapexChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<CapexDeepAuditLogs> auditLogs = capexDeepAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (CapexDeepAuditLogs auditLog : auditLogs) {
            EstimatedCostCapexDeep currentModel = estimatedCostCapexDeepRepo.findById(auditLog.getCostCapexDeep().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    estimatedCostCapexDeepRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
        changesSetCapexDeepRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(EstimatedCostCapexDeep currentModel, CapexDeepAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "operation":
                    currentModel.setOperation(auditLog.getOldValue());
                    break;
                case "cost":
                    currentModel.setCost(Double.parseDouble(auditLog.getOldValue()));
                    break;
                case "perWell":
                    currentModel.setPerWell(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            estimatedCostCapexDeepRepo.delete(currentModel);
        }

        // Save the restored entity
        estimatedCostCapexDeepRepo.save(currentModel);
    }

    // CapexDeep end Restore =========================================
   //CapexHp start Restore =========================================

    @Transactional
    public void revertCapexHpChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<CapexHpAuditLogs> auditLogs = capexHpAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (CapexHpAuditLogs auditLog : auditLogs) {
            EstimatedCostCapexHP currentModel = estimatedCostCapexHPRepo.findById(auditLog.getCostCapexHp().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    estimatedCostCapexHPRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
        changesSetCapexHpRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(EstimatedCostCapexHP currentModel, CapexHpAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "operation":
                    currentModel.setOperation(auditLog.getOldValue());
                    break;
                case "cost":
                    currentModel.setCost(Double.parseDouble(auditLog.getOldValue()));
                    break;
                case "perWell":
                    currentModel.setPerWell(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            estimatedCostCapexHPRepo.delete(currentModel);
        }

        // Save the restored entity
        estimatedCostCapexHPRepo.save(currentModel);
    }
//CapexHp end Restore =========================================

    //Opex Deep restore start=============================================
    @Transactional
    public void revertOpexDeepChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<OpexDeepAuditLogs> auditLogs = opexDeepAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (OpexDeepAuditLogs auditLog : auditLogs) {
            EstimatedCostOpexDeep currentModel = estimatedCostOpexDeepRepo.findById(auditLog.getCostCapexHp().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    estimatedCostOpexDeepRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
       opexDeepChangesSetRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(EstimatedCostOpexDeep currentModel, OpexDeepAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "operation":
                    currentModel.setOperation(auditLog.getOldValue());
                    break;
                case "cost":
                    currentModel.setCost(Double.parseDouble(auditLog.getOldValue()));
                    break;
                case "perWell":
                    currentModel.setPerWell(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            estimatedCostOpexDeepRepo.delete(currentModel);
        }

        // Save the restored entity
        estimatedCostOpexDeepRepo.save(currentModel);
    }
//Opex Deep restore end===============================================
//Opex Hp restore start===============================================
    @Transactional
    public void revertOpexHpChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<OpexHpAuditLogs> auditLogs = opexHpAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (OpexHpAuditLogs auditLog : auditLogs) {
            EstimatedCostOpexHP currentModel = estimatedCostOpexHPRepo.findById(auditLog.getCostCapexHp().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    estimatedCostOpexHPRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
        opexHpChangesSetRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(EstimatedCostOpexHP currentModel, OpexHpAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "operation":
                    currentModel.setOperation(auditLog.getOldValue());
                    break;
                case "cost":
                    currentModel.setCost(Double.parseDouble(auditLog.getOldValue()));
                    break;
                case "perWell":
                    currentModel.setPerWell(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            estimatedCostOpexHPRepo.delete(currentModel);
        }

        // Save the restored entity
        estimatedCostOpexHPRepo.save(currentModel);
    }
    //Opex Hp restore end===============================================

    //Gel Data restore start======================================================
    @Transactional
    public void revertGelDataChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<GelDataWellAuditLogs> auditLogs = gelDataWellAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (GelDataWellAuditLogs auditLog : auditLogs) {
            GelDataWell currentModel = gelDataWellRepo.findById(auditLog.getGelDataWell().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    gelDataWellRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
        gelDataWellChangesSetRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(GelDataWell currentModel, GelDataWellAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "cop":
                    currentModel.setCOP(auditLog.getOldValue());
                    break;
                case "capacity":
                    currentModel.setCapacity(auditLog.getOldValue());
                    break;
                case "flow_rate":
                    currentModel.setFlowRate(auditLog.getOldValue());
                    break;
                    case "temp_required":
                    currentModel.setTempRequired(auditLog.getOldValue());
                    break;
                    case "well_outlet_temp":
                    currentModel.setWellOutletTemp(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            gelDataWellRepo.delete(currentModel);
        }

        // Save the restored entity
        gelDataWellRepo.save(currentModel);
    }
    //gel data restore end======================================================
    //HeatLoad Data restore start======================================================
    @Transactional
    public void revertHeatLoadDataChangeSet(Long changeSetId) {
        // Fetch all logs for the change set
        List<HeatLoadAuditLogs> auditLogs = heatLoadAuditLogsRepo.findByChangeSetId(changeSetId);

        // Revert each change in the change set
        for (HeatLoadAuditLogs auditLog : auditLogs) {
            HeatLoadFuels currentModel = heatLoadFuelsRepo.findById(auditLog.getHeatLoadFuels().getId()).orElse(null);

            if (currentModel != null) {
                if (auditLog.getOperationType() == OperationType.ADD) {
                    // If it's an ADD operation, delete the added record
                    heatLoadFuelsRepo.deleteById(currentModel.getId());
                } else {
                    // If it's an EDIT operation, restore the historical version
                    restoreHistoricalVersion(currentModel, auditLog);
                }
            }
        }
        heatLoadChangesSetRepo.deleteById(changeSetId);
    }
    private void restoreHistoricalVersion(HeatLoadFuels currentModel, HeatLoadAuditLogs auditLog) {
        if (auditLog.getOperationType() == OperationType.EDIT) {
            // Restore each field to its historical value for EDIT operation
            switch (auditLog.getFieldName()) {
                case "carbon":
                    currentModel.setCarbon(auditLog.getOldValue());
                    break;
                case "efficiency":
                    currentModel.setEfficiency(auditLog.getOldValue());
                    break;
                case "fuel_type":
                    currentModel.setFuelType(auditLog.getOldValue());
                    break;
                case "ghg":
                    currentModel.setGhg(auditLog.getOldValue());
                    break;
                case "nox":
                    currentModel.setNox(auditLog.getOldValue());
                    break;
                case "noxn":
                    currentModel.setNoxN(auditLog.getOldValue());
                    break;
//            // Add cases for other fields as needed
            }
        } else if (auditLog.getOperationType() == OperationType.ADD) {
            // Handle ADD operation (removing newly added row)
            // This could involve deleting the row or resetting the fields to default values
            heatLoadFuelsRepo.delete(currentModel);
        }

        // Save the restored entity
        heatLoadFuelsRepo.save(currentModel);
    }
    //HeatLoad Data restore end======================================================
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
