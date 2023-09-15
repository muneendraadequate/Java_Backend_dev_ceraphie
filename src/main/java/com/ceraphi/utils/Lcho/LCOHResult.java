package com.ceraphi.utils.Lcho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LCOHResult {
    private BigDecimal lcoh;
    private BigDecimal npv;
    private BigDecimal irr;
    private BigDecimal pi;
    private int year; // Add a year field

    // Add a constructor to set the year
    public LCOHResult(int year, BigDecimal lcoh, BigDecimal npv, BigDecimal irr, BigDecimal pi) {
        this.year = year;
        this.lcoh = lcoh;
        this.npv = npv;
        this.irr = irr;
        this.pi = pi;
    }

    public LCOHResult(BigDecimal bigDecimal, BigDecimal bigDecimal1, BigDecimal bigDecimal2, BigDecimal add) {
    }

    // Add a getter for the year
    public int getYear() {
        return year;
    }


    // Getter methods for the fields (lcoh, npv, irr, pi)
    // Add getters for each field.
}
