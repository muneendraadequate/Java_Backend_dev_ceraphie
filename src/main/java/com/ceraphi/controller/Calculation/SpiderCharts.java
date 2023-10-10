package com.ceraphi.controller.Calculation;

import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.ForSpiderCharts.*;
import com.ceraphi.utils.Lcho.LCOHYearResponse;
import org.hibernate.validator.internal.constraintvalidators.bv.money.DecimalMaxValidatorForMonetaryAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SpiderCharts {
    //    @GetMapping("/WellCharts")
//    public ResponseEntity<?> ChartDataForDeepWell( @RequestParam double WellCapex,@RequestParam double WellOpex) {
//        if (WellCapex <= 0 || WellOpex <= 0) {
//            ApiResponseData apiResponseData = new ApiResponseData();
//            apiResponseData.setStatus(HttpStatus.NOT_FOUND.value());
//            return ResponseEntity.ok(apiResponseData);
//        }
//    List<Integer> percentages = new ArrayList<>();
//    BigDecimal change = new BigDecimal("-0.50");
//    BigDecimal step = new BigDecimal("0.10");
//
//    while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//        int percentage = (int) (change.doubleValue() * 100);
//        percentages.add(percentage);
//        change = change.add(step);
//    }
//
//    BigDecimal originalDiscountRate = new BigDecimal("3.5");
//    BigDecimal[] newDiscountRates = new BigDecimal[percentages.size()];
//
//    for (int i = 0; i < percentages.size(); i++) {
//        int percentage = percentages.get(i);
//        BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//        BigDecimal newDiscountRate = originalDiscountRate.add(originalDiscountRate.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//        newDiscountRates[i] = newDiscountRate;
//    }
//
//    FindDiscountRates findDiscountRates = new FindDiscountRates();
//    List<DiscountRateResponse> responseList = new ArrayList<>();
//
//    WellChartResponse WellChartResponse = null;
//    for (int i = 0; i < newDiscountRates.length; i++) {
//        BigDecimal discountRate = newDiscountRates[i];
//        findDiscountRates.discount_rate = discountRate.divide(new BigDecimal(100));
//        List<LCOHYearResponse> lcohYearResponses = findDiscountRates.lcohResponseHeatPump(WellCapex, WellOpex);
//
//        BigDecimal totalNpv = BigDecimal.ZERO;
//        for (LCOHYearResponse response : lcohYearResponses) {
//            BigDecimal npv = response.getNpv();
//            totalNpv = totalNpv.add(npv);
//        }
//
//        // Create a DiscountRateResponse object and add it to the response list
//        DiscountRateResponse discountRateResponse = new DiscountRateResponse();
//        discountRateResponse.setPercentage(percentages.get(i));
//        discountRateResponse.setDiscountRate(newDiscountRates[i]);
//        discountRateResponse.setNPV(totalNpv);
//        responseList.add(discountRateResponse);
//        //===================Opex===========================
//        List<OpexChartResponse> opexChartResponses = opexDiscountRates(WellOpex, WellCapex);
//        List<CapexChartResponse> capexChartResponses = capexDiscountRates(WellCapex, WellOpex);
//        List<HeatChartResponse> heatChartResponses = heatSellingPrice(WellOpex, WellCapex);
//        WellChartResponse = new WellChartResponse();
//        WellChartResponse.setDiscountRateResponse(responseList);
//        WellChartResponse.setOpexChartResponse(opexChartResponses);
//        WellChartResponse.setCapexChartResponses(capexChartResponses);
//        WellChartResponse.setHeatChartResponses(heatChartResponses);
//
//        //============================capex===========================
//
//    }
//    ApiResponseData<?> apiResponseData = ApiResponseData.builder()
//            .status(HttpStatus.OK.value())
//            .data(WellChartResponse)
//            .build();
//    return ResponseEntity.ok(apiResponseData);
//
//    }
//    ============testing=============================================================
    @GetMapping("/WellCharts")
    public ResponseEntity<?> ChartDataForDeepWell(@RequestParam double WellCapex, @RequestParam double WellOpex,@RequestParam double wellHeatSellingPrice ) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        if (WellCapex <= 0 || WellOpex <= 0) {
            ApiResponseData apiResponseData = new ApiResponseData();
            apiResponseData.setStatus(HttpStatus.NOT_FOUND.value());
            return ResponseEntity.ok(apiResponseData);
        }

        List<Integer> percentages = new ArrayList<>();
        int change = -50;
        int step = 10;
        while (change <= 50) {
            percentages.add(change);
            change += step;
        }

        double originalDiscountRate = 3.5;
        double[] newDiscountRates = new double[percentages.size()];

        for (int i = 0; i < percentages.size(); i++) {
            int percentage = percentages.get(i);
            double percentageDouble = percentage / 100.0;
            double newDiscountRate = originalDiscountRate + originalDiscountRate * percentageDouble;
            newDiscountRates[i] = newDiscountRate;
        }

        FindDiscountRates findDiscountRates = new FindDiscountRates();
        List<DiscountRateResponse> responseList = new ArrayList<>();

        WellChartResponse wellChartResponse = null;
        for (int i = 0; i < newDiscountRates.length; i++) {
            double discountRate = newDiscountRates[i];
            findDiscountRates.discount_rate = discountRate / 100.0;
            List<LCOHYearResponse> lcohYearResponses = findDiscountRates.lcohResponseHeatPump(WellCapex, WellOpex);

            double totalNpv = 0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv += npv;
            }

            // Create a DiscountRateResponse object and add it to the response list
            DiscountRateResponse discountRateResponse = new DiscountRateResponse();
            discountRateResponse.setPercentage((int) Double.parseDouble(decimalFormat.format(percentages.get(i))));
            discountRateResponse.setDiscountRate(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(newDiscountRates[i]))));
            discountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(totalNpv))));
            responseList.add(discountRateResponse);

            //===================Opex===========================
            List<OpexChartResponse> opexChartResponses = opexDiscountRates(WellOpex, WellCapex);
            List<CapexChartResponse> capexChartResponses = capexDiscountRates(WellCapex, WellOpex);
            List<HeatChartResponse> heatChartResponses = heatSellingPrice(WellOpex, WellCapex,wellHeatSellingPrice );
            wellChartResponse = new WellChartResponse();
            wellChartResponse.setDiscountRateResponse(responseList);
            wellChartResponse.setOpexChartResponse(opexChartResponses);
            wellChartResponse.setCapexChartResponses(capexChartResponses);
            wellChartResponse.setHeatChartResponses(heatChartResponses);

            //============================capex===========================
        }

        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(wellChartResponse)
                .build();
        return ResponseEntity.ok(apiResponseData);
    }


