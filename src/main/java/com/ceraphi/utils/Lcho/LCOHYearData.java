package com.ceraphi.utils.Lcho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class LCOHYearData {
    private BigDecimal lcoh;
    private BigDecimal npv;
    private BigDecimal irr;
    private BigDecimal pi;
    private int year;

    public LCOHYearData(BigDecimal lcoh, BigDecimal npv, BigDecimal irr, BigDecimal pi) {
        this.lcoh = lcoh;
        this.npv = npv;
        this.irr = irr;
        this.pi = pi;
    }}
