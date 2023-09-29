package com.ceraphi.utils.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TotalValues {
    private String installed;
    private String TonesOfCo2;
    private String capex;
    private String nonProductiveTime;
    private String operationHours;


}
