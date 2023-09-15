package com.ceraphi.utils.Lcho;

import lombok.Data;

import java.math.BigDecimal;
@Data
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

//    public LCOHYearResponse(int i, double v, double v1, double v2, double v3) {
//        this.year = i;
//        this.lcoh = v;
//        this.npv = v1;
//        this.irr = v2;
//        this.pi = v3;
//    }


//    public LCOHYearResponse(int i, double v, double v1, double v2, double v3) {
//        this.year = i;
//        this.lcoh = v;
//        this.npv = v1;
//        this.irr = v2;
//        this.pi = v3;
//    }

//    public LCOHYearResponse(BigDecimal bigDecimal, BigDecimal lcoh, BigDecimal npv, BigDecimal bigDecimal1, BigDecimal pi) {
//    }
//}

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
