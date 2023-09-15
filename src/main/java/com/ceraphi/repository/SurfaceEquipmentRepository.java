package com.ceraphi.repository;

import com.ceraphi.entities.SurfaceEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.OptionPaneUI;
import java.util.Optional;

public interface SurfaceEquipmentRepository extends JpaRepository<SurfaceEquipment,Long> {
    boolean existsByGeneralInformationId(Long id);
   Optional<SurfaceEquipment> findByGeneralInformationId(Long id);
}
