package com.ceraphi.services.Impl;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.entities.WellSummaryData;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.SummaryRepository;
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
    private SummaryRepository summaryRepository;

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
    public int getTotalProjectsForUser(Long userId) {
        // Use the repository to get projects for the user
        List<GeneralInformation> userProjects = generalInformationRepository.findAllByUser(userId);
        return userProjects.size();
}


    public int calculateTotalCapacity() {
        List<WellSummaryData> summaryDataList = summaryRepository.findAll();
        int totalCapacity = 0;

        for (WellSummaryData data : summaryDataList) {
            if (data.isHeatSelected() && !data.isDeepSelected()) {
                totalCapacity += parseCapacity(data.getHeatCapacityRequired());
            } else if (data.isDeepSelected() && !data.isHeatSelected()) {
                totalCapacity += parseCapacity(data.getDeepCapacityRequired());
            } else if (data.isHeatSelected() && data.isDeepSelected()) {
                totalCapacity += parseCapacity(data.getHeatCapacityRequired());
                totalCapacity += parseCapacity(data.getDeepCapacityRequired());
            }
        }

        return totalCapacity;
    }

    private int parseCapacity(String capacity) {
        int value = 0;
        if (capacity != null && !capacity.isEmpty()) {
            // Example: Extracting numerical values from strings
            String numericValue = capacity.replaceAll("\\D", "");
            value = Integer.parseInt(numericValue);
        }
        return value;
    }


    public int calculateTotalInvestment() {
        List<WellSummaryData> summaryDataList = summaryRepository.findAll();
        double totalInvestment = 0.0;

        for (WellSummaryData data : summaryDataList) {
            if (data.isHeatSelected() && !data.isDeepSelected()) {
                totalInvestment += parseInvestment(data.getHeatInitialInvestment());
            } else if (data.isDeepSelected() && !data.isHeatSelected()) {
                totalInvestment += parseInvestment(data.getDeepInitialInvestment());
            } else if (data.isHeatSelected() && data.isDeepSelected()) {
                totalInvestment += parseInvestment(data.getHeatInitialInvestment());
                totalInvestment += parseInvestment(data.getDeepInitialInvestment());
            }
        }

        // Convert total investment to millions
        int totalInvestmentMillions = (int) (totalInvestment / 1000000);

        return totalInvestmentMillions;
    }

    private double parseInvestment(String investment) {
        double value = 0.0;
        if (investment != null && !investment.isEmpty()) {
            value = Double.parseDouble(investment);
        }
        return value;
    }



    public Integer countHeatWellsWithHeatSelected() {
        return summaryRepository.countHeatWellsForSelectedHeat();
    }

    public Integer countDeepWellsWithDeepSelected() {
        return summaryRepository.countDeepWellsForSelectedDeep();
    }





}






