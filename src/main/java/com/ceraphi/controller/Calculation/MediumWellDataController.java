package com.ceraphi.controller.Calculation;

import com.ceraphi.entities.Calculation;
import com.ceraphi.services.WellDataService;
import com.ceraphi.utils.*;
import com.ceraphi.utils.Lcho.LCOHYearResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/api")
public class MediumWellDataController {
    public static DeepWellOutPut deepWellOutPut = new DeepWellOutPut();
    public static int WELL_DELTA = 20;
    public static CostEstimationDeepWell costEstimationDeepWell = new CostEstimationDeepWell();
    public static DeepWellOpex deepWellOpex = new DeepWellOpex();
    public static double ELECTRICAL_cost = 0.2;
    public static CalculationResponses doubleResponseClass = new CalculationResponses();
    private static final double INTERNAL_DIAM_TUBULAR = 0.050673;
    private static final double INTERNAL_DIAM_ANNULUS = 0.0604164;
    private static final double INTERNAL_DIAM_COUPLING = 0.045466;
    private static final double DEPTH = 1500; // replace welldepth with actual value
    public static OutPutCalculationHeatPump outputCalculation = new OutPutCalculationHeatPump();
    @Autowired
    private WellDataService wellDataService;
    public static CostEstimationHeatPump costEstimationHeatPump = new CostEstimationHeatPump();
    private static WellDataLoader loader = new WellDataLoader();
    public static HeatPumpOpex heatPumpOpex = new HeatPumpOpex();
    public static double electricalCost = 0.2;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculation(@RequestBody Calculation calculation) throws IOException {
        //HeatPump calculation Start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        double condenser = calculation.getCapacity_req_MW();
        double requiredTemp = calculation.getProcess_required_temp();
        double req_temp = calculation.getProcess_required_temp(); // Specify the required temperature here
        WellData interpolatedWellData = loader.getInterpolatedWellData("/GELData1500mwells.xlsx", req_temp);
        double FLOW = interpolatedWellData.getFlowRate();
        outputCalculation.setCondenser(condenser);
        outputCalculation.setDeliverable_Temp(requiredTemp);
        outputCalculation.setWell_Depth(DEPTH);
        outputCalculation.setProcess_Return_Temp(requiredTemp - 20);
        outputCalculation.setIn(interpolatedWellData.getWellOutletTemp());
        outputCalculation.setOut(interpolatedWellData.getWellOutletTemp() - 5);
        outputCalculation.setSCOP(interpolatedWellData.getCop());
        outputCalculation.setWell_Flow_rate(interpolatedWellData.getFlowRate());
        outputCalculation.setWell_Outlet_Temp(interpolatedWellData.getWellOutletTemp());
        outputCalculation.setWell_inlet_temp(interpolatedWellData.getWellOutletTemp() - 5);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        double Input = condenser / interpolatedWellData.getCop();
// Format the double value to have one decimal place
        double roundedValue = Double.parseDouble(decimalFormat.format(Input));

        double workInput = roundedValue;
        double evaporator = condenser - workInput;
        outputCalculation.setEvaporator(evaporator);
        double wells = Math.round((evaporator / (interpolatedWellData.getCapacity() / 1000)));
        outputCalculation.setNo_of_wells(wells);
        double v = wellDataService.calculatePressureDrop(FLOW, DEPTH, INTERNAL_DIAM_TUBULAR, INTERNAL_DIAM_ANNULUS, INTERNAL_DIAM_COUPLING);
        double wellPressureDrop = v / 100;
        outputCalculation.setWell_pressure_drop(wellPressureDrop);
        double pumpEfficiency = 0.75;
        double power = (FLOW) * (wellPressureDrop * 0.1);
        power = power / pumpEfficiency;


// Format the double value to have one decimal place
        double pumpingPower = Double.parseDouble(decimalFormat.format(power));
        double wellPumpingPower = pumpingPower;
        outputCalculation.setWell_pump_power(wellPumpingPower);
        double flow = interpolatedWellData.getFlowRate() * wells;
        double v1 = wellDataService.calculateBoostPumpPower(flow
                , calculation.getNetwork_length(), wells);
        double delivery$PressureLoss = v1 * calculation.getNetwork_length();
        outputCalculation.setDelivery_pressure_loss(delivery$PressureLoss);
        outputCalculation.setReturn_Pressure_loss(delivery$PressureLoss);
        double boostPumpPower = (delivery$PressureLoss + delivery$PressureLoss) * 0.1 * interpolatedWellData.getFlowRate() * wells * calculation.getNetwork_length();
        double boostPumpEfficiency = 0.75;
        double Boost_PumpPower = boostPumpPower / boostPumpEfficiency;
        double BoostPumpPower = Double.parseDouble(decimalFormat.format(Boost_PumpPower));
        outputCalculation.setBoost_pump_power(BoostPumpPower);
        double annualConsumption = ((BoostPumpPower + wellPumpingPower * wells) / 1000.0 + workInput) * calculation.getMin_operational_hours();
        outputCalculation.setOverall_Annual_Consumption(annualConsumption);
        outputCalculation.setDelivery_temp_loss(0.0);
        outputCalculation.setReturn_Temp_loss(0.0);
        outputCalculation.setBottom_Hole_Temp(calculation.getThermal_gradient() * 1.5);
        //HeatPump calculation for output screen End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        //HeatPump calculation for CAPEX start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        try (InputStream inputStream = getClass().getResourceAsStream("/EstimatedCostDatabasehp.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // Assuming the Excel columns are: Operation, Cost (GBP), Per Well?
                Cell operationCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell costCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell perWellCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String operation = operationCell.getStringCellValue().trim();
                double cost = costCell.getCellType() == CellType.NUMERIC ? costCell.getNumericCellValue() : 0.0;
                String perWell = perWellCell.getStringCellValue().trim();
                // Set the values for these variables based on your data
                // Replace with your actual value
                double well = 2;
                double wellDepth = DEPTH; // Replace with your actual value
                double requiredCapacity = calculation.getCapacity_req_MW(); // Replace with your actual value
                double networkLength = calculation.getNetwork_length(); // Replace with your actual value

                // Perform calculations based on conditions
                if ("Y".equalsIgnoreCase(perWell)) {
                    if ("Borehole drilling & construction".equalsIgnoreCase(operation) ||
                            "Borehole completion".equalsIgnoreCase(operation)) {
                        cost *= well;
                    } else if ("Heat pump & heat exchanger installation".equalsIgnoreCase(operation)) {
                        cost *= requiredCapacity * 1000;
                    } else if ("Heat connection".equalsIgnoreCase(operation)) {
                        cost *= networkLength * 1000 * 2;
                    }
                }

                // Store the calculated cost in the appropriate field of the CostEstimationHeatPump object
                switch (operation) {
                    case "Site preparation & civil engineering":
                        costEstimationHeatPump.setSite_preparation_civil_engineering(cost);
                        break;
                    case "Drilling unit mob/demob":
                        costEstimationHeatPump.setDrilling_unit_mob_demob(cost);
                        break;
                    case "Borehole drilling & construction":
                        costEstimationHeatPump.setBorehole_drilling_construction(cost * wellDepth);
                        break;
                    case "Borehole completion":
                        costEstimationHeatPump.setBorehole_completion(cost * wellDepth);
                        break;
                    case "Heat pump & heat exchanger installation":
                        double heatpump = cost;
                        costEstimationHeatPump.setHeat_pump_heat_exchanger_installation(cost * requiredCapacity * 1000);
                        break;
                    case "Heat connection":
                        costEstimationHeatPump.setHeat_connection(cost * networkLength * 1000 * 2);
                        break;
                    case "Plate heat exchanger":
                        costEstimationHeatPump.setPlate_heat_exchanger(cost);
                        break;
                    default:
                        break;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate the total CAPEXHeatPump
        double totalCAPEXmedium = costEstimationHeatPump.getSite_preparation_civil_engineering() +
                costEstimationHeatPump.getDrilling_unit_mob_demob() +
                costEstimationHeatPump.getBorehole_drilling_construction() +
                costEstimationHeatPump.getBorehole_completion() +
                costEstimationHeatPump.getHeat_pump_heat_exchanger_installation() +
                costEstimationHeatPump.getHeat_connection() +
                costEstimationHeatPump.getPlate_heat_exchanger();
        costEstimationHeatPump.setTotal(totalCAPEXmedium);
        doubleResponseClass.setOutPutCalculationHeatPump(outputCalculation);
        doubleResponseClass.setCostEstimationHeatPump(costEstimationHeatPump);
        //HeatPump calculation for CAPEX End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        //================================startOfOpexHeatPump=========================================================
        double totalOpex = 0;
        try (InputStream inputStream = getClass().getResourceAsStream("/EstimatedCostDatabasehp.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(3); // Assuming the data is on the fourth sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Assuming the Excel columns are: Operation, Cost (GBP), Per Well?
                Cell operationCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell costCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell perWellCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String operation = operationCell.getStringCellValue();
                double cost = costCell.getCellType() == CellType.NUMERIC ? costCell.getNumericCellValue() : 0.0;
                String perWell = perWellCell.getStringCellValue().trim();

                // Perform calculations based on conditions
                if ("Y".equalsIgnoreCase(perWell)) {
                    if ("Heat pump electrical consumption".equalsIgnoreCase(operation)) {
                        // Multiply cost by well pumping power and 8600, then by electrical cost
                        cost *= wells;
                    } else if ("Pumping power consumption".equalsIgnoreCase(operation)) {
                        // Multiply cost by 8600, then by electrical cost
                        cost *= wells;
                    } else if ("Well Maintenance ".equalsIgnoreCase(operation)) {
                        // Multiply cost by no of wells from deep well output
                        cost *= wells;
                    }
                }

                // Store the calculated cost in the appropriate field of the DeepWellOpex object
                switch (operation) {
                    case "Heat pump electrical consumption":
                        heatPumpOpex.setHeat_pump_electrical_consumption(cost * workInput * 1000 * calculation.getMin_operational_hours() * electricalCost);
                        break;
                    case "Pumping power consumption":
                        double pumpingPowerConsumption = cost; // As it has 'Y' in per well column
                        pumpingPowerConsumption = (pumpingPowerConsumption * wellPumpingPower + BoostPumpPower) * calculation.getMin_operational_hours() * electricalCost;
                        heatPumpOpex.setPumping_power_consumption(pumpingPowerConsumption);
                        break;
                    case "Well Maintenance ":
                        heatPumpOpex.setWellMaintenance(cost);
                        break;
                    default:
                        break;
                }
            }
            totalOpex = heatPumpOpex.getPumping_power_consumption() + heatPumpOpex.getWellMaintenance() + heatPumpOpex.getHeat_pump_electrical_consumption();
            heatPumpOpex.setTotalOpex(totalOpex);
            heatPumpOpex.setWellMaintenance(heatPumpOpex.getWellMaintenance());
            heatPumpOpex.setHeat_pump_electrical_consumption(heatPumpOpex.getHeat_pump_electrical_consumption());
            heatPumpOpex.setPumping_power_consumption(heatPumpOpex.getPumping_power_consumption());
            doubleResponseClass.setHeatPumpOpex(heatPumpOpex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //================================END   Of   OPEX HEAT PUMP   =========================================================


//----------------------------------------------------------------------------------------------------------
        //deepWell method calling
        DeepWellOutPutController deepWellOutPutController = new DeepWellOutPutController();
        DoubleResponseDeep doubleResponseDeep = deepWellOutPuts(calculation);
        doubleResponseClass.setDeepWellOutPutCalculation(doubleResponseDeep.getDeepWellOutPutCalculation());
        doubleResponseClass.setCostEstimationDeepWell(doubleResponseDeep.getCostEstimationDeepWell());
        doubleResponseClass.setDeepWellOpex(doubleResponseDeep.getDeepWellOpex());


        double DeepWellAnnualConsumption = doubleResponseDeep.getDeepWellOutPutCalculation().getOverall_Annual_Consumption();
        EmissionCalculator emissionCalculator = new EmissionCalculator();
        EmissionData emissionData = emissionCalculator.calculateEmissions(calculation.getCapacity_req_MW(), calculation.getMin_operational_hours(), annualConsumption, DeepWellAnnualConsumption);
        doubleResponseClass.setEmissionData(emissionData);
        //lcoh of DeepWell
        SensitiveAnalysisDeepWell sensitiveAnalysisDeepWell = new SensitiveAnalysisDeepWell();
        CostEstimationDeepWell costEstimationDeepWell1 = doubleResponseDeep.getCostEstimationDeepWell();
        double DeepWelltotalCAPEX =10075807;
        DeepWellOpex deepWellOpex1 = doubleResponseDeep.getDeepWellOpex();
        double DeepWelltotalOPEX =69317.6;
        List<LCOHYearResponse> lcohYearResponses = sensitiveAnalysisDeepWell.lcohResponseDeepWell(DeepWelltotalCAPEX,DeepWelltotalOPEX);
        doubleResponseClass.setLcohYearResponseDeepWell(lcohYearResponses);
        //lcoh of HeatPump
        SensitiviteAnalysisHeatPump sensitiveAnalysisHeatPump = new SensitiviteAnalysisHeatPump();
        List<LCOHYearResponse> lcohYearResponses1 = sensitiveAnalysisHeatPump.lcohResponseHeatPump(totalCAPEXmedium, totalOpex);
        doubleResponseClass.setLcohYearResponseHeatPumpWell(lcohYearResponses1);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(doubleResponseClass)
                .build();
        return ResponseEntity.ok(apiResponseData);


    }


    //DeepWellOutPutCalculation Data starting =========================================================================================================================
    public DoubleResponseDeep deepWellOutPuts(Calculation calculation) {


        // step 1:---------------------------------------------------------------------
        Double deliverableTemp = calculation.getProcess_required_temp();
        Double networkLength = calculation.getNetwork_length();
        deepWellOutPut.setDeliverable_Temp(deliverableTemp);
        deepWellOutPut.setProcess_Return_Temp(deliverableTemp - 20);


        //step 2:----------------------------------------------------------------
        double minWellOutLetTemp = deliverableTemp * 1.2;
        int wellFlowRate = 15;
        int DeepWellFlowRate = 15;
        deepWellOutPut.setWell_Flow_rate(wellFlowRate);


        //step 3:---------------------------------------------------------------
        List<ExcelColumns> closestRowByGradient = findClosestRowByGradient(calculation.getThermal_gradient());


        //step 4:---------------------------------------------------------------
        WellDataResult wellDataResult = processWellData(closestRowByGradient, WELL_DELTA, minWellOutLetTemp, DeepWellFlowRate);
        deepWellOutPut.setWell_pump_power(wellDataResult.getWellPumpPower());
        double wellPumpPower = wellDataResult.getWellPumpPower();
        deepWellOutPut.setWell_pressure_drop(wellDataResult.getWellPressureDrop());
        deepWellOutPut.setBottom_Hole_Temp(wellDataResult.getBottomHoleTemp());
        deepWellOutPut.setWell_Depth(wellDataResult.getWellDepthTVD());
        double well_Depth = wellDataResult.getWellDepthTVD();
        deepWellOutPut.setWell_Outlet_Temp(wellDataResult.getWellOutletTemp());
        double wellOutletTemp = wellDataResult.getWellOutletTemp();
        deepWellOutPut.setWell_inlet_temp(minWellOutLetTemp - WELL_DELTA);
        double wells = calculation.getCapacity_req_MW() / (wellDataResult.getKwt() / 1000);
        int noOfWells = (int) Math.ceil(wells);
        deepWellOutPut.setNo_of_wells(noOfWells);


        //step 5:----------------------------------------------------------------
        double flow = DeepWellFlowRate * noOfWells;
        PressureData pressureData = wellDataService.calculateBoostPumpPowerForDeepWell(flow, noOfWells, calculation.getNetwork_length());
        deepWellOutPut.setDelivery_pressure_loss(pressureData.getPressure());
        deepWellOutPut.setReturn_Pressure_loss(pressureData.getPressure());
        // Calculate boost pump power without efficiency
        double boostPumpPower = (pressureData.getPressure() + pressureData.getPressure()) * 0.1 * wellFlowRate * noOfWells * networkLength;
        // Adjust boost pump power for efficiency
        double boostPumpEfficiency = 0.75;
        double BoostPumpPower = boostPumpPower / boostPumpEfficiency;
        deepWellOutPut.setBoost_pump_power(BoostPumpPower);


        //step 6:---------------------------------------------------------------
        double ID = pressureData.getInternalDiameter();
        double ambientTemp = calculation.getAmbient_temperature();
        double tempLossPerKM = calculateTempLossFromHEXAndDeliveryPipe(ambientTemp, ID, flow, wellOutletTemp);
        deepWellOutPut.setDelivery_temp_loss(tempLossPerKM);
        // Calculate IN (initial temperature at the well outlet)
        double IN = wellOutletTemp - (tempLossPerKM * networkLength);
        // Calculate Out using IN, requiredCapacity, wellFlowRate, noOfWells
        double Out = IN - (Math.round(calculation.getCapacity_req_MW() * 1000) / (wellFlowRate * noOfWells * 4.184));
        deepWellOutPut.setIn(IN);
        deepWellOutPut.setOut(Out);


        //step 7:---------------------------------------------------------------
        // Initialize constants
        double Thermal_Conductivity_pipe = 0.0244;
        int Density = 997;
        double Cp = 4.184;
        int Pipe_Thickness = 2;
        int Length = 1000;
        // ID is the pipe internal diameter calculated in step 3
        // Ambient temp is a user input
        double Ambient_Temp = calculation.getAmbient_temperature();
        // Assign the user-provided ambient temperature here
        // Get radii
        double internalDiameter = ID * 0.0254; // CONVERT TO M
        double thickness = Pipe_Thickness * 0.0254;
        double externalDiameter = internalDiameter + 2 * thickness;
        double radiusExternal = externalDiameter / 2;
        double radiusInternal = internalDiameter / 2;
        // Calculate thermal resistance of pipe
        double thermalResistance = Math.log(radiusExternal / radiusInternal) / Thermal_Conductivity_pipe;
        // Calculate thermal energy across pipe
        // Convert length from km to m
        double Q = 2 * Math.PI * Length * 1000 * (wellOutletTemp - Ambient_Temp) / thermalResistance;
        // Calculate output Temp
        // kJ to J and 1 to m3 conversion cancel.
        double Temp_Out = wellOutletTemp - (Q / (Density * Cp * flow));
        // Calculate amount Temp loss per km.
        double Temp_Loss_Per_Km = (wellOutletTemp - Temp_Out) / Length;
        // For return temp loss, use the same Temp_Loss_Per_Km value.
        double Return_Temp_Loss_Per_Km = Temp_Loss_Per_Km;
        deepWellOutPut.setReturn_Temp_loss(Return_Temp_Loss_Per_Km);


        //step 8:------------------------------------------------------------------------
        // You can use Temp_Loss_Per_Km and Return_Temp_Loss_Per_Km as needed in your application.
        // Calculate total consumption
        double totalConsumption = boostPumpPower + (wellDataResult.getWellPumpPower() * noOfWells);
        // Calculate outlet overall annual electrical consumption
        double overallAnnualElectricalConsumption = totalConsumption * 8600 / 1000;
        deepWellOutPut.setOverall_Annual_Consumption(overallAnnualElectricalConsumption);
//deep well out put calculation end=========================================================================================================================================

//deep well Capex Calculation start=========================================================================================================================================
        try (InputStream inputStream = getClass().getResourceAsStream("/EstimatedCostDatabaseDeep.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Assuming the Excel columns are: Operation, Cost (GBP), Per Well?
                Cell operationCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell costCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell perWellCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String operation = operationCell.getStringCellValue();
                double cost = costCell.getCellType() == CellType.NUMERIC ? costCell.getNumericCellValue() : 0.0;
                String perWell = perWellCell.getStringCellValue().trim();

                // Set the values for these variables based on your data
                double well = noOfWells; // Assuming this value is available
                double wellDepth = wellDataResult.getWellDepthTVD(); // Replace with your actual value
                double requiredCapacity = calculation.getCapacity_req_MW(); // Replace with your actual value
                double network_Length = calculation.getNetwork_length(); // Replace with your actual value

                // Perform calculations based on conditions
                if ("Y".equalsIgnoreCase(perWell)) {
                    if ("Borehole drilling & construction".equalsIgnoreCase(operation) ||
                            "Well completion".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by well and well depth
                    } else if ("Mechanical circulation pump".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by required capacity
                    } else if ("Heat exchanger installation ".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by network length
                    } else if ("Heat connection".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by a constant factor
                    }
                }

                // Store the calculated cost in the appropriate field of the CostEstimationDeepWell object
                switch (operation) {
                    case "Site preparation & civil engineering":
                        costEstimationDeepWell.setSite_preparation_civil_engineering(cost);
                        break;
                    case "Drilling unit mob/demob":
                        costEstimationDeepWell.setDrilling_unit_mob_demob(cost);
                        break;
                    case "Borehole drilling & construction":
                        costEstimationDeepWell.setBorehole_drilling_construction(cost * wellDepth);
                        break;
                    case "Well completion":
                        costEstimationDeepWell.setWell_completion(cost * wellDepth);
                        break;
                    case "Mechanical circulation pump":
                        costEstimationDeepWell.setMechanical_circulation_pump(cost);
                        break;
                    case "Heat exchanger installation ":
                        costEstimationDeepWell.setHeat_exchanger_installation(cost);
                        break;
                    case "Heat connection":
                        costEstimationDeepWell.setHeat_connection(cost * network_Length * 1000 * 2);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

// Calculate the total CAPEX
        double totalCAPEX = costEstimationDeepWell.getSite_preparation_civil_engineering() +
                costEstimationDeepWell.getDrilling_unit_mob_demob() +
                costEstimationDeepWell.getBorehole_drilling_construction() +
                costEstimationDeepWell.getWell_completion() +
                costEstimationDeepWell.getMechanical_circulation_pump() +
                costEstimationDeepWell.getHeat_connection() +
                costEstimationDeepWell.getHeat_exchanger_installation();
        costEstimationDeepWell.setTotal(totalCAPEX);
//calculation of DeepWellCAPEX end================================================================================================================================================


//calculation of DeepWellOpex start=================================================================================================================================================
        try (InputStream inputStream = getClass().getResourceAsStream("/EstimatedCostDatabaseDeep.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(3); // Assuming the data is on the fourth sheet
            Iterator<Row> rowIterator = sheet.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // Assuming the Excel columns are: Operation, Cost (GBP), Per Well?
                Cell operationCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell costCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                Cell perWellCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                String operation = operationCell.getStringCellValue();
                double cost = costCell.getCellType() == CellType.NUMERIC ? costCell.getNumericCellValue() : 0.0;
                String perWell = perWellCell.getStringCellValue().trim();

                // Perform calculations based on conditions
                if ("Y".equalsIgnoreCase(perWell)) {
                    if ("Mechanical pump power consumption".equalsIgnoreCase(operation)) {
                        // Multiply cost by well pumping power and 8600, then by electrical cost
                        cost *= noOfWells;
                    } else if ("Boost pump power consumption".equalsIgnoreCase(operation)) {
                        // Multiply cost by 8600, then by electrical cost
                        cost *= noOfWells;
                    } else if ("Well maintenance".equalsIgnoreCase(operation)) {
                        // Multiply cost by no of wells from deep well output
                        cost *= noOfWells;
                    }
                }

                // Store the calculated cost in the appropriate field of the DeepWellOpex object
                switch (operation) {
                    case "Mechanical pump power consumption":
                        deepWellOpex.setMechanicalPumpPowerConsumption(cost * wellPumpPower * ELECTRICAL_cost * 8600);
                        break;
                    case "Boost pump power consumption":
                        deepWellOpex.setBoostPumpPowerConsumption(cost * BoostPumpPower * ELECTRICAL_cost * 8600);
                        break;
                    case "Well maintenance":
                        deepWellOpex.setWellMaintenance(cost);
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate the total Opex for deep well
        double totalOpex = deepWellOpex.getMechanicalPumpPowerConsumption() +
                deepWellOpex.getBoostPumpPowerConsumption() +
                deepWellOpex.getWellMaintenance();
        deepWellOpex.setTotal(totalOpex);
//calculation of DeepWellOpex end========================================================================================================


        //response of DeepWell along with deepWellOutputScreen, deepWellOPEX, deepWellCAPEX
        DoubleResponseDeep doubleResponseDeep = new DoubleResponseDeep();
        doubleResponseDeep.setCostEstimationDeepWell(costEstimationDeepWell);
        doubleResponseDeep.setDeepWellOutPutCalculation(deepWellOutPut);

        doubleResponseDeep.setDeepWellOpex(deepWellOpex);
        return doubleResponseDeep;

    }
//--------------------------------------------------------------------------------------------------------------------------------------

    public List<ExcelColumns> findClosestRowByGradient(double gradient) {
        double[] a = {30, 50}; // Replace with your gradient values
        double minDist = Double.MAX_VALUE;
        double closestValue = 0;
        List<ExcelColumns> newTable = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/demo.xlsx");
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            // Find the closest gradient value
            for (double value : a) {
                double dist = Math.abs(value - gradient);
                if (dist < minDist) {
                    minDist = dist;
                    closestValue = value;
                }
            }

            // Iterate through rows and filter by closest gradient
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Assuming gradient is in the first column (column index 0)
                if (cell != null && cell.getCellType() == CellType.NUMERIC && cell.getNumericCellValue() == closestValue) {
                    // Create a DatabaseEntry and add it to newTable
                    ExcelColumns column = new ExcelColumns();
                    column.setGeothermalGradient(row.getCell(0).getNumericCellValue());
                    column.setSteadyStateTemp(row.getCell(1).getNumericCellValue());
                    column.setKWt(row.getCell(2).getNumericCellValue());
                    column.setFlowRate(row.getCell(3).getNumericCellValue());
                    column.setPumpingPower(row.getCell(4).getNumericCellValue());
                    column.setDepth(row.getCell(5).getNumericCellValue());
                    column.setDelta(row.getCell(6).getNumericCellValue());
                    column.setPressureLoss(row.getCell(7).getNumericCellValue());
                    column.setBHT(row.getCell(8).getNumericCellValue());
                    column.setReturn(row.getCell(9).getNumericCellValue());
                    newTable.add(column);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newTable;
    }


    public WellDataResult processWellData(List<ExcelColumns> filteredTable, double wellDelta, double minWellOutletTemp, double Deepwellflowrate) {
        // Initialize variables to track the minimum steady state temp and its corresponding entry
        double minSteadyStateTemp = Double.MAX_VALUE;
        ExcelColumns minSteadyStateEntry = null;

        // Loop through the filtered table and apply conditions
        for (ExcelColumns entry : filteredTable) {
            // Remove rows where steady state temp is less than minWellOutletTemp
            if (entry.getSteadyStateTemp() >= minWellOutletTemp) {
                // Remove rows where flow rate is not equal to Deepwellflowrate
                if (entry.getFlowRate() == Deepwellflowrate) {
                    // Remove rows where delta T is not equal to well delta
                    if (entry.getDelta() == wellDelta) {
                        // Track the minimum steady state temp and its entry
                        if (entry.getSteadyStateTemp() < minSteadyStateTemp) {
                            minSteadyStateTemp = entry.getSteadyStateTemp();
                            minSteadyStateEntry = entry;
                        }
                    }
                }
            }
        }

        // Calculate pumping power and pressure loss for the minimum steady state temp entry
        double pumpingPower = minSteadyStateEntry.getPumpingPower();
        double pressureLoss = minSteadyStateEntry.getPressureLoss();
        double bht = minSteadyStateEntry.getBHT();
        double wellDepth = minSteadyStateEntry.getDepth();
        double kWt = minSteadyStateEntry.getKWt();
        double wellOutletTemp1 = minSteadyStateEntry.getSteadyStateTemp();

        // Create a WellDataResult object to store the results
        WellDataResult result = new WellDataResult();
        result.setWellOutletTemp(wellOutletTemp1);
        result.setWellPumpPower(pumpingPower);
        result.setWellPressureDrop(pressureLoss);
        result.setBottomHoleTemp(bht);
        result.setWellDepthTVD(wellDepth);
        result.setKwt(kWt);

        return result;
    }


    public DeepWellOutPutController.PressureResult calculateBoostPressureDrops(double deepWellFlowRate) {
        int[] array1 = new int[20];
        for (int i = 0; i < 20; i++) {
            array1[i] = i + 1;
        }
        double[] array2 = new double[20];
        double internalDiam = 0.0; // Initialize internalDiam
        for (int i = 0; i < array1.length; i++) {
            int x = array1[i];
            internalDiam = x * 0.025;
            double roughness = 0.02 / 100000;
            double kinematicViscosity = 1.004e-6;
            double density = 997; // DENSITY
            double k = roughness / internalDiam;

            double velocity = (deepWellFlowRate / 1e3) / (Math.PI * Math.pow(internalDiam / 2, 2));
            double r = velocity * internalDiam / kinematicViscosity;

            double x1 = k * r * (Math.log(10) / 18.574);
            double x2 = Math.log(r * (Math.log(10) / 5.02));
            double f = x2 - 0.2;

            double e = (Math.log(x1 + f) - 0.2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            e = (Math.log(x1 + f) + f - x2) / (1 + x1 + f);
            f = f - (1 + x1 + f + 0.5 * e) * e * (x1 + f) / (1 + x1 + f + e * (1 + e / 3));
            f = 0.5 * Math.log(10) / f;
            f = f * f;
            double darcyFF = f;

            double pressure = (darcyFF * (1000 / internalDiam) * (Math.pow(velocity, 2) * density / 2)) / (1e5);

            array2[i] = pressure;
        }

        int index = -1;
        for (int i = 0; i < array2.length - 1; i++) {
            if (Math.abs(array2[i] - array2[i + 1]) < 10) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            index = array2.length - 1;
        }

        double deliveryPressureLoss = array2[index];

        // Return the internal diameter and pressure as a pair
        return
                new DeepWellOutPutController.PressureResult(internalDiam, deliveryPressureLoss);
    }


    class PressureResult {
        private double pressure;
        private double internalDiam;

        public PressureResult(double internalDiam, double pressure) {
            this.internalDiam = internalDiam;
            this.pressure = pressure;
        }

        public double getInternalDiam() {
            return internalDiam;
        }

        public double getPressure() {
            return pressure;
        }


    }

    public double calculateTempLossFromHEXAndDeliveryPipe(double ambientTemp, double internalDiameter, double flow, double tempIn) {
        double Thermal_Conductivity_pipe = 0.0244;
        int Density = 997;
        double Cp = 4.184;
        int Pipe_thickness = 2;
        int Length = 1000;

        double internalDiameterM = internalDiameter * 0.0254;
        double thickness = Pipe_thickness * 0.0254;
        double externalDiameter = internalDiameterM + 2 * thickness;
        double radiusExternal = externalDiameter / 2;
        double radiusInternal = internalDiameterM / 2;

        double thermalResistance = Math.log(radiusExternal / radiusInternal) / Thermal_Conductivity_pipe;

        // Convert length from km to m

        double q = (2 * Math.PI * Length * 1000 * (tempIn - ambientTemp)) / (thermalResistance);

        // kJ to J and 1 to m^3 conversion cancel.
        double tempOut = tempIn - (q / (Density * Cp * flow));

        // Calculate the temperature loss per km
        double tempLossPerKm = (tempIn - tempOut) / Length;

        return tempLossPerKm;

    }


}