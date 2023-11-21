package com.ceraphi.repository.LogRepo;

import com.ceraphi.entities.LogEntities.ChangeSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeSetRepository extends JpaRepository<ChangeSet, Long> {
}
