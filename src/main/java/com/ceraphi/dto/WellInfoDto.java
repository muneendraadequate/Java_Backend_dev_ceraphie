package com.ceraphi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WellInfoDto {
    @NotEmpty(message = "SiteName couldn't be empty")
    private String siteName;
    @NotEmpty(message = "coordinates couldn't be empty")
    private String coordinates_longitude;
    private String coordinates_latitude;
    @NotEmpty
    private List<WellDto> wells;
    private Long userId;
    private Long key;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }



    public List<WellDto> getWells() {
        return wells;
    }

    public void setWells(List<WellDto> wells) {
        this.wells = wells;
    }
    public String convertCoordinatesToString(double[] coordinates) {
        if (coordinates == null || coordinates.length == 0) {
            return null;
        }
        return Arrays.stream(coordinates)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));
    }

    // Helper method to convert string to double array
    public static double[] convertStringToCoordinates(String coordinatesStr) {
        if (coordinatesStr == null || coordinatesStr.isEmpty()) {
            return new double[0];
        }
        String[] coordinatesArr = coordinatesStr.split(",");
        return Arrays.stream(coordinatesArr)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }
}
