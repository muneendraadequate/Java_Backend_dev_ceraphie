package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WellData {
    private Double Depth;
       private Double FlowRate;
       private Double Cop;
       private Double WellOutletTemp;
      private Double Capacity;
      private Double KWt;
      private Double WellDepth;
     private Double BottomHoleTemp;
    private Double WellPressureDrop;
    private Double WellPumpPower;
   private Double WellInletTemp;
    private boolean isError;
    private String errorMessage;

}
