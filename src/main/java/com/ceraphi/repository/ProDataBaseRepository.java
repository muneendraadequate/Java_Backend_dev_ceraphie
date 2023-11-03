package com.ceraphi.repository;

import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProDataBaseRepository extends JpaRepository<ProDataBaseModel ,Long> {
}
