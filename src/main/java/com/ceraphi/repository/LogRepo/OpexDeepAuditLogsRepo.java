package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.CapexHpAuditLogs;
import com.ceraphi.entities.LogEntities.OpexDeepAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpexDeepAuditLogsRepo extends JpaRepository<OpexDeepAuditLogs,Long> {
    List<OpexDeepAuditLogs> findByChangeSetId(Long changeSetId);
}
