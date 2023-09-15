package com.ceraphi.repository;

import com.ceraphi.entities.WellInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellInformationRepository extends JpaRepository<WellInformation,Long> {
    List<WellInformation>findByGeneralInformationId(Long generalInformationId);
    boolean existsByGeneralInformationId(Long id);
    Optional<WellInformation> getByGeneralInformationId(Long id);

}
