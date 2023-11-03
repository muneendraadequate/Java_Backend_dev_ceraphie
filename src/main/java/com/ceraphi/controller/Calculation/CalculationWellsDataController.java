package com.ceraphi.controller.Calculation;

import com.ceraphi.dto.InputsDto;
import com.ceraphi.dto.SummaryDto;
import com.ceraphi.dto.Calculation;
import com.ceraphi.dto.DeepWellOutPut;
import com.ceraphi.dto.OutPutCalculationHeatPump;
import com.ceraphi.services.GeneralInfoServices;
import com.ceraphi.services.WellDataService;
import com.ceraphi.utils.*;
import com.ceraphi.utils.EmissionCalculator.EmissionCalculator;
import com.ceraphi.utils.summary.SummaryDeepWell;
import com.ceraphi.utils.summary.SummaryHeatPump;
import com.ceraphi.utils.summary.SummaryResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//java -jar "C:\Staging\Ceraphiapi.adequateshop.com\Code\demo.jar"



@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CalculationWellsDataController {
    public static double discount_rate=3.5;
    public static double selling_price=0.11;
    public static int deepNoOfWells;
    public static double production_Value = 8600;
    public static double electrical_Price_Inflation=0.03;
    public static DeepWellOutPut deepWellOutPut = new DeepWellOutPut();

    //Constant

    public static SummaryResponse summaryResponse = new SummaryResponse();
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
    public static Double electricalCost = 0.2;
    public static Double WELL_DELTA = 20.0;
    public static Double boostPumpEfficiency;
   public static Double DeepWellFlowRate = 15.0;
   public static Double processDelta = 20.0;
   public static Double connectionPipeThermalConductivity=0.0244;
   public static Double connectionPipeThickness=2.0;
   @Autowired
    private GeneralInfoServices generalInfoServices;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculation(@RequestBody Calculation calculation) throws IOException {
        //HeatPump calculation Start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        if (calculation.getProcessDelta() != null) {
            processDelta = calculation.getProcessDelta();
        }
        if (calculation.getPumpEfficiency() != null) {
            boostPumpEfficiency = calculation.getPumpEfficiency();
        }
        if (calculation.getElectricalPrice() != null) {
            electricalCost = calculation.getElectricalPrice();
        } if (calculation.getDiscountRate() != null) {
            discount_rate = calculation.getDiscountRate();
        }if (calculation.getSelling_price_per_kWh() != null) {
            selling_price = calculation.getSelling_price_per_kWh();
        }








        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        double condenser = calculation.getCapacity_req_MW();
        double requiredTemp = calculation.getProcess_required_temp();
        double req_temp = calculation.getProcess_required_temp();// Specify the required temperature here
        WellData interpolatedWellData = new WellData();
        interpolatedWellData = loader.getInterpolatedWellData("/GELData1500mwells.xlsx", req_temp);
            if(interpolatedWellData.isError()){
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
        double FLOW = interpolatedWellData.getFlowRate();
        outputCalculation.setCondenser(Double.parseDouble(decimalFormat.format(condenser)));
        outputCalculation.setDeliverable_Temp(Double.parseDouble(decimalFormat.format(requiredTemp)));
        outputCalculation.setWell_Depth(DEPTH);
        outputCalculation.setProcess_Return_Temp(Double.parseDouble(decimalFormat.format(requiredTemp -processDelta)));
        outputCalculation.setIn(Double.parseDouble(decimalFormat.format(interpolatedWellData.getWellOutletTemp())));
        outputCalculation.setOut(Double.parseDouble(decimalFormat.format(interpolatedWellData.getWellOutletTemp()-5)));
        outputCalculation.setSCOP(Double.parseDouble(decimalFormat.format(interpolatedWellData.getCop())));
        outputCalculation.setWell_Flow_rate(Double.parseDouble(decimalFormat.format(interpolatedWellData.getFlowRate())));
        outputCalculation.setWell_Outlet_Temp(Double.parseDouble(decimalFormat.format(interpolatedWellData.getWellOutletTemp())));
        outputCalculation.setWell_inlet_temp(Double.parseDouble(decimalFormat.format(interpolatedWellData.getWellOutletTemp() - 5)));
        double Input = condenser / interpolatedWellData.getCop();
        double roundedValue = Double.parseDouble(decimalFormat.format(Input));
        double workInput = roundedValue;
        double evaporator = condenser - workInput;
        outputCalculation.setEvaporator(Double.parseDouble(decimalFormat.format(evaporator)));
        double wells = Math.round((evaporator / (interpolatedWellData.getCapacity() / 1000)));
        outputCalculation.setNo_of_wells(wells);
        double pressure = wellDataService.calculatePressureDrop(FLOW, DEPTH, INTERNAL_DIAM_TUBULAR, INTERNAL_DIAM_ANNULUS, INTERNAL_DIAM_COUPLING);
        double wellPressureDrop =pressure / 100;
        outputCalculation.setWell_pressure_drop(Double.parseDouble(decimalFormat.format(wellPressureDrop)));
        double pumpEfficiency = 0.75;
        double power = (FLOW) * (wellPressureDrop * 0.1);
        power = power / pumpEfficiency;
        double pumpingPower = Double.parseDouble(decimalFormat.format(power));
        double wellPumpingPower = pumpingPower;
        outputCalculation.setWell_pump_power(Double.parseDouble(decimalFormat.format(wellPumpingPower)));
        double flow = interpolatedWellData.getFlowRate() * wells;
        double v1 = wellDataService.calculateBoostPumpPower(flow
                , calculation.getNetwork_length(), wells);
        double delivery$PressureLoss = v1 * calculation.getNetwork_length();
        outputCalculation.setDelivery_pressure_loss(Double.parseDouble(decimalFormat.format(delivery$PressureLoss)));
        outputCalculation.setReturn_Pressure_loss(Double.parseDouble(decimalFormat.format(delivery$PressureLoss)));
        double boostPumpPower = (delivery$PressureLoss + delivery$PressureLoss) * 0.1 * interpolatedWellData.getFlowRate() * wells * calculation.getNetwork_length();
        double Boost_PumpPower = boostPumpPower / (boostPumpEfficiency/100);
        double BoostPumpPower = Double.parseDouble(decimalFormat.format(Boost_PumpPower));
        outputCalculation.setBoost_pump_power(Double.parseDouble(decimalFormat.format(BoostPumpPower)));
        double annualConsumption = ((BoostPumpPower + wellPumpingPower * wells) / 1000.0 + workInput) * calculation.getMin_operational_hours();
        outputCalculation.setOverall_Annual_Consumption(Double.parseDouble(decimalFormat.format(annualConsumption)));
        outputCalculation.setDelivery_temp_loss(0.0);
        outputCalculation.setReturn_Temp_loss(0.0);
        outputCalculation.setBottom_Hole_Temp(Double.parseDouble(decimalFormat.format(calculation.getThermal_gradient() * 1.5)));
       outputCalculation.setAnnual_thermal_productions(calculation.getCapacity_req_MW()*calculation.getMin_operational_hours());

        //+++++++++++++++++++++++++++++++++++++++HeatPump calculation for output screen End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        //+++++++++++++++++++++++++++++++++++++++HeatPump calculation for CAPEX start+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
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

                    if ("Borehole drilling & construction".equalsIgnoreCase(operation)){
                        cost *= well;
                    }
                   else if("Borehole completion".equalsIgnoreCase(operation)) {
                        cost *= well;
                    }
                   else if("Site preparation & civil engineering".equalsIgnoreCase(operation)) {
                        cost *= well;
                    } else if ("Plate heat exchanger".equalsIgnoreCase(operation)) {
                        cost *= well;
                    } else if ("Drilling unit mob/demob".equalsIgnoreCase(operation)) {
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
                        costEstimationHeatPump.setSite_preparation_civil_engineering(formatNumber(cost));
                        break;
                    case "Drilling unit mob/demob":
                        costEstimationHeatPump.setDrilling_unit_mob_demob(formatNumber(cost));
                        break;
                    case "Borehole drilling & construction":
                        costEstimationHeatPump.setBorehole_drilling_construction(formatNumber(cost * wellDepth));
                        break;
                    case "Borehole completion":
                        costEstimationHeatPump.setBorehole_completion(formatNumber(cost * wellDepth));
                        break;
                    case "Heat pump & heat exchanger installation":
                        double heatpump = cost;
                        costEstimationHeatPump.setHeat_pump_heat_exchanger_installation(formatNumber(cost * requiredCapacity * 1000));
                        break;
                    case "Heat connection":
                        costEstimationHeatPump.setHeat_connection(formatNumber(cost * networkLength * 1000 * 2));
                        break;
                    case "Plate heat exchanger":
                        costEstimationHeatPump.setPlate_heat_exchanger(formatNumber(cost));
                        break;
                    default:
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Calculate the total CAPEXHeatPump
        double sitePreparationCivilEngineering = parseStringToDouble(costEstimationHeatPump.getSite_preparation_civil_engineering());
        double drillingUnitMobDemob = parseStringToDouble(costEstimationHeatPump.getDrilling_unit_mob_demob());
        double boreholeDrillingConstruction = parseStringToDouble(costEstimationHeatPump.getBorehole_drilling_construction());
        double boreHoleCompletion = parseStringToDouble(costEstimationHeatPump.getBorehole_completion());
        double heatPumpHeatExchangerInstallation = parseStringToDouble(costEstimationHeatPump.getHeat_pump_heat_exchanger_installation());
        double heatConnection = parseStringToDouble(costEstimationHeatPump.getHeat_connection());
        double plateHeatExchanger = parseStringToDouble(costEstimationHeatPump.getPlate_heat_exchanger());
        double totalCapexMedium =sitePreparationCivilEngineering+drillingUnitMobDemob+boreholeDrillingConstruction+boreHoleCompletion+heatPumpHeatExchangerInstallation+heatConnection+plateHeatExchanger;
        costEstimationHeatPump.setTotal(totalCapexMedium);
        doubleResponseClass.setOutPutCalculationHeatPump(outputCalculation);
        doubleResponseClass.setCostEstimationHeatPump(costEstimationHeatPump);
        //++++++++++++++++++++++++++++++++++++++++HeatPump calculation for   CAPEX   End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        //++++++++++++++++++++++++++++++++++++++++start  Of OPEX  HeatPump++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++==========================================================================================
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
                        heatPumpOpex.setHeat_pump_electrical_consumption(formatNumber(cost * workInput * 1000 * calculation.getMin_operational_hours() * electricalCost));
                        break;
                    case "Pumping power consumption":
                        double pumpingPowerConsumption = cost; // As it has 'Y' in per well column
                        double pumpingPowerConsumptionValue  = (pumpingPowerConsumption * wellPumpingPower + BoostPumpPower) * calculation.getMin_operational_hours() * electricalCost;
                        // Calculate the cost of pumping power consumption
                        heatPumpOpex.setPumping_power_consumption(formatNumber(pumpingPowerConsumptionValue));
                        break;
                    case "Well Maintenance ":
                        heatPumpOpex.setWellMaintenance(formatNumber(cost));
                        break;
                    default:
                        break;
                }
            }
            heatPumpOpex.setWellMaintenance(heatPumpOpex.getWellMaintenance());
            heatPumpOpex.setHeat_pump_electrical_consumption(heatPumpOpex.getHeat_pump_electrical_consumption());
            heatPumpOpex.setPumping_power_consumption(heatPumpOpex.getPumping_power_consumption());
            doubleResponseClass.setHeatPumpOpex(heatPumpOpex);
            double pumpPowerConsumption = parseStringToDouble(heatPumpOpex.getPumping_power_consumption());
            double wellMaintenance = parseStringToDouble(heatPumpOpex.getWellMaintenance());
            double heatPumpElectricalConsumption = parseStringToDouble(heatPumpOpex.getHeat_pump_electrical_consumption());
            double heatPumpTotalOpex = pumpPowerConsumption + wellMaintenance + heatPumpElectricalConsumption;
            heatPumpOpex.setTotalOpex(heatPumpTotalOpex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //++++++++++++++++++++++++++++++++++++++++END OF OPEX HEAT PUMP   ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=========================================================



//----------------------------------------------------------------------------------------------------------
        //deepWell method calling
        // DeepWellOutPutController deepWellOutPutController = new DeepWellOutPutController();
        DoubleResponseDeep doubleResponseDeep = deepWellOutPuts(calculation);
        doubleResponseClass.setDeepWellOutPutCalculation(doubleResponseDeep.getDeepWellOutPutCalculation());
        doubleResponseClass.setCostEstimationDeepWell(doubleResponseDeep.getCostEstimationDeepWell());
        doubleResponseClass.setDeepWellOpex(doubleResponseDeep.getDeepWellOpex());


        double DeepWellAnnualConsumption = doubleResponseDeep.getDeepWellOutPutCalculation().getOverall_Annual_Consumption();
        EmissionCalculator emissionCalculator = new EmissionCalculator();
        EmissionData emissionData = emissionCalculator.calculateEmissions(calculation.getCapacity_req_MW(), calculation.getMin_operational_hours(), annualConsumption, DeepWellAnnualConsumption);
        doubleResponseClass.setEmissionData(emissionData);
        //lcoh of DeepWell
//        SensitivityAnalysisDeepWell sensitiveAnalysisDeepWell = new SensitivityAnalysisDeepWell();
//        CostEstimationDeepWell costEstimationDeepWell1 = doubleResponseDeep.getCostEstimationDeepWell();
//        double DeepWelltotalCAPEX = costEstimationDeepWell1.getTotal();
//        DeepWellOpex deepWellOpex1 = doubleResponseDeep.getDeepWellOpex();
//        double DeepWelltotalOPEX = deepWellOpex1.getTotal();
//        List<LCOHYearResponse> lcohYearResponses = sensitiveAnalysisDeepWell.lcohResponseDeepWell(DeepWelltotalCAPEX,DeepWelltotalOPEX,discount_rate,selling_price);
//        doubleResponseClass.setLcohYearResponseDeepWell(lcohYearResponses);
        //lcoh of HeatPump
//        SensitivityAnalysisHeatPump sensitiveAnalysisHeatPump = new SensitivityAnalysisHeatPump();
//        List<LCOHYearResponse> lcohYearResponses1 = sensitiveAnalysisHeatPump.lcohResponseHeatPump(costEstimationHeatPump.getTotal(), heatPumpOpex.getTotalOpex(),discount_rate,selling_price);
//        doubleResponseClass.setLcohYearResponseHeatPumpWell(lcohYearResponses1);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(doubleResponseClass)
                .build();
        return ResponseEntity.ok(apiResponseData);


    }
//+++++++++++++++++++++++++++++++++++END OF HEAT PUMP CALCULATION++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //==============================================DeepWellOutPutCalculation Data starting =========================================================================================================================
    public DoubleResponseDeep deepWellOutPuts(Calculation calculation) {
        if (calculation.getDeepWellFlowRate() != null) {
            DeepWellFlowRate = calculation.getDeepWellFlowRate();
        }if(calculation.getDeepDelta()!=null){
            WELL_DELTA = calculation.getDeepDelta();
        }if(calculation.getConnectionPipeThickness()!=null){
            connectionPipeThickness = calculation.getConnectionPipeThickness();
        }if (calculation.getPumpEfficiency() != null) {
            boostPumpEfficiency = calculation.getPumpEfficiency();
        }if (calculation.getDiscountRate() != null) {
            discount_rate = calculation.getDiscountRate();
        }if (calculation.getSelling_price_per_kWh() != null) {
            selling_price = calculation.getSelling_price_per_kWh();
        }




        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        // step 1:---------------------------------------------------------------------
        Double deliverableTemp = calculation.getProcess_required_temp();
        Double networkLength = calculation.getNetwork_length();
        deepWellOutPut.setDeliverable_Temp(Double.parseDouble(decimalFormat.format(deliverableTemp)));
        deepWellOutPut.setProcess_Return_Temp(Double.parseDouble(decimalFormat.format(deliverableTemp - 20)));


        //step 2:----------------------------------------------------------------
        double minWellOutLetTemp = deliverableTemp * 1.2;
//        int DeepWellFlowRate = 15;
        deepWellOutPut.setWell_Flow_rate(Double.parseDouble(decimalFormat.format(DeepWellFlowRate)));


        //step 3:---------------------------------------------------------------
        List<ExcelColumns> closestRowByGradient = findClosestRowByGradient(calculation.getThermal_gradient());


        //step 4:---------------------------------------------------------------
        WellDataResult wellDataResult = processWellData(closestRowByGradient, WELL_DELTA, minWellOutLetTemp, DeepWellFlowRate);
        deepWellOutPut.setWell_pump_power(Double.parseDouble(decimalFormat.format(wellDataResult.getWellPumpPower())));
        double wellPumpPower = wellDataResult.getWellPumpPower();
        deepWellOutPut.setWell_pressure_drop(Double.parseDouble(decimalFormat.format(wellDataResult.getWellPressureDrop())));
        deepWellOutPut.setBottom_Hole_Temp(Double.parseDouble(decimalFormat.format(wellDataResult.getBottomHoleTemp())));
        deepWellOutPut.setWell_Depth(Double.parseDouble(decimalFormat.format(wellDataResult.getWellDepthTVD())));
        double well_Depth = wellDataResult.getWellDepthTVD();
        deepWellOutPut.setWell_Outlet_Temp(Double.parseDouble(decimalFormat.format(wellDataResult.getWellOutletTemp())));
        double wellOutletTemp = wellDataResult.getWellOutletTemp();
        deepWellOutPut.setWell_inlet_temp(Double.parseDouble(decimalFormat.format(minWellOutLetTemp - WELL_DELTA)));
        double wells = calculation.getCapacity_req_MW() / (wellDataResult.getKwt() / 1000);
        int noOfWells = (int) Math.ceil(wells);
        deepNoOfWells=noOfWells;

        deepWellOutPut.setNo_of_wells(noOfWells);
        deepWellOutPut.setAnnual_thermal_productions(wellDataResult.getKwt()*8600/1000);


        //step 5:----------------------------------------------------------------
        double flow = DeepWellFlowRate * noOfWells;
        PressureData pressureData = wellDataService.calculateBoostPumpPowerForDeepWell(flow, noOfWells, calculation.getNetwork_length());

        deepWellOutPut.setDelivery_pressure_loss(Double.parseDouble(decimalFormat.format(pressureData.getPressure())));
        deepWellOutPut.setReturn_Pressure_loss(Double.parseDouble(decimalFormat.format(pressureData.getPressure())));
        // Calculate boost pump power without efficiency
        double boostPumpPower = (pressureData.getPressure() + pressureData.getPressure()) * 0.1 * DeepWellFlowRate * noOfWells * networkLength;
        // Adjust boost pump power for efficiency
        double PumpEfficiency = boostPumpEfficiency/100;
        double BoostPumpPower = boostPumpPower / PumpEfficiency;
        deepWellOutPut.setBoost_pump_power(Double.parseDouble(decimalFormat.format(BoostPumpPower)));


        //step 6:---------------------------------------------------------------
        double ID = pressureData.getInternalDiameter();
        double ambientTemp = calculation.getAmbient_temperature();
        double tempLossPerKM = calculateTempLossFromHEXAndDeliveryPipe(ambientTemp, ID, flow, wellOutletTemp);
        deepWellOutPut.setDelivery_temp_loss(Double.parseDouble(decimalFormat.format(tempLossPerKM)));
        // Calculate IN (initial temperature at the well outlet)
        double IN = wellOutletTemp - (tempLossPerKM * networkLength);
        // Calculate Out using IN, requiredCapacity, wellFlowRate, noOfWells
        double Out = IN - (Math.round(calculation.getCapacity_req_MW() * 1000) / (DeepWellFlowRate * noOfWells * 4.184));
        deepWellOutPut.setIn(Double.parseDouble(decimalFormat.format(IN)));
        deepWellOutPut.setOut(Double.parseDouble(decimalFormat.format(Out)));


        //step 7:---------------------------------------------------------------
        // Initialize constants
        double Thermal_Conductivity_pipe = connectionPipeThermalConductivity;
        int Density = 997;
        double Cp = 4.184;
        double Pipe_Thickness = connectionPipeThickness;
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
        deepWellOutPut.setReturn_Temp_loss(Double.parseDouble(decimalFormat.format(Return_Temp_Loss_Per_Km)));


        //step 8:------------------------------------------------------------------------
        // You can use Temp_Loss_Per_Km and Return_Temp_Loss_Per_Km as needed in your application.
        // Calculate total consumption
        double totalConsumption = BoostPumpPower + (wellDataResult.getWellPumpPower() * noOfWells);
        // Calculate outlet overall annual electrical consumption
        double overallAnnualElectricalConsumption = totalConsumption * 8600 / 1000;
        deepWellOutPut.setOverall_Annual_Consumption(Double.parseDouble(decimalFormat.format(overallAnnualElectricalConsumption)));
//====================================================deep well out put calculation end=========================================================================================================================================

//====================================================deep well CAPEX Calculation start=========================================================================================================================================
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
                    if ("Borehole drilling & construction".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by well and well depth
                    } else if ("Mechanical circulation pump".equalsIgnoreCase(operation)) {
                        cost *= well; // Multiply by required capacity
                    } else if ("Well completion".equalsIgnoreCase(operation)) {
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
                        costEstimationDeepWell.setSite_preparation_civil_engineering(formatNumber(cost));
                        break;
                    case "Drilling unit mob/demob":
                        costEstimationDeepWell.setDrilling_unit_mob_demob(formatNumber(cost));
                        break;
                    case "Borehole drilling & construction":
                        costEstimationDeepWell.setBorehole_drilling_construction(formatNumber(cost*wellDepth));
                        break;
                    case "Well completion":
                        costEstimationDeepWell.setWell_completion(formatNumber(cost * wellDepth));
                        break;
                    case "Mechanical circulation pump":
                        costEstimationDeepWell.setMechanical_circulation_pump(formatNumber(cost));
                        break;
                    case "Heat exchanger installation ":
                        costEstimationDeepWell.setHeat_exchanger_installation(formatNumber(cost));
                        break;
                    case "Heat connection":
                        costEstimationDeepWell.setHeat_connection(formatNumber(cost * network_Length * 1000 * 2));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sitePreparation = costEstimationDeepWell.getSite_preparation_civil_engineering();
        String drillingUnitMobDemob = costEstimationDeepWell.getDrilling_unit_mob_demob();
        String boreholeDrillingConstruction = costEstimationDeepWell.getBorehole_drilling_construction();
        String wellCompletion = costEstimationDeepWell.getWell_completion();
        String mechanicalCirculationPump = costEstimationDeepWell.getMechanical_circulation_pump();
        String heatConnection = costEstimationDeepWell.getHeat_connection();
        String heatExchangerInstallation = costEstimationDeepWell.getHeat_exchanger_installation();
// Remove commas and parse as doubles
        double sitePreparationValue = parseStringToDouble(sitePreparation);
        double drillingUnitMobDemobValue = parseStringToDouble(drillingUnitMobDemob);
        double boreholeDrillingConstructionValue = parseStringToDouble(boreholeDrillingConstruction);
        double wellCompletionValue = parseStringToDouble(wellCompletion);
        double mechanicalCirculationPumpValue = parseStringToDouble(mechanicalCirculationPump);
        double heatConnectionValue = parseStringToDouble(heatConnection);
        double heatExchangerInstallationValue = parseStringToDouble(heatExchangerInstallation);
// Add the double values together
        int totalValue = (int) (sitePreparationValue + drillingUnitMobDemobValue + boreholeDrillingConstructionValue
                        + wellCompletionValue + mechanicalCirculationPumpValue + heatConnectionValue + heatExchangerInstallationValue);

        costEstimationDeepWell.setTotal(totalValue);

//============================================calculation of DeepWell CAPEX end================================================================================================================================================


//============================================calculation of DeepWell OPEX start=================================================================================================================================================
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
                        deepWellOpex.setMechanicalPumpPowerConsumption(formatNumber(Double.parseDouble(decimalFormat.format(cost * wellPumpPower * ELECTRICAL_cost * 8600))));
                        break;
                    case "Boost pump power consumption":
                        deepWellOpex.setBoostPumpPowerConsumption(formatNumber(Double.parseDouble(decimalFormat.format(cost * BoostPumpPower * ELECTRICAL_cost * 8600))));
                        break;
                    case "Well maintenance":
                        deepWellOpex.setWellMaintenance(formatNumber(Double.parseDouble(decimalFormat.format(cost))));
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate the total Opex for deep well
        double mechanicalPumpPowerConsumption = parseStringToDouble(deepWellOpex.getMechanicalPumpPowerConsumption());
        double boostPumpPowerConsumption = parseStringToDouble(deepWellOpex.getBoostPumpPowerConsumption());
        double wellMaintenance = parseStringToDouble(deepWellOpex.getWellMaintenance());
        double totalOpex = mechanicalPumpPowerConsumption + boostPumpPowerConsumption + wellMaintenance;
        deepWellOpex.setTotal(totalOpex);
//====================================================calculation of DeepWellOpex end========================================================================================================
        //response of DeepWell along with deepWellOutputScreen, deepWellOPEX, deepWellCAPEX
        DoubleResponseDeep doubleResponseDeep = new DoubleResponseDeep();
        doubleResponseDeep.setCostEstimationDeepWell(costEstimationDeepWell);
        doubleResponseDeep.setDeepWellOutPutCalculation(deepWellOutPut);
        doubleResponseDeep.setDeepWellOpex(deepWellOpex);
        return doubleResponseDeep;

    }
    //========================================END OF CALCULATION OF DEEP WELL======================================================================================================================================
//--------------------------------------------------------------------------------------------------------------------------------------
//////////////////////////////////////////////  METHODS   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public List<ExcelColumns> findClosestRowByGradient(double gradient) {
        double[] a = {30, 50}; // Replace with your gradient values
        double minDist = Double.MAX_VALUE;
        double closestValue = 0;
        List<ExcelColumns> newTable = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream("/proDataBase.xlsx");
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


    public WellDataResult processWellData(List<ExcelColumns> filteredTable, double wellDelta, double minWellOutletTemp,Double Deepwellflowrate) {
        // Initialize variables to track the minimum steady state temp and its corresponding entry
        double minSteadyStateTemp = Double.MAX_VALUE;
        ExcelColumns minSteadyStateEntry = null;

        // Loop through the filtered table and apply conditions
        for (ExcelColumns entry : filteredTable) {
            // Remove rows where steady state temp is less than minWellOutletTemp
            if (entry.getSteadyStateTemp() > minWellOutletTemp) {
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




    public double calculateTempLossFromHEXAndDeliveryPipe(double ambientTemp, double internalDiameter, double flow, double tempIn) {
        double Thermal_Conductivity_pipe = 0.0244;
        int Density = 997;
        double Cp = 4.184;
        Double Pipe_thickness = connectionPipeThickness;
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


    public static String formatNumber(double number) {
        String pattern = "###,###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);
    }
    private static double parseStringToDouble(String stringValue) {
        // Remove commas and parse the string as double
        stringValue = stringValue.replace(",", "");
        return Double.parseDouble(stringValue);
    }

//==========================================================================================================================



    @PostMapping("/newWellSummary")
    public ResponseEntity<?> lcohOfNewWell( @RequestBody SummaryDto summaryDto) {
            // Step 2 - Initialize arrays
//        int[] years = new int[41];
//        for (int i = 0; i <= 40; i++) {
//            years[i] = i;
//        }
//
//        int rows = years.length;
        if (summaryDto.getDiscountRate() != null) {
            discount_rate = summaryDto.getDiscountRate()/100;
        }if (summaryDto.getSellingPrice() != null) {
            selling_price = summaryDto.getSellingPrice()*1000;
        }
        int year = summaryDto.getYear();
        int[] years = new int[year];
        for (int i = 0; i <= years.length-1; i++) {
            years[i] = i;
        }
        int rows = years.length;
        // Initialize arrays
        Double[] capex = new Double[rows];
        Double[] opex = new Double[rows];
        Double[] production = new Double[rows];
        Double[] discountedFactor = new Double[rows];
        Double[] netCashFlow = new Double[rows];
        Double[] revenue = new Double[rows];
        Double[] cumulativeCashFlow = new Double[rows];
        Double[] discountedCost = new Double[rows];
        Double[] discountedProduction = new Double[rows];
        Double[] priceInflationFactor = new Double[rows];
        Double[] discountedCashFlow = new Double[rows];
        Double[] totalCostArray = new Double[rows]; // Array to store total cost

        // Set constant values
        Double mediumWellCapex = summaryDto.getMediumWellCapex();
        Double mediumWellOpex = summaryDto.getMediumWellOpex();
        Double productionValue = production_Value;



        Double price = selling_price;
        Double electricalPriceInflation = electrical_Price_Inflation; // 3% inflation

        // Initialize the price inflation factor
        priceInflationFactor[0] = 1.0; // Initial value is 1.00

        // Calculate price inflation factor for the remaining years
        for (int i = 1; i < rows; i++) {
            priceInflationFactor[i] = priceInflationFactor[i - 1] * (1 + electricalPriceInflation);
        }

        // Populate capex, opex, and production arrays with constant values
        Double totalCost = 0.0;
        for (int i = 0; i < rows; i++) {
            capex[i] = (i == 0) ? mediumWellCapex : 0.0;
            opex[i] = (i != 0) ? mediumWellOpex : 0.0;
            production[i] = (i == 0) ? 0.0 : productionValue;
            Double cost = capex[i] + opex[i];
            totalCost = cost * priceInflationFactor[i]; // Calculate and store total cost
            // Assign capex[i] and opex[i] to costValues array
            totalCostArray[i] = totalCost;
            revenue[i] = production[i] * price * priceInflationFactor[i];
            Double cashFlow = revenue[i] - totalCost;
            // Assign cashFlow value to the netCashFlow array or use it as needed.
            netCashFlow[i] = cashFlow;
        }

        // Calculate Discounted Factor, Discounted Cash Flow, Cumulative Cash Flow,
        // Discounted Cost, and Discounted Production
        Double cumulativeCashFlowValue = 0.0;
        int positiveCashFlowYear = -1; // Initialize to -1 to indicate no positive year found
        for (int i = 0; i < rows; i++) {
            discountedFactor[i] = 1.0 / Math.pow(1.0 + discount_rate, years[i]);
            discountedCashFlow[i] = discountedFactor[i] * netCashFlow[i];
            if (i == 0) {
                cumulativeCashFlow[i] = discountedCashFlow[i];
            } else {
                cumulativeCashFlowValue = discountedCashFlow[i] + cumulativeCashFlow[i - 1];
                cumulativeCashFlow[i] = cumulativeCashFlowValue;
            }
            if (cumulativeCashFlow[i] > 0 && positiveCashFlowYear == -1) {
                positiveCashFlowYear = years[i];
            }


            discountedCost[i] = (capex[i] + opex[i]) * discountedFactor[i];
            discountedProduction[i] = production[i] * discountedFactor[i];
        }
        if (positiveCashFlowYear != -1) {
        }
                int x = summaryDto.getYear()-1; // Use the user-defined 'years'
                // Calculate LCOH
                double sumDiscountedCost = sum(discountedCost, 0, x );
                double sumDiscountedProduction = sum(discountedProduction, 0, x );
            double LCOHValue = sumDiscountedCost / sumDiscountedProduction;
        long roundedNumber = Math.round(LCOHValue);
        int LCOH = (int) roundedNumber;

//                double LCOH;
//                if (sumDiscountedProduction != 0) {
//                    LCOH = sumDiscountedCost / sumDiscountedProduction;
//                } else {
//                    LCOH = 0; // or any other appropriate value
//                }
                // Calculate NPV
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
                double NPV = cumulativeCashFlow[x];

                // Calculate IRR
                Double[] cashflowSubset = Arrays.copyOfRange(netCashFlow, 0, x);
                double irrValue = calculateIRR(cashflowSubset, years);
                double IRR = (irrValue != 0.0) ? irrValue : 0.0;

                // Calculate P/I
                double PIValue = cumulativeCashFlow[x] / summaryDto.getMediumWellCapex() + 1.0;
        long PIRound = Math.round(PIValue);
        int PI = (int) PIRound;
                // Create and add a response object
                SummaryHeatPump response = new SummaryHeatPump(
                        BigDecimal.valueOf(summaryDto.getYear()),
                        BigDecimal.valueOf(LCOH),
                        BigDecimal.valueOf(Double.parseDouble(decimalFormat.format(NPV))),
                        BigDecimal.valueOf(IRR),
                        BigDecimal.valueOf(PI), positiveCashFlowYear
                );

                summaryResponse.setSummaryHeatPump(response);
            SummaryDeepWell summaryDeepWell = lcohResponseDeepWell(summaryDto);

            summaryResponse.setSummaryDeepWell(summaryDeepWell);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .data(summaryResponse)
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }


    public static SummaryDeepWell lcohResponseDeepWell(SummaryDto summaryDto) {
//        // Your code for Deep Well calculations with double values
//        int[] years = new int[41];
//        for (int i = 0; i <= 40; i++) {
//            years[i] = i;
//        }
        if (summaryDto.getDiscountRate() != null) {
            discount_rate = summaryDto.getDiscountRate()/100;
        }if (summaryDto.getSellingPrice() != null) {
            selling_price = summaryDto.getSellingPrice()*1000;
        }
        int year = summaryDto.getYear();
        int[] years = new int[year];
        for (int i = 0; i < year; i++) {
            years[i] = i;
        }
        int rows = years.length;

        // Initialize arrays
        Double[] capex = new Double[rows];
        Double[] opex = new Double[rows];
        Double[] production = new Double[rows];
        Double[] discountedFactor = new Double[rows];
        Double[] netCashFlow = new Double[rows];
        Double[] revenue = new Double[rows];
        Double[] cumulativeCashFlow = new Double[rows];
        Double[] discountedCost = new Double[rows];
        Double[] discountedProduction = new Double[rows];
        Double[] priceInflationFactor = new Double[rows];
        Double[] discountedCashFlow = new Double[rows];
        Double[] totalCostArray = new Double[rows]; // Array to store total cost

        // Set constant values
        Double mediumWellCapex =summaryDto.getDeepWellCapex();
        Double mediumWellOpex = summaryDto.getDeepWellOpex();
        Double productionValue = production_Value;
        Double price = selling_price;
        Double electricalPriceInflation = electrical_Price_Inflation; // 3% inflation

        // Initialize the price inflation factor
        priceInflationFactor[0] = 1.0; // Initial value is 1.00

        // Calculate price inflation factor for the remaining years
        for (int i = 1; i < rows; i++) {
            priceInflationFactor[i] = priceInflationFactor[i - 1] * (1.0 + electricalPriceInflation);
        }

        // Populate capex, opex, and production arrays with constant values
        Double totalCost = 0.0;
        for (int i = 0; i < rows; i++) {
            capex[i] = (i == 0) ? mediumWellCapex : 0.0;
            opex[i] = (i != 0) ? mediumWellOpex : 0.0;
            production[i] = (i == 0) ? 0.0 : productionValue;
            Double cost = capex[i] + opex[i];
            totalCost = cost * priceInflationFactor[i]; // Calculate and store total cost
            // Assign capex[i] and opex[i] to costValues array
            totalCostArray[i] = totalCost;
            revenue[i] = production[i] * price * priceInflationFactor[i];
            Double cashFlow = revenue[i] - totalCost;
            // Assign cashFlow value to the netCashFlow array or use it as needed.
            netCashFlow[i] = cashFlow;
        }

        // Calculate Discounted Factor, Discounted Cash Flow, Cumulative Cash Flow,
        // Discounted Cost, and Discounted Production
        Double cumulativeCashFlowValue = 0.0;
        int positiveCashFlowYear = -1; // Initialize to -1 to indicate no positive year found
        for (int i = 0; i < rows; i++) {
            discountedFactor[i] = 1.0 / Math.pow(1.0 + discount_rate, years[i]);
            discountedCashFlow[i] = discountedFactor[i] * netCashFlow[i];
            if (i == 0) {
                cumulativeCashFlow[i] = discountedCashFlow[i];
            } else {
                cumulativeCashFlowValue = discountedCashFlow[i] + cumulativeCashFlow[i - 1];
                cumulativeCashFlow[i] = cumulativeCashFlowValue;
            }
            if (cumulativeCashFlow[i] > 0 && positiveCashFlowYear == -1) {
                positiveCashFlowYear = years[i];
            }


            discountedCost[i] = (capex[i] + opex[i]) * discountedFactor[i];
            discountedProduction[i] = production[i] * discountedFactor[i];
        }
        if (positiveCashFlowYear != -1) {
        }
        SummaryDeepWell response = null;
            int x = summaryDto.getYear()-1; // Use the user-defined 'years'
            // Calculate NPV
            double NPV = cumulativeCashFlow[x];

            // Calculate LCOH
            double sumDiscountedCost = sum(discountedCost, 0, x );
            double sumDiscountedProduction = sum(discountedProduction, 0, x);
       double LCOHValue = sumDiscountedCost / sumDiscountedProduction;
        long LCOHRound = Math.round(LCOHValue);
        int LCOH = (int) LCOHRound;
            // Calculate IRR
            Double[] cashflowSubset = Arrays.copyOfRange(netCashFlow, 0, x);
            double irrValue = calculateIRR(cashflowSubset, years);
            double IRR = (irrValue != 0.0) ? irrValue : 0.0;

            // Calculate P/I
            double PIValue = cumulativeCashFlow[x] / Double.parseDouble(String.valueOf(summaryDto.getDeepWellCapex())) + 1;
        long PIRound = Math.round(PIValue);
        int PI = (int) PIRound;
            // Create and add a response object
            response = new SummaryDeepWell(
                    BigDecimal.valueOf(summaryDto.getYear()),
                    BigDecimal.valueOf(LCOH),
                    BigDecimal.valueOf(NPV),
                    BigDecimal.valueOf(IRR),
                    BigDecimal.valueOf(PI), positiveCashFlowYear
            );

        return response;
    }
    private static double sum(Double[] array, int startIndex, int endIndex) {
        double sum = 0.0;
        for (int i = startIndex; i <= endIndex; i++) {
            sum += array[i];
        }
        return sum;
    }

//    private static double calculateIRR(Double[] netCashflow, int[] years) {
//        try {
//            double irr = 0.0;
//
//            for (double i = 0.0; i <= 1.001; i += 0.001) {
//                irr = i;
//                double[] cumulativeCashFlow = new double[years.length];
//
//                for (int x = 0; x < years.length - 1; x++) {
//                    double discountFactor = 1.0 / Math.pow(1.0 + i, years[x]);
//                    netCashflow[x] = netCashflow[x] * discountFactor;
//
//                    if (x == 0) {
//                        cumulativeCashFlow[x] = netCashflow[x];
//                    } else {
//                        cumulativeCashFlow[x] = netCashflow[x] + netCashflow[x - 1];
//                    }
//                }
//
//                if (cumulativeCashFlow[years.length - 1] <= 0.0) {
//                    break; // Found the IRR, exit the loop
//                }
//            }
//
//            return irr * 100.0; // Return IRR as a percentage
//        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
//            return 0.0;
//        }
private static double calculateIRR(Double[] netCashflow, int[] years) {
    try {
        double irr = 0.0;
        double previousIrr = 0.0;
        int yearsLength = years.length-1;

        for (double i = 0.0; i <= 1.001; i += 0.001) {
            double[] cumulativeCashFlow = new double[yearsLength];
            double[] discountFactor = new double[yearsLength];
            double[] discountedNetCashFlow = new double[yearsLength];

            for (int x = 0; x < yearsLength; x++) {
                discountFactor[x] = 1.0 / Math.pow(1.0 + i, years[x]);
                discountedNetCashFlow[x] = netCashflow[x] * discountFactor[x];

                if (x == 0) {
                    cumulativeCashFlow[x] = discountedNetCashFlow[x];
                } else {
                    cumulativeCashFlow[x] = discountedNetCashFlow[x] + cumulativeCashFlow[x - 1];
                }
            }

            if (cumulativeCashFlow[yearsLength - 1] <= 0.0) {
                irr = i * 100.0; // Found the IRR, set the result
                break;
            }

            // Check for convergence, if not converging, exit the loop
            if (Math.abs(irr - previousIrr) < 0.0001) {
                break;
            }

            previousIrr = irr;
        }

        return irr;
    } catch (IndexOutOfBoundsException e) {
        e.printStackTrace();
        return 0.0;
    }
}




    @PostMapping("/saveInputs")
    public ResponseEntity<?> saveInputs(@RequestBody InputsDto inputs){
       generalInfoServices.saveInputs(inputs);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(apiResponseData);
    }

    @GetMapping("/last-saved-data")
    public ResponseEntity<?> getLastSavedData() {
        ApiResponseData<?> apiResponseData =ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(generalInfoServices.findLastSavedData())
                .build();
        return ResponseEntity.ok(apiResponseData);
    }













}