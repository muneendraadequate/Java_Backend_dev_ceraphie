package com.ceraphi.repository;

import com.ceraphi.entities.HeatConnectionCapex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeatConnectionRepo extends JpaRepository<HeatConnectionCapex,Long> {
    boolean existsByGeneralInformationId(Long id);
   Optional<HeatConnectionCapex> findByGeneralInformationId(Long id);
}

