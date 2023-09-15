package com.ceraphi.repository;


import com.ceraphi.entities.OutputCalculator;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface OutPutCalculatorRepository extends JpaRepository<OutputCalculator,Long> {
   Optional<OutputCalculator> findByGeneralInformationId(Long id);
   boolean existsByGeneralInformationId(Long id);
}
