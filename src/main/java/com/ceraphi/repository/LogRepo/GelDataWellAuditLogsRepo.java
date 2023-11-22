package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.GelDataWellAuditLogs;
import com.ceraphi.entities.LogEntities.OpexHpAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GelDataWellAuditLogsRepo extends JpaRepository<GelDataWellAuditLogs,Long> {
    List<GelDataWellAuditLogs> findByChangeSetId(Long changeSetId);
}
