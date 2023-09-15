package com.ceraphi.controller.Calculation;

import com.ceraphi.utils.Lcho.LCOHYearData;
import lombok.Data;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;


@RestController
@RequestMapping("/api")
public class SensitiveAnalysis {

    private double  medium_well_CAPEX=10075807;
    private double  medium_well_OPEX=69317.6;
    private double discount_rate=3.5;
    private double selling_price=0.11*1000;
    private double productionValue=86000;

    public static void main(String[] args) {
        SensitiveAnalysis sensitiveAnalysis = new SensitiveAnalysis();
        sensitiveAnalysis.lcohResponseClass();
    }
public void lcohResponseClass(){
    // Step 2 - Initialize arrays
    int[] years = new int[41];
    for (int i = 0; i <= 40; i++) {
        years[i] = i;
    }

    int rows = years.length;

// Initialize arrays
    BigDecimal [] capex = new BigDecimal [rows];
    BigDecimal[] opex = new BigDecimal[rows];
    BigDecimal[] production = new BigDecimal[rows];
    BigDecimal[] discountedFactor = new BigDecimal[rows];
    BigDecimal[] netCashFlow = new BigDecimal[rows];
    BigDecimal[] cumulativeCashFlow = new BigDecimal[rows];
    BigDecimal[] discountedCost = new BigDecimal[rows];
    BigDecimal[] discountedProduction = new BigDecimal[rows];
    BigDecimal[] priceInflationFactor = new BigDecimal[rows];

// Set constant values
    BigDecimal mediumWellCapex = new BigDecimal("10075807");
    BigDecimal mediumWellOpex = new BigDecimal("69318");
    BigDecimal productionValue = new BigDecimal("8600");
    BigDecimal price = new BigDecimal("110");
    BigDecimal electricalPriceInflation = new BigDecimal("0.03"); // 3% inflation

// Initialize the price inflation factor
    priceInflationFactor[0] = BigDecimal.ONE; // Initial value is 1.00

// Calculate price inflation factor for the remaining years
    for (int i = 1; i < rows; i++) {
        priceInflationFactor[i] = priceInflationFactor[i - 1]
                .multiply(BigDecimal.ONE.add(new BigDecimal(String.valueOf(electricalPriceInflation))))
                .setScale(2, RoundingMode.HALF_UP); }

// Populate capex, opex, and production arrays with constant values
    for (int i = 0; i < rows; i++) {
        capex[i] = (i == 0) ? mediumWellCapex :new BigDecimal(0);
        opex[i] = (i != 0) ?  mediumWellOpex:new BigDecimal(0);
        production[i] = (i == 0) ? BigDecimal.ZERO : productionValue;
    }

// Calculate Cashflow based on Price, Production, CAPEX, and OPEX
    for (int i = 0; i < rows; i++) {
        BigDecimal revenue = production[i].multiply(price).multiply(priceInflationFactor[i]);
        BigDecimal cost = capex[i].add(opex[i]).multiply(priceInflationFactor[i]);
        BigDecimal cashFlow = revenue.subtract(cost);
        // Assign cashFlow value to the discountedCashFlow array or use it as needed.
        netCashFlow[i] = cashFlow;
        // Calculate other financial metrics if needed.
    }
        for (int i = 0; i < rows; i++) {
            BigDecimal revenue = production[i].multiply(price).multiply(priceInflationFactor[i]) ;
            BigDecimal Total_cost = capex[i].add(opex[i]).multiply(priceInflationFactor[i]);
            BigDecimal discount_net_cashFlow = revenue.subtract(Total_cost);

            System.out.println("Year: " + years[i]);
            System.out.println("CAPEX: " + capex[i]);
            System.out.println("OPEX: " + opex[i]);
            System.out.println("Revenue: " + revenue);
            System.out.println("discounted net Cash flow: " + discount_net_cashFlow);
            System.out.println("net CashFlow: " + netCashFlow[i]);
            System.out.println("price inflation factor: " + priceInflationFactor[i] );
            System.out.println("total Cost: " + Total_cost);

            System.out.println(); // Add an empty line for better readability
        }



}}
