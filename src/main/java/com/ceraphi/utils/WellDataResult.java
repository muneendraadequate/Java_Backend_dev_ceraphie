package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WellDataResult {
    private  double WellInletTemp;
  private double  NumberOfWells;
  private double WellDepthTVD;
  private double BottomHoleTemp;
  private double WellPressureDrop;
  private double WellPumpPower;
  private double WellOutletTemp;
  private double wellFlowRate;
  private double kwt;

}
