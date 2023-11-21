package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.CapexDeepAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapexDeepAuditLogsRepo extends JpaRepository<CapexDeepAuditLogs,Long> {
}
