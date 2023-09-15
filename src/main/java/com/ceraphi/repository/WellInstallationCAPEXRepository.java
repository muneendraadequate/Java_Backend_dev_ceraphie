package com.ceraphi.repository;


import com.ceraphi.entities.WellInstallationCAPEX;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WellInstallationCAPEXRepository extends JpaRepository<WellInstallationCAPEX,Long> {
    Optional<WellInstallationCAPEX>findByGeneralInformationId(Long id);
     boolean existsByGeneralInformationId(Long id);
}
