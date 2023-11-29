package com.ceraphi.services.Impl;

import com.ceraphi.services.WellDataService;
import com.ceraphi.utils.FluidMechanicsUtil;
import com.ceraphi.utils.PressureData;
import com.ceraphi.utils.WellData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WellDataCalculationImpl implements  WellDataService {
    private static final double KINEMATIC_VISCOSITY = 1.004e-6;
    private static final double DENSITY = 997;
    private static final double ROUGHNESS_TUBULAR = 0.00005;
    private static final double ROUGHNESS_ANNULUS = 0.00005;
    private static final double ROUGHNESS_COUPLING = 0.00005;
    private static final double COUPLING_LENGTH = 30.46875;



    @Override
    public double calculatePressureDrop(double flowRate, double depth, double internalDiamTubular, double internalDiamAnnulus, double internalDiamCoupling) {
        double kTubular = ROUGHNESS_TUBULAR / internalDiamTubular;
        double velocityTubular = (flowRate / 1e3) / (Math.PI * Math.pow((internalDiamTubular / 2), 2));
        double reTubular = velocityTubular * internalDiamTubular / KINEMATIC_VISCOSITY;
        double darcyFF_Tubular = FluidMechanicsUtil.colebrookQuartic(kTubular, reTubular);
        double pressure_Tubular = (darcyFF_Tubular * (depth / internalDiamTubular) * (Math.pow(velocityTubular, 2) * DENSITY / 2)) / 1e5;


        // Calculate annulus pressure drop
        double kAnnulus = ROUGHNESS_ANNULUS / internalDiamAnnulus;
        double velocityAnnulus = (flowRate / 1e3) / (Math.PI * Math.pow(internalDiamAnnulus / 2, 2));
        double reAnnulus = velocityAnnulus * internalDiamAnnulus / KINEMATIC_VISCOSITY;
        double darcyFF_Annulus = FluidMechanicsUtil.colebrookQuartic(kAnnulus, reAnnulus);
        double pressure_Annulus = (darcyFF_Annulus * (depth / internalDiamTubular)
                * (Math.pow(velocityAnnulus, 2) * DENSITY / 2)) / 1e5;

        // calculations for coupling
        double kCoupling = ROUGHNESS_COUPLING / internalDiamCoupling;
        double velocityCoupling = (flowRate / 1e3) / (Math.PI * Math.pow(internalDiamCoupling / 2, 2));
        double reCoupling = velocityCoupling * internalDiamCoupling / KINEMATIC_VISCOSITY;
        double darcyFF_Coupling = FluidMechanicsUtil.colebrookQuartic(kCoupling, reCoupling);
        double pressure_Coupling = (darcyFF_Coupling * (COUPLING_LENGTH / internalDiamCoupling)
                * (Math.pow(velocityCoupling, 2) * DENSITY / 2)) / 1e5;


        // Calculate total pressure drop
        double pressureDrop = pressure_Tubular + pressure_Annulus + pressure_Coupling;
        return pressureDrop;
    }



    private double interpolateValues(double y1, double y2, double frac) {
        return y1 + frac * (y2 - y1);
    }

    @Override
    public double calculateBoostPumpPower(double flowRate, double networkLength, double wells) {

        List<Double> values = new ArrayList<>();

        for (int x = 0; x <= 20; x++) {
            double internalDiam = x * 0.025;
            double roughness = 0.02 / 100000;
            double kinematicViscosity = KINEMATIC_VISCOSITY;
            double density = DENSITY;
            double k = roughness / internalDiam;

            double velocity = (flowRate / 1e3) / (Math.PI * Math.pow(internalDiam / 2, 2));
            double r = velocity * internalDiam / kinematicViscosity;

            double x1 = k * r * (Math.log(10) / 18.574);
            double x2 = Math.log(r * (Math.log(10) / 5.02));
            double f = x2 - 0.2;

            double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            f = 0.5 * Math.log(10) / f;
            f = f * f;
            double darcyFF = f;

            double pressure = (darcyFF * (1000 / internalDiam) * (Math.pow(velocity, 2) * density / 2)) / (1e5);

            values.add(pressure);
        }

        int i = findIndex(values);
        return values.get(i) * networkLength;
    }
//@Override
//public double calculateBoostPumpPower(double flowRate, double networkLength, double wells) {
//
//    List<Double> values = new ArrayList<>();
//
//    for (int x = 1; x <= 20; x++) {
//        double internalDiam = x * 0.025;
//        double roughness = 0.02 / 100000;
//        double kinematicViscosity = KINEMATIC_VISCOSITY;
//        double density = DENSITY;
//        double k = roughness / internalDiam;
//
//        double velocity = (flowRate / 1e3) / (Math.PI * Math.pow(internalDiam / 2, 2));
//        double r = velocity * internalDiam / kinematicViscosity;
//
//        double x1 = k * r * (Math.log(10) / 18.574);
//        double x2 = Math.log(r * (Math.log(10) / 5.02));
//        double f = x2 - 0.2;
//
//        double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
//        f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
//        e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
//        f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
//        f = 0.5 * Math.log(10) / f;
//        f = f * f;
//        double darcyFF = f;
//
//        double pressure = (darcyFF * (1000 / internalDiam) * (Math.pow(velocity, 2) * density / 2)) / (1e5);
//
//        if (pressure < 1) { // Include values less than 1
//            values.add(pressure);
//        }
//    }
//
//    int i = findIndex(values);
//    return values.get(i) * networkLength;
//}


    @Override
    public PressureData calculateBoostPumpPowerForDeepWell(double flowRate, double numberOfWells,double networkLength) {

        List<Double> pressureValues = new ArrayList<>();

        double pipeDiameter = 0;
        for (int x = 0; x <= 20; x++) {
            double internalDiam = x * 0.025;
            double roughness = 0.02 / 100000;
            double kinematicViscosity = KINEMATIC_VISCOSITY;
            double density = DENSITY;
            pipeDiameter = internalDiam;
            double k = roughness / internalDiam;

            double velocity = (flowRate / 1000) / (Math.PI * Math.pow(internalDiam / 2, 2));
            double r = velocity * internalDiam / kinematicViscosity;

            double x1 = k * r * (Math.log(10) / 18.574);
            double x2 = Math.log(r * (Math.log(10) / 5.02));
            double f = x2 - 0.2;

            double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            f = 0.5 * Math.log(10) / f;
            f = f * f;
            double darcyFF = f;

            double pressure = (darcyFF * (1000 / internalDiam) * (Math.pow(velocity, 2) * density / 2)) / (1e5);

            pressureValues.add(pressure);
        }
        int i = findIndex(pressureValues);
        PressureData pressureData = new PressureData();
        pressureData.setPressure(pressureValues.get(i) * networkLength);
        pressureData.setInternalDiameter(pipeDiameter);
        return pressureData;


    }

    private int findIndex(List<Double> values) {
        for (int i = 0; i < values.size() - 1; i++) {
            if (Math.abs(values.get(i) - values.get(i + 1)) < 5) {
                return i;
            }
        }
        return values.size() - 1;
    }
}

