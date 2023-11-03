package com.ceraphi.utils.ForSpiderCharts;

import com.ceraphi.utils.Lcho.LCOHYearResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindOpexValues {
    public double discount_rate =0.035;
        public double selling_price = 0.11 * 1000;
        public double productionValue = 8600;
        public double electrical_Price_Inflation=0.03;
        public double deep_well_OPEX;


//        public List<LCOHYearResponse> lcohResponseHeatPump(double deep_well_CAPEX ) {
//            List<LCOHYearResponse> responseList = new ArrayList<>();
//            // Step 2 - Initialize arrays
//            int[] years = new int[41];
//            for (int i = 0; i <= 40; i++) {
//                years[i] = i;
//            }
//
//            int rows = years.length;
//
//            // Initialize arrays
//            BigDecimal[] capex = new BigDecimal[rows];
//            BigDecimal[] opex = new BigDecimal[rows];
//            BigDecimal[] production = new BigDecimal[rows];
//            BigDecimal[] discountedFactor = new BigDecimal[rows];
//            BigDecimal[] netCashFlow = new BigDecimal[rows];
//            BigDecimal[] revenue = new BigDecimal[rows];
//            BigDecimal[] cumulativeCashFlow = new BigDecimal[rows];
//            BigDecimal[] discountedCost = new BigDecimal[rows];
//            BigDecimal[] discountedProduction = new BigDecimal[rows];
//            BigDecimal[] priceInflationFactor = new BigDecimal[rows];
//            BigDecimal[] discountedCashFlow = new BigDecimal[rows];
//
//            // Set constant values
//            BigDecimal mediumWellCapex = new BigDecimal(deep_well_CAPEX);
//            BigDecimal mediumWellOpex = new BigDecimal(String.valueOf(deep_well_OPEX));
//            BigDecimal productionValue = new BigDecimal("8600");
//            BigDecimal price = new BigDecimal(selling_price);
//            BigDecimal electricalPriceInflation = new BigDecimal(electrical_Price_Inflation); // 3% inflation
//
//            // Initialize the price inflation factor
//            priceInflationFactor[0] = BigDecimal.ONE; // Initial value is 1.00
//
//            // Calculate price inflation factor for the remaining years
//            for (int i = 1; i < rows; i++) {
//                priceInflationFactor[i] = priceInflationFactor[i - 1]
//                        .multiply(BigDecimal.ONE.add(new BigDecimal(String.valueOf(electricalPriceInflation))))
//                        .setScale(2, RoundingMode.HALF_UP);
//            }
//
//            // Populate capex, opex, and production arrays with constant values
//            BigDecimal totalCost = BigDecimal.ZERO;
//            for (int i = 0; i < rows; i++) {
//                capex[i] = (i == 0) ? mediumWellCapex : BigDecimal.ZERO;
//                opex[i] = (i != 0) ? mediumWellOpex : BigDecimal.ZERO;
//                production[i] = (i == 0) ? BigDecimal.ZERO : productionValue;
//                totalCost = totalCost.add(capex[i].add(opex[i]).multiply(priceInflationFactor[i]));
//            }
//
//            // Calculate Cashflow based on Price, Production, CAPEX, and OPEX
//            for (int i = 0; i < rows; i++) {
//                revenue[i] = production[i].multiply(price).multiply(priceInflationFactor[i]);
//                BigDecimal cost = capex[i].add(opex[i]);
//                BigDecimal cashFlow = revenue[i].subtract(cost);
//                // Assign cashFlow value to the netCashFlow array or use it as needed.
//                netCashFlow[i] = cashFlow;
//            }
//
//            // Calculate Discounted Factor, Discounted Cash Flow, Cumulative Cash Flow,
//            // Discounted Cost, and Discounted Production
//            BigDecimal cumulativeCashFlowValue = BigDecimal.ZERO;
//            for (int i = 0; i < rows; i++) {
//                discountedFactor[i] = BigDecimal.ONE.divide(BigDecimal.ONE.add(new BigDecimal(String.valueOf(discount_rate))).pow(years[i]), 2, RoundingMode.HALF_UP);
//                discountedCashFlow[i] = discountedFactor[i].multiply(netCashFlow[i]);
//                if (i == 0) {
//                    cumulativeCashFlow[i] = discountedCashFlow[i];
//                } else {
//                    cumulativeCashFlowValue = discountedCashFlow[i].add(cumulativeCashFlow[i - 1]);
//                    cumulativeCashFlow[i] = cumulativeCashFlowValue;
//                }
//
//                discountedCost[i] = capex[i].add(opex[i]).multiply(discountedFactor[i]);
//                discountedProduction[i] = production[i].multiply(discountedFactor[i]);
//            }
//            // Print the values for debugging purposes
//
//
//            BigDecimal[][] LCOHYears = new BigDecimal[3][5];
//            for (int i = 0; i < 3; i++) {
//                int x;
//                if (i == 0) {
//                    x = 26;
//                } else if (i == 1) {
//                    x = 31;
//                } else {
//                    x = 41;
//                }
//
//                // Calculate LCOH
//                BigDecimal sumDiscountedCost = sum(discountedCost, 0, x - 1);
//                BigDecimal sumDiscountedProduction = sum(discountedProduction, 0, x - 1);
//                BigDecimal LCOH = sumDiscountedCost.divide(sumDiscountedProduction, 2, RoundingMode.HALF_UP);
//
//                // Calculate NPV
//                BigDecimal NPV = cumulativeCashFlow[x - 1];
//
//                // Calculate IRR
//                BigDecimal[] cashflowSubset = Arrays.copyOfRange(netCashFlow, 0, x);
//                BigDecimal irrValue = calculateIRR(cashflowSubset, years);
//                BigDecimal IRR = irrValue != null ? irrValue : BigDecimal.ZERO;
//
//                // Calculate P/I
//                BigDecimal PI = cumulativeCashFlow[x - 1].divide(BigDecimal.valueOf(deep_well_CAPEX), 2, RoundingMode.HALF_UP).add(BigDecimal.ONE);
//
//                // Create and add a response object
//                LCOHYearResponse response = new LCOHYearResponse(
//                        BigDecimal.valueOf(years[x - 1]),
//                        LCOH,
//                        NPV,
//                        IRR,
//                        PI
//                );
//                responseList.add(response);
//            }
//
//            return responseList;
//        }
//    private static BigDecimal sum(BigDecimal[] array, int startIndex, int endIndex) {
//        BigDecimal sum = BigDecimal.ZERO;
//        for (int i = startIndex; i <= endIndex; i++) {
//            sum = sum.add(array[i]);
//        }
//        return sum;
//    }
//
//    private static BigDecimal calculateIRR(BigDecimal[] netCashflow, int[] years) {
//        try {
//            BigDecimal irr = BigDecimal.ZERO;
//
//            for (BigDecimal i = BigDecimal.ZERO; i.compareTo(BigDecimal.valueOf(1.001)) <= 0; i = i.add(new BigDecimal("0.001"))) {
//                irr = i;
//                BigDecimal[] cumulativeCashFlow = new BigDecimal[41];
//
//                for (int x = 0; x < years.length; x++) {
//                    BigDecimal discountFactor = BigDecimal.ONE.divide(BigDecimal.ONE.add(i).pow(years[x]), 10, RoundingMode.HALF_UP);
//                    netCashflow[x] = netCashflow[x].multiply(discountFactor);
//
//                    if (x == 0) {
//                        cumulativeCashFlow[x] = netCashflow[x];
//                    } else {
//                        cumulativeCashFlow[x] = netCashflow[x].add(netCashflow[x - 1]);
//                    }
//                }
//
//                if (cumulativeCashFlow[40].compareTo(BigDecimal.ZERO) <= 0) {
//                    break; // Found the IRR, exit the loop
//                }
//            }
//
//            return irr.multiply(BigDecimal.valueOf(100)); // Return IRR as a percentage
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//}



    public  List<LCOHYearResponse> lcohOpex( double well_capex,double selling_price,double discount_rate) {
        List<LCOHYearResponse> responseList = new ArrayList<>();
        // Step 2 - Initialize arrays
        int[] years = new int[41];
        for (int i = 0; i <= 40; i++) {
            years[i] = i;
        }

        int rows = years.length;

        // Initialize arrays
        Double[] capex = new Double[rows];
        Double[] opex = new Double[rows];
        Double[] production = new Double[rows];
        Double[] discountedFactor = new Double[rows];
        Double[] netCashFlow = new Double[rows];
        Double[] revenue = new Double[rows];
        Double[] cumulativeCashFlow = new Double[rows];
        Double[] discountedCost = new Double[rows];
        Double[] discountedProduction = new Double[rows];
        Double[] priceInflationFactor = new Double[rows];
        Double[] discountedCashFlow = new Double[rows];
        Double[] totalCostArray = new Double[rows]; // Array to store total cost

        // Set constant values
        Double mediumWellCapex = well_capex;
        Double mediumWellOpex = deep_well_OPEX;
        Double productionValue = 8600.0;
        Double price = selling_price*1000;
        Double electricalPriceInflation = electrical_Price_Inflation; // 3% inflation

        // Initialize the price inflation factor
        priceInflationFactor[0] = 1.0; // Initial value is 1.00

        // Calculate price inflation factor for the remaining years
        for (int i = 1; i < rows; i++) {
            priceInflationFactor[i] = priceInflationFactor[i - 1] * (1.0 + electricalPriceInflation);
        }

        // Populate capex, opex, and production arrays with constant values
        Double totalCost = 0.0;
        for (int i = 0; i < rows; i++) {
            capex[i] = (i == 0) ? mediumWellCapex : 0.0;
            opex[i] = (i != 0) ? mediumWellOpex : 0.0;
            production[i] = (i == 0) ? 0.0 : productionValue;
            Double cost = capex[i] + opex[i];
            totalCost = cost * priceInflationFactor[i]; // Calculate and store total cost
            // Assign capex[i] and opex[i] to costValues array
            totalCostArray[i] = totalCost;
            revenue[i] = production[i] * price * priceInflationFactor[i];
            Double cashFlow = revenue[i] - totalCost;
            // Assign cashFlow value to the netCashFlow array or use it as needed.
            netCashFlow[i] = cashFlow;
        }

        // Calculate Discounted Factor, Discounted Cash Flow, Cumulative Cash Flow,
        // Discounted Cost, and Discounted Production
        Double cumulativeCashFlowValue = 0.0;
        for (int i = 0; i < rows; i++) {
            double discountRate=discount_rate/100;
            discountedFactor[i] = 1.0 / Math.pow(1.0 + discountRate, years[i]);
            discountedCashFlow[i] = discountedFactor[i] * netCashFlow[i];
            if (i == 0) {
                cumulativeCashFlow[i] = discountedCashFlow[i];
            } else {
                cumulativeCashFlowValue = discountedCashFlow[i] + cumulativeCashFlow[i - 1];
                cumulativeCashFlow[i] = cumulativeCashFlowValue;
            }

            discountedCost[i] = (capex[i] + opex[i]) * discountedFactor[i];
            discountedProduction[i] = production[i] * discountedFactor[i];
        }

        // Calculate values for 25 years, 30 years, and 40 years
        int[] yearsToCalculate = {25, 30, 40};
        for (int i = 0; i < 3; i++) {
            int x;
            if (i == 0) {
                x = 26;
            } else if (i == 1) {
                x = 31;
            } else {
                x = 41;
            }
            // Calculate LCOH
            Double sumDiscountedCost = sum(discountedCost, 0, x - 1);
            Double sumDiscountedProduction = sum(discountedProduction, 0, x - 1);
            Double LCOH = sumDiscountedCost / sumDiscountedProduction;

            // Calculate NPV
            Double NPV = cumulativeCashFlow[x - 1];

            // Calculate IRR
            Double[] cashflowSubset = Arrays.copyOfRange(netCashFlow, 0, x);
            Double irrValue = calculateIRR(cashflowSubset, Arrays.copyOfRange(years, 0, x));
            Double IRR = irrValue != null ? irrValue : 0.0;

            // Calculate P/I
            Double PI = cumulativeCashFlow[x - 1] / deep_well_OPEX + 1.0;

            // Create and add a response object
            LCOHYearResponse response = new LCOHYearResponse(
                    BigDecimal.valueOf(x),
                    BigDecimal.valueOf(LCOH),
                    BigDecimal.valueOf(NPV),
                    BigDecimal.valueOf(IRR),
                    BigDecimal.valueOf(PI)
            );
            responseList.add(response);
        }
//        }

        return responseList;
    }

    private static Double sum(Double[] array, int startIndex, int endIndex) {
        Double sum = 0.0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += array[i];
        }
        return sum;
    }

    private static Double calculateIRR(Double[] netCashflow, int[] years) {
        try {
            Double irr = 0.0;

            for (Double i = 0.0; i.compareTo(1.001) <= 0; i += 0.001) {
                irr = i;
                Double[] cumulativeCashFlow = new Double[41];

                for (int x = 0; x < years.length; x++) {
                    Double discountFactor = 1.0 / Math.pow(1.0 + i, years[x]);
                    netCashflow[x] *= discountFactor;

                    if (x == 0) {
                        cumulativeCashFlow[x] = netCashflow[x];
                    } else {
                        cumulativeCashFlow[x] = netCashflow[x] + cumulativeCashFlow[x - 1];
                    }
                }

                if (cumulativeCashFlow[40] <= 0) {
                    break; // Found the IRR, exit the loop
                }
            }

            return irr * 100; // Return IRR as a percentage
        } catch (Exception e) {
            return null;
        }
    }
}



