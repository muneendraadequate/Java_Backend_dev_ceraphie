package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.OpexHpAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpexHpAuditLogsRepo extends JpaRepository<OpexHpAuditLogs,Long> {
    List<OpexHpAuditLogs> findByChangeSetId(Long changeSetId);
}
