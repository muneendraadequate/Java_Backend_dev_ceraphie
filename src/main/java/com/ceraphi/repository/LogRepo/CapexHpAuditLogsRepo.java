package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.CapexDeepAuditLogs;
import com.ceraphi.entities.LogEntities.CapexHpAuditLogs;
import com.ceraphi.entities.MasterDataTables.ChangeLog;
import com.ceraphi.entities.MasterDataTables.EstimatedCostCapexHP;
import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CapexHpAuditLogsRepo extends JpaRepository<CapexHpAuditLogs, Long> {
    List<CapexHpAuditLogs> findByChangeSetId(Long changeSetId);
}
