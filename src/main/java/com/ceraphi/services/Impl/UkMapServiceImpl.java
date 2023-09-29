package com.ceraphi.services.Impl;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.WellDetails;
import com.ceraphi.entities.WellInformation;
import com.ceraphi.repository.GeneralInformationRepository;
import com.ceraphi.repository.WellDetailsRepo;
import com.ceraphi.repository.WellInformationRepository;
import com.ceraphi.services.UkMapService;
import com.ceraphi.utils.DashBoard.GeographicalAnalysis;
import com.ceraphi.utils.UkWells.UkWells;
import com.ceraphi.utils.UkWells.Wells;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UkMapServiceImpl implements UkMapService {
    @Autowired
    private GeneralInformationRepository generalInfoRepo;
    @Autowired
    private WellInformationRepository wellInformationRepository;

    @Override
    public List<Wells> getUkwells(String country) {
List<Wells> wellsData=new ArrayList<>();
        List<GeneralInformation> allByCountry = generalInfoRepo.findAllByCountry(country);

        for (GeneralInformation generalInfo : allByCountry) {
            Wells wells = new Wells();

            wells.setCountry(generalInfo.getCountry());


            // Fetch coordinates for the current GeneralInformation record
            List<WellInformation> wellInfo = wellInformationRepository.findByGeneralInformationId(generalInfo.getId());

            if (!wellInfo.isEmpty()) {
                // Assuming there is only one set of coordinates per GeneralInformation record
                WellInformation wellInformation = wellInfo.get(0);
                wells.setLongitude((wellInformation.getCoordinates_longitude()));
                wells.setLatitude((wellInformation.getCoordinates_latitude()));
            }

            wellsData.add(wells);
        }

        return wellsData;
    }

}
