package com.ceraphi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calculation {
    private Double capacity_req_MW;
    private Double process_required_temp;
    private Double network_length;
    private Double min_operational_hours;
    private Double thermal_gradient;
    private Double ambient_temperature;
    private Double life_time_yrs;
    private Double selling_price_per_kWh;

}
