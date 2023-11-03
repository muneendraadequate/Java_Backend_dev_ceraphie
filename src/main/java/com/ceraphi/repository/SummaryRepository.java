package com.ceraphi.repository;

import com.ceraphi.entities.WellSummaryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SummaryRepository extends JpaRepository<WellSummaryData ,Long> {
    WellSummaryData findByGeneralInformationId(Long id);
    int countHeatWellsByHeatSelectedIsTrue();
    int countDeepWellsByDeepSelectedIsTrue();
    @Query("SELECT SUM(wsd.heatWells) FROM WellSummaryData wsd WHERE wsd.heatSelected = true")
    int countHeatWellsForSelectedHeat();

    @Query("SELECT SUM(wsd.deepWells) FROM WellSummaryData wsd WHERE wsd.deepSelected = true")
    int countDeepWellsForSelectedDeep();

}
