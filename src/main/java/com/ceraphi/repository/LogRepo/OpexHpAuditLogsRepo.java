package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.OpexHpAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpexHpAuditLogsRepo extends JpaRepository<OpexHpAuditLogs,Long> {
}
