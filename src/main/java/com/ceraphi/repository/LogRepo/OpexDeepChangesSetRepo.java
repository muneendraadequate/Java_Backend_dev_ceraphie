package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.OpexDeepChangesSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpexDeepChangesSetRepo extends JpaRepository<OpexDeepChangesSet, Long> {
}
