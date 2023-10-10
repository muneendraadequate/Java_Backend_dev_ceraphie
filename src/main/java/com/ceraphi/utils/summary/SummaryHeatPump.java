package com.ceraphi.utils.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryHeatPump {
        private BigDecimal year;
        private BigDecimal lcoh;
        private BigDecimal npv;
        private BigDecimal irr;
        private BigDecimal pi;
        private int period;


    }
