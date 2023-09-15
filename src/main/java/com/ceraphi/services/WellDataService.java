package com.ceraphi.services;

import com.ceraphi.utils.PressureData;
import com.ceraphi.utils.WellData;

public interface WellDataService {
   double calculatePressureDrop(double flowRate, double depth, double internalDiamTubular,
                                double internalDiamAnnulus,double internalDiamCoupling);
     double calculateBoostPumpPower(double flowRate, double networkLength, double wells);
    public PressureData calculateBoostPumpPowerForDeepWell(double flowRate, double wells, double networkLength); ;

    }
