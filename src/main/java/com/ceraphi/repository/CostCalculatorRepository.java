package com.ceraphi.repository;

import com.ceraphi.entities.CostCalculator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostCalculatorRepository extends JpaRepository<CostCalculator,Long> {
   boolean existsByGeneralInformationId(Long id);

   Optional<CostCalculator> findByGeneralInformationId(Long id);
}
