package com.ceraphi.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.util.Iterator;
public class WellDataLoader {
    public WellData getInterpolatedWellData(String excelFilePath, double requiredTemp) {

            try (InputStream inputStream = getClass().getResourceAsStream(excelFilePath);
                 Workbook workbook = new XSSFWorkbook(inputStream)) {

                if (workbook == null) {
                    throw new IllegalStateException("Workbook not initialized.");
                }

                // Assuming your data is in the first sheet (index 0)
                Sheet sheet = workbook.getSheetAt(1);

                int closestSmallerIndex = -1;
                int closestLargerIndex = -1;

                // Skip the first row (header row) by starting the loop from the second row
                Iterator<Row> rowIterator = sheet.iterator();
                rowIterator.next(); // Skip the first row

                DataFormatter dataFormatter = new DataFormatter();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    // Use DataFormatter to get cell content as a formatted string
                    String tempStr = dataFormatter.formatCellValue(row.getCell(0));

                    // Parse the formatted string as a double
                    double temp = Double.parseDouble(tempStr);

                    if (temp <= requiredTemp) {
                        closestSmallerIndex = row.getRowNum();
                    } else if (closestLargerIndex == -1) {
                        closestLargerIndex = row.getRowNum();
                    }

                    if (closestSmallerIndex != -1 && closestLargerIndex != -1) {
                        break;
                    }
                }

                if (closestSmallerIndex == -1 || closestLargerIndex == -1) {
                    String errorMessage = "Data not found for the specified temperature.";
                    WellData errorWellData = createErrorWellData(errorMessage);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorWellData).getBody();
                }

                // Interpolate values based on the formula
                Row smallerRow = sheet.getRow(closestSmallerIndex);
                Row largerRow = sheet.getRow(closestLargerIndex);

                double x1 = smallerRow.getCell(0).getNumericCellValue();
                double x2 = largerRow.getCell(0).getNumericCellValue();
                double y1FlowRate = smallerRow.getCell(1).getNumericCellValue();
                double y2FlowRate = largerRow.getCell(1).getNumericCellValue();
                double y1COP = smallerRow.getCell(2).getNumericCellValue();
                double y2COP = largerRow.getCell(2).getNumericCellValue();
                double y1WellOutletTemp = smallerRow.getCell(3).getNumericCellValue();
                double y2WellOutletTemp = largerRow.getCell(3).getNumericCellValue();
                double y1Capacity = smallerRow.getCell(4).getNumericCellValue();
                double y2Capacity = largerRow.getCell(4).getNumericCellValue();

                double interpolatedFlowRate = interpolate(x1, x2, y1FlowRate, y2FlowRate, requiredTemp);
                double interpolatedCOP = interpolate(x1, x2, y1COP, y2COP, requiredTemp);
                double interpolatedWellOutletTemp = interpolate(x1, x2, y1WellOutletTemp, y2WellOutletTemp, requiredTemp);
                double interpolatedCapacity = interpolate(x1, x2, y1Capacity, y2Capacity, requiredTemp);

                // Create and return a WellData object
                WellData wellData = new WellData();
                wellData.setFlowRate(interpolatedFlowRate);
                wellData.setCop(interpolatedCOP);
                wellData.setWellOutletTemp(interpolatedWellOutletTemp);
                wellData.setCapacity(interpolatedCapacity);

                return wellData;
            } catch (Exception e) {
                e.printStackTrace();
                return null; // Return null or handle the exception as needed
            }
    }

    private double interpolate(double x1, double x2, double y1, double y2, double requiredTemp) {
        return y1 + (requiredTemp - x1) * (y2 - y1) / (x2 - x1);

    }
    private WellData createErrorWellData(String errorMessage) {
        WellData errorWellData = new WellData();
        errorWellData.setError(true);
        errorWellData.setErrorMessage(errorMessage);
        return errorWellData;
    }
}

