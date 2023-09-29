package com.ceraphi.utils.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GeographicalAnalysis {
    private String Country ;
    private String longitude;
    private String latitude;
}
