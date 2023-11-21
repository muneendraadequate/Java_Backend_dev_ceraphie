package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.CapexDeepAuditLogs;
import com.ceraphi.entities.LogEntities.ProDataBaseAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapexDeepAuditLogsRepo extends JpaRepository<CapexDeepAuditLogs,Long> {
    List<CapexDeepAuditLogs> findByChangeSetId(Long changeSetId);
}
