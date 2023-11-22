package com.ceraphi.repository;

import com.ceraphi.entities.LogEntities.CapexHpAuditLogs;
import com.ceraphi.entities.MasterDataTables.EstimatedCostCapexHP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimatedCostCapexHPRepo extends JpaRepository<EstimatedCostCapexHP, Long> {
}
