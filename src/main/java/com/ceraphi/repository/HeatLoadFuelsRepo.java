package com.ceraphi.repository;

import com.ceraphi.entities.MasterDataTables.HeatLoadFuels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeatLoadFuelsRepo extends JpaRepository<HeatLoadFuels,Long> {
}
