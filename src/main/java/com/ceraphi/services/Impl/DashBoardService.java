package com.ceraphi.services.Impl;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.WellInformationRepository;
import com.ceraphi.utils.DashBoard.GeographicalAnalysis;
import com.ceraphi.utils.DashBoard.WellByType;
import com.ceraphi.utils.DashBoard.WellsByRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardService {
    private final GeneralInformationRepository generalInformationRepository;
    private final WellInformationRepository wellInformationRepository;

    @Autowired
    public DashBoardService(
            GeneralInformationRepository generalInformationRepository,
            WellInformationRepository wellInformationRepository
    ) {
        this.generalInformationRepository = generalInformationRepository;
        this.wellInformationRepository = wellInformationRepository;
    }

    public List<GeographicalAnalysis> getTheWellsByCountry(Long id ) {
        List<GeographicalAnalysis> dashBoardMaps = new ArrayList<>();

        List<GeneralInformation> allByCountry = generalInformationRepository.findAllByUser(id);

        for (GeneralInformation generalInfo : allByCountry) {
            GeographicalAnalysis geographicalAnalysis = new GeographicalAnalysis();

            geographicalAnalysis.setCountry(generalInfo.getCountry());


            // Fetch coordinates for the current GeneralInformation record
            List<WellInformation> wellInfo = wellInformationRepository.findByGeneralInformationId(generalInfo.getId());

            if (!wellInfo.isEmpty()) {
                // Assuming there is only one set of coordinates per GeneralInformation record
                WellInformation wellInformation = wellInfo.get(0);
                geographicalAnalysis.setLongitude((wellInformation.getCoordinates_longitude()));
                geographicalAnalysis.setLatitude((wellInformation.getCoordinates_latitude()));
            }

            dashBoardMaps.add(geographicalAnalysis);
        }

        return dashBoardMaps;
    }
//    public List<WellsByRegion> getProjectTypesByCountry(Long id ) {
//        List<Object[]> projectTypeCounts = generalInformationRepository.countProjectTypesByCountry();
//
//        List<WellsByRegion> countryProjectTypeDTOs = new ArrayList<>();
//
//        for (Object[] result : projectTypeCounts) {
//            String country = (String) result[0];
//            long newWellsCount = (Long) result[1];
//            long repurposedWellsCount = (Long) result[2];
//
//            countryProjectTypeDTOs.add(new WellsByRegion(country, (int) newWellsCount, (int) repurposedWellsCount));
//        }
//
//        return countryProjectTypeDTOs;
//    }


    public List<WellsByRegion> getProjectTypesByCountryAndUserId(Long userId) {
        List<Object[]> projectTypeCounts = generalInformationRepository.countProjectTypesByCountryAndUserId(userId);

        List<WellsByRegion> countryProjectTypeDTOs = new ArrayList<>();

        for (Object[] result : projectTypeCounts) {
            String country = (String) result[0];
            long newWellsCount = (Long) result[1];
            long repurposedWellsCount = (Long) result[2];

            countryProjectTypeDTOs.add(new WellsByRegion(country, (int) newWellsCount, (int) repurposedWellsCount));
        }

        return countryProjectTypeDTOs;
    }
//    public List<WellByType> getWellCounts(Long id ) {
//        List<Object[]> wellCounts = generalInformationRepository.countBothProjectTypes();
//
//        long newWellsCount = 0;
//        long repurposedWellsCount = 0;
//
//        if (!wellCounts.isEmpty()) {
//            Object[] counts = wellCounts.get(0);
//            newWellsCount = (Long) counts[0];
//            repurposedWellsCount = (Long) counts[1];
//        }
//
//        List<WellByType> wellTypeDTOs = new ArrayList<>();
//        wellTypeDTOs.add(new WellByType("New Wells", newWellsCount));
//        wellTypeDTOs.add(new WellByType("Repurposed Wells", repurposedWellsCount));
//
//        return wellTypeDTOs;
//    }
public List<WellByType> getWellCountsByUserId(Long userId) {
    List<Object[]> wellCounts = generalInformationRepository.countWellTypesByUserId(userId);

    long newWellsCount = 0;
    long repurposedWellsCount = 0;

    if (!wellCounts.isEmpty()) {
        Object[] counts = wellCounts.get(0);
        newWellsCount = (Long) counts[0];
        repurposedWellsCount = (Long) counts[1];
    }

    List<WellByType> wellTypeDTOs = new ArrayList<>();
    wellTypeDTOs.add(new WellByType("New Wells", newWellsCount));
    wellTypeDTOs.add(new WellByType("Repurposed Wells", repurposedWellsCount));

    return wellTypeDTOs;
}


}

