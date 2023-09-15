package com.ceraphi.utils.Lcho;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

//public class MediumWellLCOHCalculator {
//    public static void main(String[] args) {
//        List<LCOHYearData> yearDataList = new ArrayList<>();
//        List<LCOHResult> results = new ArrayList<>();
//        // Step 1 - Initialize variables
//        BigDecimal price = new BigDecimal("0.11");
//        BigDecimal discountRate = new BigDecimal("0.035");
//
//        // Step 2 - Initialize arrays
//        int[] years = new int[41];
//        for (int i = 0; i <= 40; i++) {
//            years[i] = i;
//        }
//
//        int rows = years.length;
//
//        // Initialize arrays
//        BigDecimal[] capex = new BigDecimal[rows];
//        BigDecimal[] opex = new BigDecimal[rows];
//        BigDecimal[] production = new BigDecimal[rows];
//        BigDecimal[] discountedFactor = new BigDecimal[rows];
//        BigDecimal[] discountedCashFlow = new BigDecimal[rows];
//        BigDecimal[] cumulativeCashFlow = new BigDecimal[rows];
//        BigDecimal[] discountedCost = new BigDecimal[rows];
//        BigDecimal[] discountedProduction = new BigDecimal[rows];
//
//        // Set constant values for capex and opex for the Medium Well
//        BigDecimal mediumWellCapex = new BigDecimal("2742807");
//        BigDecimal mediumWellOpex = new BigDecimal("306680");
//
//        // Populate capex, opex, and production arrays with constant values
//        for (int i = 0; i < rows; i++) {
//            capex[i] = (i == 0) ? mediumWellCapex : new BigDecimal("153057");
//            opex[i] = (i == 0) ? mediumWellOpex : mediumWellOpex;
//            production[i] = (i == 0) ? BigDecimal.ZERO : new BigDecimal("1000.0");
//        }
//
//        // Create and initialize the "LCOH years" array
//        BigDecimal[][] lcohYears = new BigDecimal[][]{
//                {new BigDecimal("25"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
//                {new BigDecimal("30"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
//                {new BigDecimal("40"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}
//        };
//
//        // Calculate financial metrics
//        for (int i = 0; i < rows; i++) {
//            // Calculate Discounted Factor
//            discountedFactor[i] = BigDecimal.ONE.divide(
//                    BigDecimal.ONE.add(discountRate).pow(years[i]), 10, RoundingMode.HALF_UP
//            );
//
//            // Calculate Cashflow based on Price, Production, CAPEX, and OPEX
//            BigDecimal cashFlow = production[i].multiply(price).subtract(capex[i].add(opex[i]));
//
//            // Calculate Discounted Cashflow
//            discountedCashFlow[i] = discountedFactor[i].multiply(cashFlow);
//
//            // Calculate Cumulative Cashflow
//            cumulativeCashFlow[i] = (i == 0) ? discountedCashFlow[i] : discountedCashFlow[i].add(cumulativeCashFlow[i - 1]);
//
//            // Calculate Discounted Cost and Discounted Production
//            discountedCost[i] = capex[i].multiply(discountedFactor[i]).add(opex[i].multiply(discountedFactor[i]));
//            discountedProduction[i] = production[i].multiply(discountedFactor[i]);
//        }
//
//        // Fill in the "LCOH years" array
//        for (int i = 0; i < lcohYears.length; i++) {
//            int x;
//            if (i == 0) {
//                x = 26;
//            } else if (i == 1) {
//                x = 31;
//            } else {
//                x = 41;
//            }
//
//            lcohYears[i][1] = calculateLCOH(x - 1, discountedCost, discountedProduction);
//            lcohYears[i][2] = cumulativeCashFlow[x - 1];
//            BigDecimal irrValue = calculateIRR(x - 1, discountedCashFlow);
//            lcohYears[i][3] = (irrValue == null) ? new BigDecimal("NaN") : irrValue;
//            lcohYears[i][4] = cumulativeCashFlow[x - 1].divide(capex[0], 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
//        }
//        for (int i = 0; i < lcohYears.length; i++) {
//            int x;
//            if (i == 0) {
//                x = 26;
//            } else if (i == 1) {
//                x = 31;
//            } else {
//                x = 41;
//            }
//
//            BigDecimal lcoh = calculateLCOH(x - 1, discountedCost, discountedProduction);
//            BigDecimal npv = cumulativeCashFlow[x - 1];
//            BigDecimal irrValue = calculateIRR(x - 1, discountedCashFlow);
//            BigDecimal pi = cumulativeCashFlow[x - 1].divide(capex[0], 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
//
//            // Create an instance of LCOHYearData and add it to the yearDataList
//            LCOHYearData yearData = new LCOHYearData(lcoh, npv, irrValue, pi);
//            yearDataList.add(yearData);
//            System.out.println(yearData.getLcoh());
//            System.out.println(yearData.getNpv());
//            System.out.println(yearData.getIrr());
//            System.out.println(yearData.getPi());
//        }
//        LCOHResponse response = new LCOHResponse(yearDataList);
//
//        // Filter the data for years 25, 30, and 40
//        List<LCOHYearData> filteredData = response.getDataForYears(25, 30, 40);
//
//        // Serialize the filtered response to JSON
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String jsonResponse = objectMapper.writeValueAsString(filteredData);
//            System.out.println(jsonResponse); // Print the JSON response
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//        // Print the "LCOH years" array
//
//
//    private static BigDecimal calculateLCOH(int currentYear, BigDecimal[] discountedCost, BigDecimal[] discountedProduction) {
//        BigDecimal totalCost = BigDecimal.ZERO;
//        BigDecimal totalProduction = BigDecimal.ZERO;
//
//        for (int i = 0; i <= currentYear; i++) {
//            totalCost = totalCost.add(discountedCost[i]);
//            totalProduction = totalProduction.add(discountedProduction[i]);
//        }
//
//        if (totalProduction.compareTo(BigDecimal.ZERO) == 0) {
//            return BigDecimal.ZERO;
//        }
//
//        return totalCost.divide(totalProduction, 10, RoundingMode.HALF_UP);
//    }
//
//    private static BigDecimal calculateIRR(int currentYear, BigDecimal[] discountedCashFlow) {
//        BigDecimal irr = BigDecimal.ZERO;
//        BigDecimal lowerBound = new BigDecimal("-1.0");
//        BigDecimal upperBound = new BigDecimal("1.0");
//        BigDecimal epsilon = new BigDecimal("0.00001");
//
//        for (int i = 0; i < 1000; i++) {
//            BigDecimal guess = lowerBound.add(upperBound).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
//            BigDecimal npv = BigDecimal.ZERO;
//
//            boolean denominatorZero = false; // Flag to check if denominator could be zero
//
//            for (int j = 0; j <= currentYear; j++) {
//                BigDecimal denominator = BigDecimal.ONE.add(guess).pow(j);
//
//                // Check if the denominator is close to zero
//                if (denominator.abs().compareTo(epsilon) < 0) {
//                    denominatorZero = true;
//                    break; // Skip this iteration
//                }
//
//                npv = npv.add(discountedCashFlow[j].divide(denominator, MathContext.DECIMAL128));
//            }
//
//            if (denominatorZero) {
//                continue; // Skip this iteration and try the next guess
//            }
//
//            if (npv.abs().compareTo(epsilon) < 0) {
//                irr = guess;
//                break;
//            }
//
//            if (npv.compareTo(BigDecimal.ZERO) > 0) {
//                lowerBound = guess;
//            } else {
//                upperBound = guess;
//            }
//        }
//
//        if (irr.compareTo(new BigDecimal("-1.0")) < 0 || irr.compareTo(new BigDecimal("1.0")) > 0) {
//            // IRR calculation failed, return null
//            return null;
//        }
//
//        // Convert IRR to percentage
//        return irr.multiply(BigDecimal.valueOf(100));
//    }
//}

