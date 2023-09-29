package com.ceraphi.controller.Calculation;

import com.ceraphi.utils.Lcho.LCOHYearResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component


public class SensitivityAnalysisDeepWell {

    public double discount_rate = 0.03;
    public double selling_price = 0.11 * 1000;
    public double production_Value = 8600;


    public List<LCOHYearResponse> lcohResponseDeepWell(double Deep_well_CAPEX,double Deep_well_OPEX  ) {
        List<LCOHYearResponse> responseList = new ArrayList<>();

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
        BigDecimal[] netCashFlow = new BigDecimal[rows];
        BigDecimal[] revenue = new BigDecimal[rows];
        BigDecimal[] cumulativeCashFlow = new BigDecimal[rows];
        BigDecimal[] discountedCost = new BigDecimal[rows];
        BigDecimal[] discountedProduction = new BigDecimal[rows];
        BigDecimal[] priceInflationFactor = new BigDecimal[rows];
        BigDecimal[] discountedCashFlow = new BigDecimal[rows];

        // Set constant values
        BigDecimal DeepWellCapex = new BigDecimal(Deep_well_CAPEX);
        BigDecimal DeepWellOpex = new BigDecimal(Deep_well_OPEX);
        BigDecimal productionValue = new BigDecimal(production_Value);
        BigDecimal price = new BigDecimal(selling_price);
        BigDecimal electricalPriceInflation = new BigDecimal("0.03"); // 3% inflation

        // Initialize the price inflation factor
        priceInflationFactor[0] = BigDecimal.ONE; // Initial value is 1.00

        // Calculate price inflation factor for the remaining years
        for (int i = 1; i < rows; i++) {
            priceInflationFactor[i] = priceInflationFactor[i - 1]
                    .multiply(BigDecimal.ONE.add(new BigDecimal(String.valueOf(electricalPriceInflation))))
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Populate capex, opex, and production arrays with constant values
        BigDecimal totalCost = BigDecimal.ZERO;
        for (int i = 0; i < rows; i++) {
            capex[i] = (i == 0) ? DeepWellCapex : BigDecimal.ZERO;
            opex[i] = (i != 0) ? DeepWellOpex : BigDecimal.ZERO;
            production[i] = (i == 0) ? BigDecimal.ZERO : productionValue;
            totalCost = totalCost.add(capex[i].add(opex[i]).multiply(priceInflationFactor[i]));
        }

        // Calculate Cashflow based on Price, Production, CAPEX, and OPEX
        for (int i = 0; i < rows; i++) {
            revenue[i] = production[i].multiply(price).multiply(priceInflationFactor[i]);
            BigDecimal cost = capex[i].add(opex[i]);
            BigDecimal cashFlow = revenue[i].subtract(cost);
            // Assign cashFlow value to the netCashFlow array or use it as needed.
            netCashFlow[i] = cashFlow;
        }

        // Calculate Discounted Factor, Discounted Cash Flow, Cumulative Cash Flow,
        // Discounted Cost, and Discounted Production
        BigDecimal cumulativeCashFlowValue = BigDecimal.ZERO;
        for (int i = 0; i < rows; i++) {
            discountedFactor[i] = BigDecimal.ONE.divide(BigDecimal.ONE.add(new BigDecimal(discount_rate)).pow(years[i]), 2, RoundingMode.HALF_UP);
            discountedCashFlow[i] = discountedFactor[i].multiply(netCashFlow[i]);
            if (i == 0) {
                cumulativeCashFlow[i] = discountedCashFlow[i];
            } else {
                cumulativeCashFlowValue = discountedCashFlow[i].add(cumulativeCashFlow[i - 1]);
                cumulativeCashFlow[i] = cumulativeCashFlowValue;
            }

            discountedCost[i] = capex[i].add(opex[i]).multiply(discountedFactor[i]);
            discountedProduction[i] = production[i].multiply(discountedFactor[i]);
        }


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
            BigDecimal sumDiscountedCost = sum(discountedCost, 0, x - 1);
            BigDecimal sumDiscountedProduction = sum(discountedProduction, 0, x - 1);
            BigDecimal LCOH = sumDiscountedCost.divide(sumDiscountedProduction, 2, RoundingMode.HALF_UP);

            // Calculate NPV
            BigDecimal NPV = cumulativeCashFlow[x - 1];

            // Calculate IRR
            BigDecimal[] cashflowSubset = Arrays.copyOfRange(netCashFlow, 0, x);
            BigDecimal irrValue = calculateIRR(cashflowSubset, years);
            BigDecimal IRR = irrValue != null ? irrValue : BigDecimal.ZERO;

            // Calculate P/I
            BigDecimal PI = cumulativeCashFlow[x - 1].divide(BigDecimal.valueOf(Deep_well_CAPEX), 2, RoundingMode.HALF_UP).add(BigDecimal.ONE);

            // Create and add a response object
            LCOHYearResponse response = new LCOHYearResponse(
                    BigDecimal.valueOf(years[x - 1]),
                    LCOH,
                    NPV,
                    IRR,
                    PI
            );
            responseList.add(response);
        }

        return responseList;
    }

    private static BigDecimal sum(BigDecimal[] array, int startIndex, int endIndex) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = startIndex; i <= endIndex; i++) {
            sum = sum.add(array[i]);
        }
        return sum;
    }

    private static BigDecimal calculateIRR(BigDecimal[] netCashflow, int[] years) {
        try {
            BigDecimal irr = BigDecimal.ZERO;

            for (BigDecimal i = BigDecimal.ZERO; i.compareTo(BigDecimal.valueOf(1.001)) <= 0; i = i.add(new BigDecimal("0.001"))) {
                irr = i;
                BigDecimal[] cumulativeCashFlow = new BigDecimal[41];

                for (int x = 0; x < years.length; x++) {
                    BigDecimal discountFactor = BigDecimal.ONE.divide(BigDecimal.ONE.add(i).pow(years[x]), 10, RoundingMode.HALF_UP);
                    netCashflow[x] = netCashflow[x].multiply(discountFactor);

                    if (x == 0) {
                        cumulativeCashFlow[x] = netCashflow[x];
                    } else {
                        cumulativeCashFlow[x] = netCashflow[x].add(netCashflow[x - 1]);
                    }
                }

                if (cumulativeCashFlow[40].compareTo(BigDecimal.ZERO) <= 0) {
                    break; // Found the IRR, exit the loop
                }
            }

            return irr.multiply(BigDecimal.valueOf(100)); // Return IRR as a percentage
        } catch (Exception e) {
            return null;
        }
    }
}