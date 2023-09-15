package com.ceraphi.utils.Lcho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LCOHResponse {
    private Map<Integer, LCOHResult> results = new HashMap<>();

    private List<LCOHYearData> yearDataList;
    private List<LCOHYearData> data;

    public LCOHResponse(List<LCOHYearData> yearDataList) {
        this.yearDataList = yearDataList;
    }
    public void addResult(int year, LCOHResult result) {
        results.put(year, result);
    }

    // Getter method for results
    public Map<Integer, LCOHResult> getResults() {
        return results;
    }
    public List<LCOHYearData> getDataForYears(int... years) {
        List<LCOHYearData> filteredData = new ArrayList<>();
        for (LCOHYearData yearData : yearDataList) {
            for (int year : years) {
                if (yearData.getYear() == year) {
                    filteredData.add(yearData);
                    break;
                }
            }
        }
        return filteredData;
    }

    // Add any other methods or properties as needed
}
