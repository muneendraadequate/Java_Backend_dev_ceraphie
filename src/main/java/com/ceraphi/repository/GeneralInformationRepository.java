package com.ceraphi.repository;

import com.ceraphi.entities.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralInformationRepository extends JpaRepository<GeneralInformation,Long> {
    List<GeneralInformation> findAllByUser(Long id);
}
