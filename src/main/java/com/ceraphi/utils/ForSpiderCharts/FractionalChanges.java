package com.ceraphi.utils.ForSpiderCharts;


import com.ceraphi.utils.ForSpiderCharts.FindOpexValues;
import com.ceraphi.utils.Lcho.LCOHYearResponse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


//public class FractionalChanges {
//        static DiscountRateResponse discountRateResponse;
//        public static void main(String[] args) {
//                FractionalChanges fractionalChanges=new FractionalChanges();
//            capexDiscountRates(10075807, 69317);
//                System.out.println(   discountRateResponse1.getPercentage()+ " "+discountRateResponse1.getDiscountRate()+" " +
//                discountRateResponse1.getTotalNPV());

//                DiscountRateResponse discountRateResponse1 = fractionalChanges.discountRates(10075807, 69317);
//                System.out.println(   discountRateResponse1.getPercentage()+ " "+discountRateResponse1.getDiscountRate()+" " +
//                discountRateResponse1.getTotalNPV());

//        }
//        public  DiscountRateResponse discountRates(double deepWellCapex,double deepWellOpex) {
//
//
//                //==================================deep well discountRates ========================================
//                List<Integer> percentages = new ArrayList<>();
//
//                // Create the list of percentages as you did before
//                BigDecimal change = new BigDecimal("-0.50");
//                BigDecimal step = new BigDecimal("0.10");
//
//                while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//                        int percentage = (int) (change.doubleValue() * 100);
//                        percentages.add(percentage);
//                        change = change.add(step);
//                }
//
//
//                // Original discount rate as BigDecimal
//                BigDecimal originalDiscountRate = new BigDecimal("3.5");
//
//                // Calculate new discount rates based on percentages and original discount rate
//                BigDecimal[] newDiscountRates = new BigDecimal[percentages.size()];
//
//                for (int i = 0; i < percentages.size(); i++) {
//                        int percentage = percentages.get(i);
//                        BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//                        BigDecimal newDiscountRate = originalDiscountRate.add(originalDiscountRate.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//                        newDiscountRates[i] = newDiscountRate;
//                }
//
//                FindDiscountRates findDiscountRates = new FindDiscountRates();
//                List<BigDecimal> npvValues = new ArrayList<>();
//
//                for (int i = 0; i < newDiscountRates.length; i++) {
//                        BigDecimal discountRate = newDiscountRates[i]; // Get the discount rate
//
//                        findDiscountRates.discount_rate = discountRate.divide(new BigDecimal(100)); // Set the discount rate
//                        List<LCOHYearResponse> lcohYearResponses = findDiscountRates.lcohResponseHeatPump(deepWellCapex, deepWellOpex);
//
//                        // Calculate the NPV values for this discount rate
//                        BigDecimal totalNpv = BigDecimal.ZERO;
//                        for (LCOHYearResponse response : lcohYearResponses) {
//                                BigDecimal npv = response.getNpv(); // Assuming there's a getter method for NPV in LCOHYearResponse
//                                totalNpv = totalNpv.add(npv);
//                        }
//
//                        // Add the total NPV to the list
//                        npvValues.add(totalNpv);
//                }
//
//                // Now, npvValues contains the NPV values for each discount rate
//                for (int i = 0; i < newDiscountRates.length; i++) {
//                        discountRateResponse = new DiscountRateResponse();
//                        discountRateResponse.setPercentage(percentages.get(i));
//                        discountRateResponse.setDiscountRate(newDiscountRates[i]);
//                        discountRateResponse.setTotalNPV(npvValues.get(i));
//                }
//                        return discountRateResponse;
//
//         }
//
////                ============================================ opex deepWell ==========================================================
//public static void opexDiscountRates() {
//        //                ============================================ opex deepWell ==========================================================
//
//                List<Integer> percentages = new ArrayList<>();
//
//                // Create the list of percentages as you did before
//                BigDecimal change = new BigDecimal("-0.50");
//                BigDecimal step = new BigDecimal("0.10");
//
//                while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//                        int percentage = (int) (change.doubleValue() * 100);
//                        percentages.add(percentage);
//                        change = change.add(step);
//                }
//
//                // Original discount rate as BigDecimal
//                BigDecimal originalOpex = new BigDecimal("69318.0");
//
//                // Calculate new discount rates based on percentages and original discount rate
//                BigDecimal[] newOpexs = new BigDecimal[percentages.size()];
//
//                for (int i = 0; i < percentages.size(); i++) {
//                        int percentage = percentages.get(i);
//                        BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//                        BigDecimal newOpex = originalOpex.add(originalOpex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//                        newOpexs[i] = newOpex;
//                }
//
//                FindOpexValues findOpexValues = new FindOpexValues();
//                List<BigDecimal> npvValues = new ArrayList<>();
//
////                for (int i = 0; i < newOpexs.length; i++) {
////                        System.out.println(" OPEX: " + newOpexs[i] + "   " +" percentage: " + percentages.get(i));
////                }
//
//
//                for (int i = 0; i < newOpexs.length; i++) {
//                        BigDecimal Opxes = newOpexs[i]; // Get the discount rate
//
//                        findOpexValues.medium_well_OPEX = Opxes;// Set the discount rate
//                        List<LCOHYearResponse> lcohYearResponses = findOpexValues.lcohResponseHeatPump(10075807);
//
//                        // Calculate the NPV values for this discount rate
//                        BigDecimal totalNpv = BigDecimal.ZERO;
//                        for (LCOHYearResponse response : lcohYearResponses) {
//                                BigDecimal npv = response.getNpv(); // Assuming there's a getter method for NPV in LCOHYearResponse
//                                totalNpv = totalNpv.add(npv);
//                        }
//
//                        // Add the total NPV to the list
//                        npvValues.add(totalNpv);
//                }
//                for (int i = 0; i < newOpexs.length; i++) {
//                        System.out.println("NPV: "+npvValues.get(i)+"    "+" OPEX: "+newOpexs[i]+" "+percentages.get(i));
//                }}
//=======================================Capex===========================================================================
//        public static void capexDiscountRates(double deepWellCapex,double deepWellOpex) {
//
//
//                List<Integer> percentages = new ArrayList<>();
//
//                // Create the list of percentages as you did before
//                BigDecimal change = new BigDecimal("-0.50");
//                BigDecimal step = new BigDecimal("0.10");
//
//                while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//                        int percentage = (int) (change.doubleValue() * 100);
//                        percentages.add(percentage);
//                        change = change.add(step);
//                }
//
//                // Original discount rate as BigDecimal
//                BigDecimal originalOpex = new BigDecimal(deepWellCapex);
//
//                // Calculate new discount rates based on percentages and original discount rate
//                BigDecimal[] newCapexs = new BigDecimal[percentages.size()];
//
//                for (int i = 0; i < percentages.size(); i++) {
//                        int percentage = percentages.get(i);
//                        BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//                        BigDecimal newOpex = originalOpex.add(originalOpex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//                        newCapexs[i] = newOpex;
//                }
//
//                FindCapexValues findCapexValues = new FindCapexValues();
//                List<BigDecimal> npvValues = new ArrayList<>();
//
//                for (int i = 0; i < newCapexs.length; i++) {
//                        System.out.println(" Capex: " + newCapexs[i] + "   " + " percentage: " + percentages.get(i));
//                }
//
//
//                for (int i = 0; i < newCapexs.length; i++) {
//                        BigDecimal Opxes = newCapexs[i]; // Get the discount rate
//
//                        findCapexValues.deep_well_CAPEX = Opxes;// Set the discount rate
//                        List<LCOHYearResponse> lcohYearResponses = findCapexValues.lcohResponseHeatPump(deepWellOpex);
//
//                        // Calculate the NPV values for this discount rate
//                        BigDecimal totalNpv = BigDecimal.ZERO;
//                        for (LCOHYearResponse response : lcohYearResponses) {
//                                BigDecimal npv = response.getNpv(); // Assuming there's a getter method for NPV in LCOHYearResponse
//                                totalNpv = totalNpv.add(npv);
//                        }
//
//                        // Add the total NPV to the list
//                        npvValues.add(totalNpv);
//                }
//                for (int i = 0; i < newCapexs.length; i++) {
//                        System.out.println("NPV: " + npvValues.get(i) + "    " + " Capex: " + newCapexs[i] + " " + percentages.get(i));
//                }}}
//                //=============================================== heat selling price =================================
//        }
//             public static void findTheHeatSellingPrice(double medium_well_OPEX,double medium_well_CAPE) {
//
//
//                        List<Integer> percentages = new ArrayList<>();
//
//                        // Create the list of percentages as you did before
//                        BigDecimal change = new BigDecimal("-0.50");
//                        BigDecimal step = new BigDecimal("0.10");
//
//                        while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//                                int percentage = (int) (change.doubleValue() * 100);
//                                percentages.add(percentage);
//                                change = change.add(step);
//                        }
//
//                        // Original discount rate as BigDecimal
//                        BigDecimal originalOpex = new BigDecimal(0.11 * 1000);
//
//                        // Calculate new discount rates based on percentages and original discount rate
//                        BigDecimal[] newHeatSellings = new BigDecimal[percentages.size()];
//
//                        for (int i = 0; i < percentages.size(); i++) {
//                                int percentage = percentages.get(i);
//                                BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//                                BigDecimal newHeatSelling = originalOpex.add(originalOpex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//                                newHeatSellings[i] = newHeatSelling;
//                        }
//
//                        FindHeatSellingValues findHeatSellingValues = new FindHeatSellingValues();
//                        List<BigDecimal> npvValues = new ArrayList<>();
//
//                        for (int i = 0; i < newHeatSellings.length; i++) {
//                                System.out.println(" Capex: " + newHeatSellings[i] + "   " + " percentage: " + percentages.get(i));
//                        }
//
//
//                        for (int i = 0; i < newHeatSellings.length; i++) {
//                                BigDecimal HeatSellings = newHeatSellings[i]; // Get the discount rate
//
//                                findHeatSellingValues.selling_price = HeatSellings;// Set the discount rate
//                                List<LCOHYearResponse> lcohYearResponses = findHeatSellingValues.lcohResponseHeatPump(medium_well_CAPE, medium_well_OPEX);
//
//                                // Calculate the NPV values for this discount rate
//                                BigDecimal totalNpv = BigDecimal.ZERO;
//                                for (LCOHYearResponse response : lcohYearResponses) {
//                                        BigDecimal npv = response.getNpv(); // Assuming there's a getter method for NPV in LCOHYearResponse
//                                        totalNpv = totalNpv.add(npv);
//                                }
//
//                                // Add the total NPV to the list
//                                npvValues.add(totalNpv);
//                        }
//                        for (int i = 0; i < newHeatSellings.length; i++) {
//                                System.out.println("NPV: " + npvValues.get(i) + "    " + " Capex: " + newHeatSellings[i] + " " + percentages.get(i));
//                        }
//                }
//        }



