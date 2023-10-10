package com.ceraphi.utils.EmissionCalculator;

import com.ceraphi.utils.EmissionData;
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

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
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

                bau.setCarbon(Double.parseDouble(decimalFormat.format(requiredCapacity * gasData.getCarbon() * loadHours / gasData.getEfficiency())));
                bau.setNox(Double.parseDouble(decimalFormat.format(requiredCapacity * gasData.getNox() * loadHours / gasData.getEfficiency())));
                bau.setNoxn(Double.parseDouble(decimalFormat.format(requiredCapacity * gasData.getNoxn() * loadHours / gasData.getEfficiency())));
                bau.setGhg(Double.parseDouble(decimalFormat.format(requiredCapacity * electricData.getGhg() * loadHours / gasData.getEfficiency())));

            }
            if (oilData != null) {

                medium.setCarbon(Double.parseDouble(decimalFormat.format(annualConsumptionMediumWell * electricData.getCarbon())));
                medium.setNox(Double.parseDouble(decimalFormat.format(annualConsumptionMediumWell * electricData.getNox())));
                medium.setNoxn(Double.parseDouble(decimalFormat.format(annualConsumptionMediumWell * electricData.getNoxn())));
                medium.setGhg(Double.parseDouble(decimalFormat.format(annualConsumptionMediumWell * electricData.getGhg())));


            }
            if (gasData != null) {

                deep.setCarbon(Double.parseDouble(decimalFormat.format(annualConsumptionDeepWell * electricData.getCarbon())));
                deep.setNox(Double.parseDouble(decimalFormat.format(annualConsumptionDeepWell * electricData.getNox())));
                deep.setNoxn(Double.parseDouble(decimalFormat.format(annualConsumptionDeepWell * electricData.getNoxn())));
                deep.setGhg(Double.parseDouble(decimalFormat.format(annualConsumptionDeepWell * electricData.getGhg())));

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


}
