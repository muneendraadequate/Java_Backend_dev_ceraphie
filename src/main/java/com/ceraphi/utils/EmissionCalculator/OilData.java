package com.ceraphi.utils.EmissionCalculator;

class OilData {
    private double efficiency;
    private double carbon;
    private double nox;
    private double noxn;
    private double ghg;

    public OilData(double efficiency, double carbon, double nox, double noxn, double ghg) {
        this.efficiency = efficiency;
        this.carbon = carbon;
        this.nox = nox;
        this.noxn = noxn;
        this.ghg = ghg;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public double getCarbon() {
        return carbon;
    }

    public double getNox() {
        return nox;
    }

    public double getNoxn() {
        return noxn;
    }

    public double getGhg() {
        return ghg;
    }
}
