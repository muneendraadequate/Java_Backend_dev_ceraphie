package com.ceraphi.repository;

import com.ceraphi.entities.Inputs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputsRepository extends JpaRepository<Inputs, Long > {
   Inputs findFirstByOrderByCreatedAtDesc();

    Inputs findByGeneralInformationId(Long id);
}
