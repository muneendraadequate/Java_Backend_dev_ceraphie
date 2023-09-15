package com.ceraphi.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeepWellOutPut {
    private double In;

    private double Out;
    private double Well_Outlet_Temp;

    private double Well_inlet_temp;

    private double Well_Flow_rate;

    private double Well_pressure_drop;
    private double Well_pump_power;
    private double Well_Depth;
    private double No_of_wells;
    private double Bottom_Hole_Temp;
    private double Boost_pump_power;
    private double Delivery_temp_loss;
    private double Delivery_pressure_loss;
    private double Return_Temp_loss;
    private double Return_Pressure_loss;
    private double Deliverable_Temp;
    private double Process_Return_Temp;
    private double Overall_Annual_Consumption;

}