//===========================testing===========================================================

    //    public static List<OpexChartResponse> opexDiscountRates(double WellOpex,  double WellCapex) {
//        List<Integer> percentages = new ArrayList<>();
//        BigDecimal change = new BigDecimal("-0.50");
//        BigDecimal step = new BigDecimal("0.10");
//
//        while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//            int percentage = (int) (change.doubleValue() * 100);
//            percentages.add(percentage);
//            change = change.add(step);
//        }
//
//        BigDecimal originalOpex = new BigDecimal(WellOpex);
//        BigDecimal[] newOpexs = new BigDecimal[percentages.size()];
//
//        for (int i = 0; i < percentages.size(); i++) {
//            int percentage = percentages.get(i);
//            BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//            BigDecimal newOpex = originalOpex.add(originalOpex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//            newOpexs[i] = newOpex;
//        }
//
//        FindOpexValues findOpexValues = new FindOpexValues();
//        List<OpexChartResponse> responseList = new ArrayList<>();
//
//        for (int i = 0; i < newOpexs.length; i++) {
//            BigDecimal opex = newOpexs[i];
//            findOpexValues.deep_well_OPEX = opex;
//            List<LCOHYearResponse> lcohYearResponses = findOpexValues.lcohResponseHeatPump(WellCapex);
//
//            BigDecimal totalNpv = BigDecimal.ZERO;
//            for (LCOHYearResponse response : lcohYearResponses) {
//                BigDecimal npv = response.getNpv();
//                totalNpv = totalNpv.add(npv);
//            }
//
//            // Create an OpexDiscountRateResponse object and add it to the response list
//            OpexChartResponse opexDiscountRateResponse = new OpexChartResponse();
//            opexDiscountRateResponse.setOpex(newOpexs[i]);
//            opexDiscountRateResponse.setPercentage(percentages.get(i));
//            opexDiscountRateResponse.setNPV(totalNpv);
//
//            responseList.add(opexDiscountRateResponse);
//        }
//
//        // Return the list of OpexDiscountRateResponse objects as a ResponseEntity
//        return responseList;
//    }
//============testing Opex============================================================================
    public static List<OpexChartResponse> opexDiscountRates(double WellOpex, double WellCapex) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<Integer> percentages = new ArrayList<>();
        int change = -50;
        int step = 10;
        while (change <= 50) {
            percentages.add(change);
            change += step;
        }
        double originalOpex = WellOpex;
        double[] newOpexs = new double[percentages.size()];

        for (int i = 0; i < percentages.size(); i++) {
            int percentage = percentages.get(i);
            double percentageDouble = percentage;
            double newOpex = originalOpex + (originalOpex * (percentageDouble / 100));
            newOpexs[i] = newOpex;
        }

        FindOpexValues findOpexValues = new FindOpexValues();
        List<OpexChartResponse> responseList = new ArrayList<>();

        for (int i = 0; i < newOpexs.length; i++) {
            double opex = newOpexs[i];
            findOpexValues.deep_well_OPEX = opex;
            List<LCOHYearResponse> lcohYearResponses = findOpexValues.lcohResponseHeatPump(WellCapex);

            double totalNpv = 0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv += npv;
            }

            // Create an OpexDiscountRateResponse object and add it to the response list
            OpexChartResponse opexDiscountRateResponse = new OpexChartResponse();
            opexDiscountRateResponse.setOpex(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(newOpexs[i])))));
            opexDiscountRateResponse.setPercentage(percentages.get(i));
            opexDiscountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));

            responseList.add(opexDiscountRateResponse);
        }

        // Return the list of OpexDiscountRateResponse objects as a ResponseEntity
        return responseList;
    }

    //====================================testing opex=====================================================
