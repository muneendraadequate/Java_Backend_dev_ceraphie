package com.ceraphi.utils.Lcho;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor

public class LCOHYearResponse {
    private BigDecimal year;
    private BigDecimal lcoh;
    private BigDecimal npv;
    private BigDecimal irr;
    private BigDecimal pi;


    public LCOHYearResponse(BigDecimal year, BigDecimal lcoh, BigDecimal npv, BigDecimal irr, BigDecimal pi) {
        this.year = year;
        this.lcoh = lcoh;
        this.npv = npv;
        this.irr = irr;
        this.pi = pi;
    }

    public LCOHYearResponse(BigDecimal newDiscountRate, BigDecimal lcoh, BigDecimal npv) {
    }

    public LCOHYearResponse(BigDecimal newDiscountRate, BigDecimal npv) {
    }


    public LCOHYearResponse(int year, double lcoh, double npv, double irr, double pi) {
    }


    public BigDecimal getYear() {
        return year;
    }

    public BigDecimal getLcoh() {
        return lcoh;
    }

    public BigDecimal getNpv() {
        return npv;
    }

    public BigDecimal getIrr() {
        return irr;
    }

    public BigDecimal getPi() {
        return pi;
    }
}
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class LCOHYearResponse {
//    private BigDecimal year;
//    private BigDecimal lcoh;
//    private BigDecimal npv;
//    private BigDecimal irr;
//    private BigDecimal pi;}
//class LCOHYearResponse {
//    private BigDecimal year;
//    private BigDecimal lcoh;
//    private BigDecimal npv;
//    private BigDecimal irr;
//    private BigDecimal pi;
//
//    public LCOHYearResponse(BigDecimal year, BigDecimal lcoh, BigDecimal npv, BigDecimal irr, BigDecimal pi) {
//        this.year = year;
//        this.lcoh = lcoh;
//        this.npv = npv;
//        this.irr = irr;
//        this.pi = pi;
//    }
//
//    // Override the toString method to provide a custom string representation
//    @Override
//    public String toString() {
//        return "Year: " + year +
//                ", LCOH: " + lcoh +
//                ", NPV: " + npv +
//                ", IRR: " + irr +
//                ", P/I: " + pi;
//    }
//}