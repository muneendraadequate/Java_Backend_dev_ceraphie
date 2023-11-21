//package com.ceraphi.services.Impl;
//
//import com.ceraphi.entities.Demo.ChangeSet;
//import com.ceraphi.entities.Demo.ProDataBaseAuditLog;
//import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
//import com.ceraphi.repository.DemoRepo.ChangeSetRepository;
//import com.ceraphi.repository.DemoRepo.ProDataBaseAuditLogRepository;
//import com.ceraphi.repository.ProDataBaseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//
//@Service
//public class DemoService {
//    @Autowired
//    private ProDataBaseRepository repository;
//
//    @Autowired
//    private ProDataBaseAuditLogRepository logRepository;
//
//    @Autowired
//    private ChangeSetRepository changeSetRepository;
//
//    @Transactional
//    public ProDataBaseModel save(ProDataBaseModel model) {
//        return repository.save(model);
//    }
//
//    @Transactional
//    public void updateRecords(List<ProDataBaseModel> updatedModels) {
//        // Create a new change set
//        ChangeSet changeSet = new ChangeSet();
//        changeSet.setTimestamp(LocalDateTime.now());
//        changeSetRepository.save(changeSet);
//
//        // Update each record and save the corresponding audit logs
//        for (ProDataBaseModel updatedModel : updatedModels) {
//            ProDataBaseModel currentModel = repository.findById(updatedModel.getId()).orElse(null);
//
//            if (currentModel != null) {
//                // Create a copy of the current version for auditing
//                ProDataBaseModel auditModel = new ProDataBaseModel(currentModel);
//
//                // Update the current version with the new values
//                currentModel.setGeothermalGradient(updatedModel.getGeothermalGradient());
//                currentModel.setSteadyStateTemp(updatedModel.getSteadyStateTemp());
//                currentModel.setKWt(updatedModel.getKWt());
//                currentModel.setFlowRate(updatedModel.getFlowRate());
//                currentModel.setPumpingPower(updatedModel.getPumpingPower());
//                currentModel.setDepth(updatedModel.getDepth());
//                currentModel.setDelta(updatedModel.getDelta());
//                currentModel.setPressureLoss(updatedModel.getPressureLoss());
//                currentModel.setBHT(updatedModel.getBHT());
//                currentModel.setReturnValue(updatedModel.getReturnValue());
//
//
//
//
//
//
//                // Update other fields as needed...
//
//                // Save the updated entity to create a new version
//                repository.save(currentModel);
//
//                // Save the audit log associated with the change set
//                saveAuditLog(currentModel, auditModel, changeSet);
//            }
//        }
//    }
//    @Transactional
//    public void saveAuditLog(ProDataBaseModel currentModel, ProDataBaseModel auditModel, ChangeSet changeSet) {
//        for (Field field : ProDataBaseModel.class.getDeclaredFields()) {
//            try {
//                field.setAccessible(true);
//                Object currentValue = field.get(currentModel);
//                Object auditValue = (auditModel != null) ? field.get(auditModel) : null;
//
//                if (!Objects.equals(currentValue, auditValue)) {
//                    logRepository.save(new ProDataBaseAuditLog(
//                            null,
//                            currentModel,
//                            changeSet,
//                            field.getName(),
//                            (auditValue != null) ? auditValue.toString() : null,
//                            (currentValue != null) ? currentValue.toString() : null,
//                            LocalDateTime.now()
//
//                    ));
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    @Transactional
//    public void revertChangeSet(Long changeSetId) {
//        // Fetch all logs for the change set
//        List<ProDataBaseAuditLog> auditLogs = logRepository.findByChangeSetId(changeSetId);
//
//        // Revert each change in the change set
//        for (ProDataBaseAuditLog auditLog : auditLogs) {
//            ProDataBaseModel currentModel = repository.findById(auditLog.getProdata().getId()).orElse(null);
//
//            if (currentModel != null) {
//                // Restore the historical version
//                restoreHistoricalVersion(currentModel, auditLog);
//            }
//        }
//
//        // Delete the change set and associated logs
//        changeSetRepository.deleteById(changeSetId);
//    }
//
//    private void restoreHistoricalVersion(ProDataBaseModel currentModel, ProDataBaseAuditLog auditLog) {
//        // Restore each field to its historical value
//        switch (auditLog.getFieldName()) {
//            case "geothermalGradient":
//                currentModel.setGeothermalGradient(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "steadyStateTemp":
//                currentModel.setSteadyStateTemp(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "kWt":
//                currentModel.setKWt(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "flowRate":
//                currentModel.setFlowRate(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "pumpingPower":
//                currentModel.setPumpingPower(Double.parseDouble(auditLog.getOldValue()));
//                break;
//            case "depth":
//                currentModel.setDepth(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "delta":
//                currentModel.setDelta(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "pressureLoss":
//                currentModel.setPressureLoss(Double.parseDouble(auditLog.getOldValue()));
//                break;
//            case "BHT":
//                currentModel.setBHT(Integer.parseInt(auditLog.getOldValue()));
//                break;
//            case "returnValue":
//                currentModel.setReturnValue(Double.parseDouble(auditLog.getOldValue()));
//                break;
//            // Add cases for other fields as needed
//        }
//
//        // Save the restored entity
//        repository.save(currentModel);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}
