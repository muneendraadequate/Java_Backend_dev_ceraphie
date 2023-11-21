package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.HeatLoadAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeatLoadAuditLogsRepo extends JpaRepository<HeatLoadAuditLogs,Long> {
}