//    @Override
//    public PressureData calculateBoostPumpPowerForDeepWell(double flowRate, double numberOfWells, double networkLength) {
//
//        double pipeDiameter = 0;
//        List<Double> pressureValues = new ArrayList<>();
//
//        for (int x = 1; x <= 20; x++) {
//            double internalDiam = x * 0.025;
//            double roughness = 0.02 / 100000;
//            double kinematicViscosity = KINEMATIC_VISCOSITY;
//            double density = DENSITY;
//            pipeDiameter = internalDiam;
//            double k = roughness / internalDiam;
//
//            double velocity = (flowRate / 1000) / (Math.PI * Math.pow(internalDiam / 2, 2));
//            double r = velocity * internalDiam / kinematicViscosity;
//
//            double x1 = k * r * (Math.log(10) / 18.574);
//            double x2 = Math.log(r * (Math.log(10) / 5.02));
//            double f = x2 - 0.2;
//
//            double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
//            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
//            e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
//            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
//            f = 0.5 * Math.log(10) / f;
//            f = f * f;
//            double darcyFF = f;
//
//            double pressure = (darcyFF * (1000 / internalDiam) * (Math.pow(velocity, 2) * density / 2)) / (1e5);
//
//            pressureValues.add(pressure);
//        }
//
//        int i = findIndex(pressureValues);
//        PressureData pressureData = new PressureData();
//        pressureData.setPressure(pressureValues.get(i) * networkLength);
//        pressureData.setInternalDiameter(pipeDiameter);
//        return pressureData;
//    }
//
//    private int findIndex(List<Double> values) {
//        for (int i = 0; i < values.size() - 1; i++) {
//            if (Math.abs(values.get(i) - values.get(i + 1)) < 5) {
//                return i;
//            }
//        }
//        return values.size() - 1;
//    }}