//    public static List<CapexChartResponse> capexDiscountRates( double WellCapex, double WellOpex) {
//        List<Integer> percentages = new ArrayList<>();
//        BigDecimal change = new BigDecimal("-0.50");
//        BigDecimal step = new BigDecimal("0.10");
//
//        while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//            int percentage = (int) (change.doubleValue() * 100);
//            percentages.add(percentage);
//            change = change.add(step);
//        }
//
//        BigDecimal originalCapex = new BigDecimal(WellCapex);
//        BigDecimal[] newCapexs = new BigDecimal[percentages.size()];
//
//        for (int i = 0; i < percentages.size(); i++) {
//            int percentage = percentages.get(i);
//            BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//            BigDecimal newCapex = originalCapex.add(originalCapex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//            newCapexs[i] = newCapex;
//        }
//
//        FindCapexValues findCapexValues = new FindCapexValues();
//        List<CapexChartResponse> responseList = new ArrayList<>();
//
//        for (int i = 0; i < newCapexs.length; i++) {
//            BigDecimal capex = newCapexs[i];
//            findCapexValues.deep_well_CAPEX = capex;
//            List<LCOHYearResponse> lcohYearResponses = findCapexValues.lcohResponseHeatPump(WellOpex);
//
//            BigDecimal totalNpv = BigDecimal.ZERO;
//            for (LCOHYearResponse response : lcohYearResponses) {
//                BigDecimal npv = response.getNpv();
//                totalNpv = totalNpv.add(npv);
//            }
//
//            // Create a CapexDiscountRateResponse object and add it to the response list
//            CapexChartResponse capexDiscountRateResponse = new CapexChartResponse();
//            capexDiscountRateResponse.setCapex(newCapexs[i]);
//            capexDiscountRateResponse.setPercentage(percentages.get(i));
//            capexDiscountRateResponse.setNPV(totalNpv);
//
//            responseList.add(capexDiscountRateResponse);
//        }
//
//        // Return the list of CapexDiscountRateResponse objects as a ResponseEntity
//        return responseList;
//    }
    // =======================================testing Capex start =============================================
    public static List<CapexChartResponse> capexDiscountRates(double WellCapex, double WellOpex) {
        DecimalFormat decimalFormat=new DecimalFormat("#.##");
        List<Integer> percentages = new ArrayList<>();
        int change = -50;
        int step = 10;
        while (change <= 50) {
            percentages.add(change);
            change += step;
        }

        double originalCapex = WellCapex;
        double[] newCapexs = new double[percentages.size()];

        for (int i = 0; i < percentages.size(); i++) {
            int percentage = percentages.get(i);
            double percentageDouble = percentage;
            double newCapex = originalCapex + (originalCapex * (percentageDouble / 100));
            newCapexs[i] = newCapex;
        }

        FindCapexValues findCapexValues = new FindCapexValues();
        List<CapexChartResponse> responseList = new ArrayList<>();

        for (int i = 0; i < newCapexs.length; i++) {
            double capex = newCapexs[i];
            findCapexValues.deep_well_CAPEX = capex;
            List<LCOHYearResponse> lcohYearResponses = findCapexValues.lcohResponseHeatPump(WellOpex);

            double totalNpv = 0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv += npv;
            }

            // Create a CapexDiscountRateResponse object and add it to the response list
            CapexChartResponse capexDiscountRateResponse = new CapexChartResponse();
            capexDiscountRateResponse.setCapex(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(newCapexs[i])))));
            capexDiscountRateResponse.setPercentage(percentages.get(i));
            capexDiscountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));

            responseList.add(capexDiscountRateResponse);
        }

        // Return the list of CapexDiscountRateResponse objects as a ResponseEntity
        return responseList;
    }
    // =======================================testing Capex end =============================================

    //    public static List<HeatChartResponse> heatSellingPrice( double wellOPEX, double wellCAPE) {
