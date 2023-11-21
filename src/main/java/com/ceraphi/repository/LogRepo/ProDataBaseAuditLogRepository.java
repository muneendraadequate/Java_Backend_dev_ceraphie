package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.ProDataBaseAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProDataBaseAuditLogRepository extends JpaRepository<ProDataBaseAuditLog, Long> {
    List<ProDataBaseAuditLog> findByChangeSetId(Long changeSetId);
}
