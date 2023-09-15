package com.ceraphi.repository;

import com.ceraphi.entities.WellDetails;
import com.ceraphi.entities.WellInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellDetailsRepo extends JpaRepository<WellDetails,Long> {
    List<WellDetails> findByWellInformationId(Long id);
}