public class MediumWellLCOHCalculator {
        public LCOHYearData getData () {
        List<LCOHYearData> yearDataList = new ArrayList<>();
        List<LCOHResult> results = new ArrayList<>();
        // Step 1 - Initialize variables
        BigDecimal price = new BigDecimal("0.11");
        BigDecimal discountRate = new BigDecimal("0.035");

        // Step 2 - Initialize arrays
        int[] years = new int[41];
        for (int i = 0; i <= 40; i++) {
            years[i] = i;
        }

        int rows = years.length;

        // Initialize arrays
        BigDecimal[] capex = new BigDecimal[rows];
        BigDecimal[] opex = new BigDecimal[rows];
        BigDecimal[] production = new BigDecimal[rows];
        BigDecimal[] discountedFactor = new BigDecimal[rows];
        BigDecimal[] discountedCashFlow = new BigDecimal[rows];
        BigDecimal[] cumulativeCashFlow = new BigDecimal[rows];
        BigDecimal[] discountedCost = new BigDecimal[rows];
        BigDecimal[] discountedProduction = new BigDecimal[rows];

        // Set constant values for capex and opex for the Medium Well
        BigDecimal mediumWellCapex = new BigDecimal("2742807");
        BigDecimal mediumWellOpex = new BigDecimal("306680");

        // Populate capex, opex, and production arrays with constant values
        for (int i = 0; i < rows; i++) {
            capex[i] = (i == 0) ? mediumWellCapex : new BigDecimal("153057");
            opex[i] = (i == 0) ? mediumWellOpex : mediumWellOpex;
            production[i] = (i == 0) ? BigDecimal.ZERO : new BigDecimal("1000.0");
        }

        // Create and initialize the "LCOH years" array
        BigDecimal[][] lcohYears = new BigDecimal[][]{
                {new BigDecimal("25"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {new BigDecimal("30"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO},
                {new BigDecimal("40"), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}
        };

        // Calculate financial metrics
        for (int i = 0; i < rows; i++) {
            // Calculate Discounted Factor
            discountedFactor[i] = BigDecimal.ONE.divide(
                    BigDecimal.ONE.add(discountRate).pow(years[i]), 10, RoundingMode.HALF_UP
            );

            // Calculate Cashflow based on Price, Production, CAPEX, and OPEX
            BigDecimal cashFlow = production[i].multiply(price).subtract(capex[i].add(opex[i]));

            // Calculate Discounted Cashflow
            discountedCashFlow[i] = discountedFactor[i].multiply(cashFlow);

            // Calculate Cumulative Cashflow
            cumulativeCashFlow[i] = (i == 0) ? discountedCashFlow[i] : discountedCashFlow[i].add(cumulativeCashFlow[i - 1]);

            // Calculate Discounted Cost and Discounted Production
            discountedCost[i] = capex[i].multiply(discountedFactor[i]).add(opex[i].multiply(discountedFactor[i]));
            discountedProduction[i] = production[i].multiply(discountedFactor[i]);
        }

        // Fill in the "LCOH years" array
        for (int i = 0; i < lcohYears.length; i++) {
            int x;
            if (i == 0) {
                x = 26;
            } else if (i == 1) {
                x = 31;
            } else {
                x = 41;
            }

            lcohYears[i][1] = calculateLCOH(x - 1, discountedCost, discountedProduction);
            lcohYears[i][2] = cumulativeCashFlow[x - 1];
            BigDecimal irrValue = calculateIRR(x - 1, discountedCashFlow);
            lcohYears[i][3] = (irrValue == null) ? new BigDecimal("NaN") : irrValue;
            lcohYears[i][4] = cumulativeCashFlow[x - 1].divide(capex[0], 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
        }
        LCOHYearData yearData = null;
        for (int i = 0; i < lcohYears.length; i++) {
            int x;
            if (i == 0) {
                x = 26;
            } else if (i == 1) {
                x = 31;
            } else {
                x = 41;
            }

            BigDecimal lcoh = calculateLCOH(x - 1, discountedCost, discountedProduction);
            BigDecimal npv = cumulativeCashFlow[x - 1];
            BigDecimal irrValue = calculateIRR(x - 1, discountedCashFlow);
            BigDecimal pi = cumulativeCashFlow[x - 1].divide(capex[0], 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);

            // Create an instance of LCOHYearData and add it to the yearDataList
            yearData = new LCOHYearData(lcoh, npv, irrValue, pi);
            yearDataList.add(yearData);
            System.out.println(yearData.getLcoh());
            System.out.println(yearData.getNpv());
            System.out.println(yearData.getIrr());
            System.out.println(yearData.getPi());
        }
        LCOHResponse response = new LCOHResponse(yearDataList);

        System.out.println("Entire Response:");
        System.out.println(response);

        // Filter the data for years 25, 30, and 40
        List<LCOHYearData> filteredData = response.getDataForYears(25, 30, 40);

        // Print the filtered data
        System.out.println("\nFiltered Data for Years 25, 30, and 40:");
        for (LCOHYearData data : filteredData) {
            System.out.println(data);
        }

        // Serialize the filtered response to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponse = objectMapper.writeValueAsString(filteredData);
            System.out.println("\nJSON Response:");
            System.out.println(jsonResponse); // Print the JSON response
        } catch (Exception e) {
            e.printStackTrace();
        }
        return yearData;
    }
        // Print the "LCOH years" array


    private static BigDecimal calculateLCOH(int currentYear, BigDecimal[] discountedCost, BigDecimal[] discountedProduction) {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalProduction = BigDecimal.ZERO;

        for (int i = 0; i <= currentYear; i++) {
            totalCost = totalCost.add(discountedCost[i]);
            totalProduction = totalProduction.add(discountedProduction[i]);
        }

        if (totalProduction.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return totalCost.divide(totalProduction, 10, RoundingMode.HALF_UP);
    }

    private static BigDecimal calculateIRR(int currentYear, BigDecimal[] discountedCashFlow) {
        BigDecimal irr = BigDecimal.ZERO;
        BigDecimal lowerBound = new BigDecimal("-1.0");
        BigDecimal upperBound = new BigDecimal("1.0");
        BigDecimal epsilon = new BigDecimal("0.00001");

        for (int i = 0; i < 1000; i++) {
            BigDecimal guess = lowerBound.add(upperBound).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);
            BigDecimal npv = BigDecimal.ZERO;

            boolean denominatorZero = false; // Flag to check if denominator could be zero

            for (int j = 0; j <= currentYear; j++) {
                BigDecimal denominator = BigDecimal.ONE.add(guess).pow(j);

                // Check if the denominator is close to zero
                if (denominator.abs().compareTo(epsilon) < 0) {
                    denominatorZero = true;
                    break; // Skip this iteration
                }

                npv = npv.add(discountedCashFlow[j].divide(denominator, MathContext.DECIMAL128));
            }

            if (denominatorZero) {
                continue; // Skip this iteration and try the next guess
            }

            if (npv.abs().compareTo(epsilon) < 0) {
                irr = guess;
                break;
            }

            if (npv.compareTo(BigDecimal.ZERO) > 0) {
                lowerBound = guess;
            } else {
                upperBound = guess;
            }
        }

        if (irr.compareTo(new BigDecimal("-1.0")) < 0 || irr.compareTo(new BigDecimal("1.0")) > 0) {
            // IRR calculation failed, return null
            return null;
        }

        // Convert IRR to percentage
        return irr.multiply(BigDecimal.valueOf(100));
    }
}