//        List<Integer> percentages = new ArrayList<>();
//        BigDecimal change = new BigDecimal("-0.50");
//        BigDecimal step = new BigDecimal("0.10");
//
//        while (change.compareTo(new BigDecimal("0.51")) <= 0) {
//            int percentage = (int) (change.doubleValue() * 100);
//            percentages.add(percentage);
//            change = change.add(step);
//        }
//
//        BigDecimal originalOpex = new BigDecimal(0.11 * 1000);
//        BigDecimal[] newHeatSellings = new BigDecimal[percentages.size()];
//
//        for (int i = 0; i < percentages.size(); i++) {
//            int percentage = percentages.get(i);
//            BigDecimal percentageBigDecimal = new BigDecimal(percentage);
//
//            BigDecimal newHeatSelling = originalOpex.add(originalOpex.multiply(percentageBigDecimal.divide(new BigDecimal(100))));
//            newHeatSellings[i] = newHeatSelling;
//        }
//
//        FindHeatSellingValues findHeatSellingValues = new FindHeatSellingValues();
//        List<HeatChartResponse> responseList = new ArrayList<>();
//
//        for (int i = 0; i < newHeatSellings.length; i++) {
//            BigDecimal heatSelling = newHeatSellings[i];
//            findHeatSellingValues.selling_price = heatSelling;
//            List<LCOHYearResponse> lcohYearResponses = findHeatSellingValues.lcohResponseHeatPump(wellCAPE, wellOPEX);
//
//            BigDecimal totalNpv = BigDecimal.ZERO;
//            for (LCOHYearResponse response : lcohYearResponses) {
//                BigDecimal npv = response.getNpv();
//                totalNpv = totalNpv.add(npv);
//            }
//
//            // Create a HeatSellingPriceResponse object and add it to the response list
//            HeatChartResponse heatSellingPriceResponse = new HeatChartResponse();
//            heatSellingPriceResponse.setHeatSellingPrice(newHeatSellings[i]);
//            heatSellingPriceResponse.setPercentage(percentages.get(i));
//            heatSellingPriceResponse.setTotalNPV(totalNpv);
//
//            responseList.add(heatSellingPriceResponse);
//        }
//
//        // Return the list of HeatSellingPriceResponse objects as a ResponseEntity
//        return responseList;
//    }
//}
//=======================================testing start HeatSellingPrice====================================================================================
    public static List<HeatChartResponse> heatSellingPrice(double wellOPEX, double wellCAPE,double wellHeatSellingPrice) {
        DecimalFormat decimalFormat= new DecimalFormat("#.##");
        List<Integer> percentages = new ArrayList<>();
        int change = -50;
        int step = 10;
        while (change <= 50) {
            percentages.add(change);
            change += step;
        }

        double originalOpex = wellHeatSellingPrice* 1000;
        double[] newHeatSellings = new double[percentages.size()];

        for (int i = 0; i < percentages.size(); i++) {
            int percentage = percentages.get(i);
            double percentageDouble = percentage;

            double newHeatSelling = originalOpex + (originalOpex * (percentageDouble / 100));
            newHeatSellings[i] = newHeatSelling;
        }

        FindHeatSellingValues findHeatSellingValues = new FindHeatSellingValues();
        List<HeatChartResponse> responseList = new ArrayList<>();

        for (int i = 0; i < newHeatSellings.length; i++) {
            double heatSelling = newHeatSellings[i];
            findHeatSellingValues.selling_price = heatSelling;
            List<LCOHYearResponse> lcohYearResponses = findHeatSellingValues.lcohResponseHeatPump(wellCAPE, wellOPEX);

            double totalNpv = 0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv += npv;
            }

            // Create a HeatSellingPriceResponse object and add it to the response list
            HeatChartResponse heatSellingPriceResponse = new HeatChartResponse();
            heatSellingPriceResponse.setHeatSellingPrice(BigDecimal.valueOf(newHeatSellings[i]));
            heatSellingPriceResponse.setPercentage(percentages.get(i));
            heatSellingPriceResponse.setTotalNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));

            responseList.add(heatSellingPriceResponse);
        }

        // Return the list of HeatSellingPriceResponse objects as a ResponseEntity
        return responseList;
    }
}
//=======================================testing end HeatSellingPrice====================================================================================