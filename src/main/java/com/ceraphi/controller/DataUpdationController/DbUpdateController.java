package com.ceraphi.controller.DataUpdationController;
import com.ceraphi.entities.LogEntities.*;
import com.ceraphi.entities.MasterDataTables.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.services.Impl.DbUpdateService;
import com.ceraphi.utils.ApiResponseData;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.io.IOException;

import static com.ceraphi.utils.PdfTableGenerator.drawTableHeader;
import static com.ceraphi.utils.PdfTableGenerator.drawTableRows;

@RestController
@RequestMapping("/api")
public class DbUpdateController {
    @Autowired
    private DbUpdateService dbUpdateService;

//pro data table start //////////////////////////////
    @PutMapping("/proDataUpdate")
    public ResponseEntity<?> updateRecords(@RequestBody List<ProDataBaseModelDto> updatedModels) {
        dbUpdateService.updateRecords(updatedModels);
      ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restore/{changeSetId}")
    public ResponseEntity<?> revertChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("/ProDataList")
    public ResponseEntity<?> getDataList() {
        List<ProDataBaseModelDto> theDataList = dbUpdateService.getTheDataList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/ProDataFindById/{id}")
    public ResponseEntity<?> dataFindById(@PathVariable Long id) {
        ProDataBaseModelDto theData = dbUpdateService.dataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("proData/download")
    public ResponseEntity<byte[]> downloadProData() {
        List<ProDataBaseModel> proDataList = dbUpdateService.getAllProData(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSV(proDataList);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "prodata.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSV(List<ProDataBaseModel> proDataList) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVString(proDataList);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVString(List<ProDataBaseModel> proDataList) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "geothermal_gradient", "steady_state_temp", "k_wt", "flow_rate",
                    "pumping_power", "depth", "delta", "pressure_loss", "bht", "return_value"};
            csvWriter.writeNext(header);

            // Writing data
            for (ProDataBaseModel proData : proDataList) {
                String[] data = {
                        String.valueOf(proData.getId()),
                        String.valueOf(proData.getGeothermalGradient()),
                        String.valueOf(proData.getSteadyStateTemp()),
                        String.valueOf(proData.getKWt()),
                        String.valueOf(proData.getFlowRate()),
                        String.valueOf(proData.getPumpingPower()),
                        String.valueOf(proData.getDepth()),
                        String.valueOf(proData.getDelta()),
                        String.valueOf(proData.getPressureLoss()),
                        String.valueOf(proData.getBHT()),
                        String.valueOf(proData.getReturnValue())
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
@GetMapping("/getChangesSetList")
public ResponseEntity<?> getTheChangesSetList() {
    List<ChangeSetDto> theChangesSetList = dbUpdateService.getTheChangesSetList();
   ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
    return ResponseEntity.ok(response);
}
//pro database table end //////////////////////////////
//estimated cost capex deep start //////////////////////////////

    @PutMapping("/estCostCapexDeep")
    public ResponseEntity<?> estimated_cost_Capex_Deep(@RequestBody List<EstimatedCostCapexDeepDto> estimatedCostCapexDeepDto) {
        dbUpdateService.estimated_cost_Capex_Deep(estimatedCostCapexDeepDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreCapexDeep/{changeSetId}")
    public ResponseEntity<?> restoreDeepCapexChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.restoreDeepCapexChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getEstCostCapexDeepList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeepDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_Deep();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/CapexDeepFindById/{id}")
    public ResponseEntity<?> capexDeepDataFindById(@PathVariable Long id) {
        EstimatedCostCapexDeepDto theData = dbUpdateService.CapexDeepDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("capexDeep/download")
    public ResponseEntity<byte[]> downloadCapexDeep() {
        List<EstimatedCostCapexDeep> list = dbUpdateService.getAllCapexDeep(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVCapexDeep(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "NewDeepCapex.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVCapexDeep(List<EstimatedCostCapexDeep> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringCapexDeep(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringCapexDeep(List<EstimatedCostCapexDeep> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "cost", "operation", "per_well"};
            csvWriter.writeNext(header);

            // Writing data
            for (EstimatedCostCapexDeep proData : list) {
                String[] data = {
                        String.valueOf(proData.getId()),
                        String.valueOf(proData.getCost()),
                        String.valueOf(proData.getOperation()),
                        String.valueOf(proData.getPerWell()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getCapexDeepChangesList")
    public ResponseEntity<?> getTheCapexDeepChangesList() {
        List<ChangesSetCapexDeep> theChangesSetList = dbUpdateService.getTheCapexDeepChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    //estimated cost capex deep end //////////////////////////////
    //estimated cost capex HP start //////////////////////////////
    @PutMapping("/estCostCapexHP")
    public ResponseEntity<?> estimated_cost_Capex_HP(@RequestBody List<EstimatedCostCapexHPDto> estimatedCostCapexHPDto) {
        dbUpdateService.estimated_cost_Capex_HP(estimatedCostCapexHPDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreCapexHp/{changeSetId}")
    public ResponseEntity<?> restoreCapexHpChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertCapexHpChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("/getEstCostCapexHPList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHPDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_HP();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/CapexHpFindById/{id}")
    public ResponseEntity<?> capexHpDataFindById(@PathVariable Long id) {
        EstimatedCostCapexHPDto theData = dbUpdateService.CapexHpDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("capexHp/download")
    public ResponseEntity<byte[]> downloadCapexHp() {
        List<EstimatedCostCapexHP> list = dbUpdateService.getAllCapexHp(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVCapexHp(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "NewMediumCapex.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVCapexHp(List<EstimatedCostCapexHP> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringCapexHp(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringCapexHp(List<EstimatedCostCapexHP> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "cost", "operation", "per_well"};
            csvWriter.writeNext(header);

            // Writing data
            for (EstimatedCostCapexHP proData : list) {
                String[] data = {
                        String.valueOf(proData.getId()),
                        String.valueOf(proData.getCost()),
                        String.valueOf(proData.getOperation()),
                        String.valueOf(proData.getPerWell()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getCapexHpChangesList")
    public ResponseEntity<?> getTheCapexHpChangesList() {
        List<ChangesSetCapexHp> theChangesSetList = dbUpdateService.getTheCapexHpChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    //estimated cost opex deep start //////////////////////////////
    @PutMapping("/estCostOpexDeep")
    public ResponseEntity<?> estimated_cost_Opex_Deep(@RequestBody List<EstimatedCostOpexDeepDto> estimatedCostOpexDeepDto) {
        dbUpdateService.estimated_cost_Opex_Deep(estimatedCostOpexDeepDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEstCostOpexDeepList")
    public ResponseEntity<?> getTheDataListEst_Cost_Opex_Deep() {
        List<EstimatedCostOpexDeepDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Opex_Deep();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/opexDeepFindById/{id}")
    public ResponseEntity<?> opexDeepDataFindById(@PathVariable Long id) {
        EstimatedCostOpexDeepDto theData = dbUpdateService.opexDeepDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreOpexDeep/{changeSetId}")
    public ResponseEntity<?> restoreOpexDeepChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertOpexDeepChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("opexDeep/download")
    public ResponseEntity<byte[]> downloadOpexDeep() {
        List<EstimatedCostOpexDeep> list = dbUpdateService.getAllOpexDeep(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVOpexDeep(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "NewDeepOpex .csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVOpexDeep(List<EstimatedCostOpexDeep> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringOpexDeep(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringOpexDeep(List<EstimatedCostOpexDeep> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "cost", "operation", "per_well"};
            csvWriter.writeNext(header);

            // Writing data
            for (EstimatedCostOpexDeep proData : list) {
                String[] data = {
                        String.valueOf(proData.getId()),
                        String.valueOf(proData.getCost()),
                        String.valueOf(proData.getOperation()),
                        String.valueOf(proData.getPerWell()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getOpexDeepChangesList")
    public ResponseEntity<?> getOpexDeepTheCapChangesList() {
        List<OpexDeepChangesSet> theChangesSetList = dbUpdateService.getTheOpexDeepChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

//estimated cost opex deep end //////////////////////////////`


//estimated cost opex hp start //////////////////////////////
    @PutMapping("/estCostOpexHP")
    public ResponseEntity<?> estimated_cost_Opex_HP(@RequestBody List<EstimatedCostOpexHpDto> estimatedCostOpexHpDto) {
        dbUpdateService.estimated_cost_Opex_HP(estimatedCostOpexHpDto);

        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEstCostOpexHPList")
    public ResponseEntity<?> getTheDataListEst_Cost_Opex_HP() {
        List<EstimatedCostOpexHpDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Opex_HP();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/opexHpFindById/{id}")
    public ResponseEntity<?> opexHpDataFindById(@PathVariable Long id) {
        EstimatedCostOpexHpDto theData = dbUpdateService.opexHpDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreOpexHp/{changeSetId}")
    public ResponseEntity<?> restoreOpexHpChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertOpexHpChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("opexHp/download")
    public ResponseEntity<byte[]> downloadOpexHp() {
        List<EstimatedCostOpexHP> list = dbUpdateService.getAllOpexHp(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVOpexHp(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "NewMediumOpex.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVOpexHp(List<EstimatedCostOpexHP> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringOpexHp(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringOpexHp(List<EstimatedCostOpexHP> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "cost", "operation", "per_well"};
            csvWriter.writeNext(header);

            // Writing data
            for (EstimatedCostOpexHP proData : list) {
                String[] data = {
                        String.valueOf(proData.getId()),
                        String.valueOf(proData.getCost()),
                        String.valueOf(proData.getOperation()),
                        String.valueOf(proData.getPerWell()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getOpexHpChangesList")
    public ResponseEntity<?> getOpexHpTheCapChangesList() {
        List<OpexHpChangesSet> theChangesSetList = dbUpdateService.getTheOpexHpChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    //estimated cost opex hp end //////////////////////////////
    //gel data well start //////////////////////////////
    @PutMapping("/gelDataWell")
    public ResponseEntity<?> gelDataWellData(@RequestBody List<GelDataWellDto> gelDataWellDto) {
        dbUpdateService.gelDataWell(gelDataWellDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getGelDataWellList")
    public ResponseEntity<?> getTheDataListGelDataWell() {
        List<GelDataWellDto> theDataList = dbUpdateService.getTheDataListGelDataWell();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/gelDataFindById/{id}")
    public ResponseEntity<?> gelDataFindById(@PathVariable Long id) {
        GelDataWellDto theData = dbUpdateService.gelDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreGelData/{changeSetId}")
    public ResponseEntity<?> restoreGelDataChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertGelDataChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("gelData/download")
    public ResponseEntity<byte[]> downloadGelData() {
        List<GelDataWell> list = dbUpdateService.getAllGelData(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVGelData(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "MediumDeepNewWell.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVGelData(List<GelDataWell> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringGelData(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringGelData(List<GelDataWell> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "cop", "capacity", "flow_rate","temp_required","well_outlet_temp"};
            csvWriter.writeNext(header);

            // Writing data
            for (GelDataWell Data : list) {
                String[] data = {
                        String.valueOf(Data.getId()),
                        String.valueOf(Data.getCOP()),
                        String.valueOf(Data.getCapacity()),
                        String.valueOf(Data.getFlowRate()),
                        String.valueOf(Data.getTempRequired()),
                        String.valueOf(Data.getWellOutletTemp()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getGelDataChangesList")
    public ResponseEntity<?> getGelDataTheCapChangesList() {
        List<GelDataWellChangesSet> theChangesSetList = dbUpdateService.getTheGellDataChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
//gel data well end //////////////////////////////

//heat load fuel start //////////////////////////////
    @PutMapping("/heatLoadFuel")
    public ResponseEntity<?> heatLoadFuel(@RequestBody List<HeatLoadFuelsDto> heatLoadFuelsDto) {
        dbUpdateService.heatLoadFuelsData(heatLoadFuelsDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getHeatLoadFuelList")
    public ResponseEntity<?> heatLoadDataList() {
        List<HeatLoadFuelsDto> theDataList = dbUpdateService.getTheHeatLoadFuelList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/heatLoadDataFindById/{id}")
    public ResponseEntity<?> heatLoadDataFindById(@PathVariable Long id) {
        HeatLoadFuelsDto theData = dbUpdateService.heatLoadDataFindById(id);
        ApiResponseData<?> response = ApiResponseData.builder().data(theData).message("successfully get the Data").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restoreHeatLoadData/{changeSetId}")
    public ResponseEntity<?> restoreHeatLoadDataChangeSet(@PathVariable Long changeSetId) {
        dbUpdateService.revertHeatLoadDataChangeSet(changeSetId);
        ApiResponseData<?> response = ApiResponseData.builder().message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);

    }
    @GetMapping("HeatLoadFuel/download")
    public ResponseEntity<byte[]> downloadHeatLoadFuel() {
        List<HeatLoadFuels> list = dbUpdateService.getAllHeatLoadData(); // Replace with your service method to get all records

        byte[] csvData = convertProDataToCSVHeatLoad(list);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "prodata.csv");

        return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
    }

    private byte[] convertProDataToCSVHeatLoad(List<HeatLoadFuels> list) {
        // Implement logic to convert the list of ProDataBaseModel to CSV format
        // You can use a library like OpenCSV or manually create the CSV string
        // For simplicity, let's assume a method called convertToCSVString is available
        String csvString = convertToCSVStringHeatLoad(list);

        return csvString.getBytes(StandardCharsets.UTF_8);
    }

    public static String convertToCSVStringHeatLoad(List<HeatLoadFuels> list) {
        try (StringWriter stringWriter = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(stringWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                     CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {

            // Writing header
            String[] header = {"id", "carbon", "efficiency", "fuel_type","ghg","nox","noxn"};
            csvWriter.writeNext(header);

            // Writing data
            for (HeatLoadFuels Data : list) {
                String[] data = {
                        String.valueOf(Data.getId()),
                        String.valueOf(Data.getCarbon()),
                        String.valueOf(Data.getEfficiency()),
                        String.valueOf(Data.getFuelType()),
                        String.valueOf(Data.getGhg()),
                        String.valueOf(Data.getNox()),
                        String.valueOf(Data.getNoxN()),
                };
                csvWriter.writeNext(data);
            }

            return stringWriter.toString();
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
            return ""; // Return empty string in case of an error
        }
    }
    @GetMapping("/getHeatLoadChangesList")
    public ResponseEntity<?> getHeatLoadTheCapChangesList() {
        List<HeatLoadChangesSet> theChangesSetList = dbUpdateService.getTheHeatLoadChangesList();
        ApiResponseData<?> response = ApiResponseData.builder().data(theChangesSetList).message("successfully get the list ").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
//heat load fuel end //////////////////////////////
//testing download feature

//    @GetMapping("/download-pdf")
//    public ResponseEntity<byte[]> downloadPdf() {
//        List<ProDataBaseModel> proDataList = dbUpdateService.getAllProData();
//
//        // Generate the PDF byte array
//        byte[] pdfBytes = generatePdfs(proDataList);
//
//        // Set up HTTP headers for the response
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "prodata_report.pdf"); // Specify the filename
//
//        // Return the response entity with the PDF bytes and headers
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }
//
//    public byte[] generatePdf(List<ProDataBaseModel> proDataList) {
//        return generatePdfs(proDataList);
//    }
//    public static byte[] generatePdfs(List<ProDataBaseModel> proDataList) {
//        try (PDDocument document = new PDDocument()) {
//            PDPage page = new PDPage(PDRectangle.A4);
//            document.addPage(page);
//
//            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//                float margin = 50;
//                float yStart = page.getMediaBox().getHeight() - margin;
//                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
//                float yPosition = yStart;
//                float tableHeight = 100f;
//                float marginY = 5;
//                float cellMargin = 5f;
//
//                // Define the number of columns and their width ratios
//                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
//                float tableWidthFixed = tableWidth - 20;
//                float[] cellWidths = new float[columnWidths.length];
//                for (int i = 0; i < columnWidths.length; i++) {
//                    cellWidths[i] = tableWidthFixed * columnWidths[i] / 22f;
//                }
//
//                // Draw the table headers
//                drawTableHeader(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin);
//
//                // Draw the table rows
//                yPosition -= tableHeight;
//                drawTableRows(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin, proDataList);
//
//                // Save the document to a byte array
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                document.save(byteArrayOutputStream);
//
//                return byteArrayOutputStream.toByteArray();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception as needed
//        }
//
//        return null;
//    }


}







