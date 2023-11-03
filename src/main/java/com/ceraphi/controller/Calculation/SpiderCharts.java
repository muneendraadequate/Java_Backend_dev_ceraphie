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
    @GetMapping("/WellCharts")
    public ResponseEntity<?> chartDataForDeepWell(@RequestParam double WellCapex, @RequestParam double WellOpex,@RequestParam double wellHeatSellingPrice, @RequestParam double discountRates) {
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
        BigDecimal originalDiscount = new BigDecimal(discountRates);
        double discountRateDouble = originalDiscount.doubleValue();
        double originalDiscountRate = discountRateDouble;
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
            findDiscountRates.discount_rate = discountRate / 100;
            List<LCOHYearResponse> lcohYearResponses = findDiscountRates.lcohDiscountRates(WellCapex, WellOpex,wellHeatSellingPrice);

            double totalNpv = 0.0;
            double totalLcoh=0.0;

            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv = npv;
                double lcoh = response.getLcoh().doubleValue();
                long lcohRound = Math.round(lcoh);
                int lcohValue = (int) lcohRound;
                totalLcoh = lcohValue;

            }

            // Create a DiscountRateResponse object and add it to the response list
            DiscountRateResponse discountRateResponse = new DiscountRateResponse();
            discountRateResponse.setPercentage((int) Double.parseDouble(decimalFormat.format(percentages.get(i))));
            discountRateResponse.setDiscountRate(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(newDiscountRates[i]))));
            discountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(totalNpv))));
            discountRateResponse.setLcoh(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(totalLcoh))));

            responseList.add(discountRateResponse);

            //===================Opex===========================
            List<OpexChartResponse> opexChartResponses = opex(WellCapex,WellOpex ,wellHeatSellingPrice,discountRates);
            //===================Capex===========================
            List<CapexChartResponse> capexChartResponses = capex(WellCapex, WellOpex,wellHeatSellingPrice,discountRates);
            //===================Heat===========================
            List<HeatChartResponse> heatChartResponses = heatSellingPrice(WellCapex,WellOpex,wellHeatSellingPrice,discountRates);



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


    public static List<OpexChartResponse> opex(double WellCapex,double WellOpex ,double wellHeatSellingPrice,double discountRates) {
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
            List<LCOHYearResponse> lcohYearResponses = findOpexValues.lcohOpex(WellCapex,wellHeatSellingPrice,discountRates);

            double totalNpv = 0.0;
            double totalLcoh=0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv = npv;
                double lcoh = response.getLcoh().doubleValue();
                long lcohRound = Math.round(lcoh);
                int lcohValue = (int) lcohRound;
                totalLcoh = lcohValue;
            }

            // Create an OpexDiscountRateResponse object and add it to the response list
            OpexChartResponse opexDiscountRateResponse = new OpexChartResponse();
            opexDiscountRateResponse.setOpex(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(newOpexs[i])))));
            opexDiscountRateResponse.setPercentage(percentages.get(i));
            opexDiscountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));

            opexDiscountRateResponse.setLcoh(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalLcoh)))));
            responseList.add(opexDiscountRateResponse);
        }

        // Return the list of OpexDiscountRateResponse objects as a ResponseEntity
        return responseList;
    }


    public static List<CapexChartResponse> capex(double WellCapex, double WellOpex,double wellSellingPrice,double discountRates) {
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
            List<LCOHYearResponse> lcohYearResponses = findCapexValues.lcohCapex(WellOpex,wellSellingPrice,discountRates);

            double totalNpv = 0.0;
                    double totalLcoh=0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv = npv;
                double lcoh = response.getLcoh().doubleValue();
                long lcohRound = Math.round(lcoh);
                int lcohValue = (int) lcohRound;
                totalLcoh = lcohValue;
            }

            // Create a CapexDiscountRateResponse object and add it to the response list
            CapexChartResponse capexDiscountRateResponse = new CapexChartResponse();
            capexDiscountRateResponse.setCapex(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(newCapexs[i])))));
            capexDiscountRateResponse.setPercentage(percentages.get(i));
            capexDiscountRateResponse.setNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));
capexDiscountRateResponse.setLcoh(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalLcoh)))));
            responseList.add(capexDiscountRateResponse);
        }

        // Return the list of CapexDiscountRateResponse objects as a ResponseEntity
        return responseList;
    }

    public static List<HeatChartResponse> heatSellingPrice(double wellCapex,double wellOPEX ,double wellHeatSellingPrice,double discountRates) {
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
            List<LCOHYearResponse> lcohYearResponses = findHeatSellingValues.lcohResponseHeatPump(wellCapex, wellOPEX,discountRates);

            double totalNpv = 0.0;
            double totalLcoh=0.0;
            for (LCOHYearResponse response : lcohYearResponses) {
                double npv = response.getNpv().doubleValue();
                totalNpv = npv;
                double lcoh = response.getLcoh().doubleValue();
                long lcohRound = Math.round(lcoh);
                int lcohValue = (int) lcohRound;
                totalLcoh = lcohValue;
            }

            // Create a HeatSellingPriceResponse object and add it to the response list
            HeatChartResponse heatSellingPriceResponse = new HeatChartResponse();
            heatSellingPriceResponse.setHeatSellingPrice(BigDecimal.valueOf(newHeatSellings[i]));
            heatSellingPriceResponse.setPercentage(percentages.get(i));
            heatSellingPriceResponse.setTotalNPV(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalNpv)))));
heatSellingPriceResponse.setLcoh(BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(BigDecimal.valueOf(totalLcoh)))));
            responseList.add(heatSellingPriceResponse);
        }
        // Return the list of HeatSellingPriceResponse objects as a ResponseEntity
        return responseList;
    }
}
