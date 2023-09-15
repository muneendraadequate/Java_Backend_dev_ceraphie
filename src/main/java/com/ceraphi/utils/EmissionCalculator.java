package com.ceraphi.utils;

import com.ceraphi.utils.Lcho.Current;
import com.ceraphi.utils.Lcho.Deep;
import com.ceraphi.utils.Lcho.Medium;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;


public class EmissionCalculator {
    public static Current bau = new Current();
    public static Medium medium = new Medium();
    public static Deep deep = new Deep();

    public EmissionData calculateEmissions(double requiredCapacity, double loadHours, double annualConsumptionMediumWell, double annualConsumptionDeepWell) throws IOException {


        try (InputStream inputStream = EmissionCalculator.class.getResourceAsStream("/HeatLoadFuels.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet

            ElectricData electricData = null;
            OilData oilData = null;
            GasData gasData = null;

            // Read data for Electric, Oil, and Gas
            for (int rowIndex = 1; rowIndex <= 3; rowIndex++) { // Start from 1 to skip the header row
                Row row = sheet.getRow(rowIndex);
                String fuelType = row.getCell(0).getStringCellValue();
                double efficiency = getNumericCellValue(row.getCell(1));
                double carbon = getNumericCellValue(row.getCell(2));
                double nox = getNumericCellValue(row.getCell(3));
                double noxn = getNumericCellValue(row.getCell(4));
                double ghg = getNumericCellValue(row.getCell(5));

                switch (fuelType) {
                    case "Electric":
                        electricData = new ElectricData(efficiency, carbon, nox, noxn, ghg);
                        break;
                    case "Oil":
                        oilData = new OilData(efficiency, carbon, nox, noxn, ghg);
                        break;
                    case "Gas":
                        gasData = new GasData(efficiency, carbon, nox, noxn, ghg);
                        break;
                }
            }

            // Check if any of the data is null before using it
            if (electricData != null) {

                bau.setCarbon(requiredCapacity * gasData.getCarbon() * loadHours / gasData.getEfficiency());
                bau.setNox(requiredCapacity * gasData.getNox() * loadHours / gasData.getEfficiency());
                bau.setNoxn(requiredCapacity * gasData.getNoxn() * loadHours / gasData.getEfficiency());
                bau.setGhg(requiredCapacity * electricData.getGhg() * loadHours / gasData.getEfficiency());

            }
            if (oilData != null) {

                medium.setCarbon(annualConsumptionMediumWell * electricData.getCarbon());
                medium.setNox(annualConsumptionMediumWell * electricData.getNox());
                medium.setNoxn(annualConsumptionMediumWell * electricData.getNoxn());
                medium.setGhg(annualConsumptionMediumWell * electricData.getGhg());


            }
            if (gasData != null) {

                deep.setCarbon(annualConsumptionDeepWell * electricData.getCarbon());
                deep.setNox(annualConsumptionDeepWell * electricData.getNox());
                deep.setNoxn(annualConsumptionDeepWell * electricData.getNoxn());
                deep.setGhg(annualConsumptionDeepWell * electricData.getGhg());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmissionData emissionData = new EmissionData();
        emissionData.setCurrent(bau);
        emissionData.setMedium(medium);
        emissionData.setDeep(deep);

        return emissionData;
    }

    private static double getNumericCellValue(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                // Handle parsing error gracefully
                return 0.0; // You can change this default value as needed
            }
        }
        return 0.0; // Default value for non-numeric cell types
    }

    private static String formatToDecimal(double value) {
        DecimalFormat df = new DecimalFormat("0.00000");
        return df.format(value);
    }
}


class ElectricData {
    private double efficiency;
    private double carbon;
    private double nox;
    private double noxn;
    private double ghg;

    public ElectricData(double efficiency, double carbon, double nox, double noxn, double ghg) {
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

class GasData {
    private double efficiency;
    private double carbon;
    private double nox;
    private double noxn;
    private double ghg;

    public GasData(double efficiency, double carbon, double nox, double noxn, double ghg) {
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