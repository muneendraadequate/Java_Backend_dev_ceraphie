package com.ceraphi.utils;

public class FluidMechanicsUtil {

    public static double colebrookQuartic(double K, double R) {


    double x1 = K * R * (Math.log(10) / 18.574);
    double x2 = Math.log(R * Math.log(10) / 5.02);

    // Initial guess
    double f = x2 - 0.2;

    // First iteration
    double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
    f=f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));

    // Second iteration
    e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
    f=f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));

    // Finalized solution
    f = 0.5 * Math.log(10) / f;

    return f * f;
}

}