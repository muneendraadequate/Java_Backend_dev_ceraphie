package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.HeatLoadAuditLogs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeatLoadAuditLogsRepo extends JpaRepository<HeatLoadAuditLogs,Long> {
    List<HeatLoadAuditLogs> findByChangeSetId(Long changeSetId);
}
