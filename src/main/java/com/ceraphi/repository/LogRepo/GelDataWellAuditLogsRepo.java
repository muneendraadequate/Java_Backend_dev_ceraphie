package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.GelDataWellAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GelDataWellAuditLogsRepo extends JpaRepository<GelDataWellAuditLogs,Long> {
}
