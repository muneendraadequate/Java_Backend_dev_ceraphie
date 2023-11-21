package com.ceraphi.controller.DataUpdationController;
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
import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
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
//    @PostMapping("/newProDataRecord")
//    public ResponseEntity<?> addNewRecordData(@RequestBody ProDataBaseModelDto proDataBaseModelDto) {
//        dbUpdateService.addNewData(proDataBaseModelDto);
//        return ResponseEntity.ok("Added successfully");
//    }
//pro data table start //////////////////////////////
    @PutMapping("/proDataUpdate")
    public ResponseEntity<?> updateRecords(@RequestBody List<ProDataBaseModelDto> updatedModels) {
        dbUpdateService.updateRecords(updatedModels);

      ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/revert/{changeSetId}")
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
//pro database table end //////////////////////////////



//estimated cost capex deep start //////////////////////////////

    @PutMapping("/estCostCapexDeep")
    public ResponseEntity<?> estimated_cost_Capex_Deep(@RequestBody List<EstimatedCostCapexDeepDto> estimatedCostCapexDeepDto) {
        dbUpdateService.estimated_cost_Capex_Deep(estimatedCostCapexDeepDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/restore/capexDeep/{changeSetId}")
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
//estimated cost capex deep end //////////////////////////////
    @PutMapping("/estCostCapexHP")
    public ResponseEntity<?> estimated_cost_Capex_HP(@RequestBody List<EstimatedCostCapexHPDto> estimatedCostCapexHPDto) {
        dbUpdateService.estimated_cost_Capex_HP(estimatedCostCapexHPDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEstCostCapexHPList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHPDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_HP();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
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
//heat load fuel end //////////////////////////////
//testing download feature
@GetMapping("/download")
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
    @GetMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf() {
        List<ProDataBaseModel> proDataList = dbUpdateService.getAllProData();

        // Generate the PDF byte array
        byte[] pdfBytes = generatePdfs(proDataList);

        // Set up HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "prodata_report.pdf"); // Specify the filename

        // Return the response entity with the PDF bytes and headers
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }

    public byte[] generatePdf(List<ProDataBaseModel> proDataList) {
        return generatePdfs(proDataList);
    }
    public static byte[] generatePdfs(List<ProDataBaseModel> proDataList) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
                float yPosition = yStart;
                float tableHeight = 100f;
                float marginY = 5;
                float cellMargin = 5f;

                // Define the number of columns and their width ratios
                float[] columnWidths = {2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                float tableWidthFixed = tableWidth - 20;
                float[] cellWidths = new float[columnWidths.length];
                for (int i = 0; i < columnWidths.length; i++) {
                    cellWidths[i] = tableWidthFixed * columnWidths[i] / 22f;
                }

                // Draw the table headers
                drawTableHeader(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin);

                // Draw the table rows
                yPosition -= tableHeight;
                drawTableRows(contentStream, margin, yPosition, tableWidth, tableHeight, cellWidths, cellMargin, proDataList);

                // Save the document to a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);

                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return null;
    }


}







