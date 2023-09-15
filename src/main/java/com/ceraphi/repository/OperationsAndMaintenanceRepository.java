package com.ceraphi.repository;

import com.ceraphi.entities.OperationsAndMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationsAndMaintenanceRepository extends JpaRepository<OperationsAndMaintenance,Long> {
    boolean existsByGeneralInformationId(Long id);
    Optional<OperationsAndMaintenance>findByGeneralInformationId(Long id);
}
