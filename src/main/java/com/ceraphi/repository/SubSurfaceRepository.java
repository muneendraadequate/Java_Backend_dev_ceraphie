package com.ceraphi.repository;

import com.ceraphi.entities.SubSurface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubSurfaceRepository extends JpaRepository<SubSurface,Long> {
    boolean existsByGeneralInformationId(Long id);
   Optional<SubSurface> findByGeneralInformationId(Long id);
}
