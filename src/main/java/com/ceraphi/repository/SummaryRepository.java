package com.ceraphi.repository;

import com.ceraphi.entities.WellSummaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SummaryRepository extends JpaRepository<WellSummaryData, Long> {

    WellSummaryData findByGeneralInformationId(Long id);

    int countHeatWellsByHeatSelectedIsTrue();

    int countDeepWellsByDeepSelectedIsTrue();

    @Query("SELECT COALESCE(SUM(wsd.heatWells), 0) FROM WellSummaryData wsd WHERE wsd.heatSelected = true")
    Integer countHeatWellsForSelectedHeat();

    @Query("SELECT COALESCE(SUM(wsd.deepWells), 0) FROM WellSummaryData wsd WHERE wsd.deepSelected = true")
    Integer countDeepWellsForSelectedDeep();
}