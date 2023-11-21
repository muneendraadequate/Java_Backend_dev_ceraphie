package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.OpexDeepAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpexDeepAuditLogsRepo extends JpaRepository<OpexDeepAuditLogs,Long> {
}
