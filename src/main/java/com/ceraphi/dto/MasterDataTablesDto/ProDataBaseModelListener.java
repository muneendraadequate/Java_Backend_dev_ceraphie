//package com.ceraphi.dto.MasterDataTablesDto;
//
//
//import com.ceraphi.entities.MasterDataTables.ChangeLog;
//import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PreUpdate;
//import java.lang.reflect.Field;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class ProDataBaseModelListener {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @PreUpdate
//    public void preUpdate(ProDataBaseModel model) {
//        ProDataBaseModel original = entityManager.find(ProDataBaseModel.class, model.getId());
//
//        List<ChangeLog> changes = compareObjects(original, model);
//
//        if (!changes.isEmpty()) {
//            if (model.getChangeLogs() == null) {
//                model.setChangeLogs(new ArrayList<>());
//            }
//            model.getChangeLogs().addAll(changes);
//        }
//    }
//
//    private List<ChangeLog> compareObjects(ProDataBaseModel original, ProDataBaseModel updated) {
//        List<ChangeLog> changes = new ArrayList<>();
//
//        // Use reflection to compare fields
//        Field[] fields = ProDataBaseModel.class.getDeclaredFields();
//        for (Field field : fields) {
//            field.setAccessible(true);
//            try {
//                Object originalValue = field.get(original);
//                Object updatedValue = field.get(updated);
//
//                // Compare values
//                if (!Objects.equals(originalValue, updatedValue)) {
//                    ChangeLog changeLog = createChangeLog(original, field.getName(), originalValue, updatedValue);
//                    changes.add(changeLog);
//                }
//            } catch (IllegalAccessException e) {
//                e.printStackTrace(); // Handle the exception according to your needs
//            }
//        }
//
//        return changes;
//    }
//
//    private ChangeLog createChangeLog(ProDataBaseModel model, String fieldName, Object oldValue, Object newValue) {
//        ChangeLog changeLog = new ChangeLog();
//        changeLog.setProDataBaseModel(model);
//        changeLog.setFieldName(fieldName);
//        changeLog.setOldValue(String.valueOf(oldValue));
//        changeLog.setNewValue(String.valueOf(newValue));
//        changeLog.setChangeDateTime(LocalDateTime.now());
//        // You can set other properties like user information if needed
//        return changeLog;
//    }
//}