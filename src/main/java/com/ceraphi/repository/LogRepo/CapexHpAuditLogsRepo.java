package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.CapexHpAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapexHpAuditLogsRepo extends JpaRepository<CapexHpAuditLogs, Long> {
}
