package com.ceraphi.repository;

import com.ceraphi.entities.MasterDataTables.ChangeLog;
import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChangeLogRepo extends JpaRepository<ChangeLog,Long> {
    List<ChangeLog> findByProDataBaseModel(ProDataBaseModel proDataBaseModel);
}
