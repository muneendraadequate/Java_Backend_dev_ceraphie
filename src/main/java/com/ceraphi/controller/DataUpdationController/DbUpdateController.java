package com.ceraphi.controller.DataUpdationController;

import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.entities.MasterDataTables.ProDataBaseModel;
import com.ceraphi.services.Impl.DbUpdateService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("restored successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }


    @PutMapping("/estCostCapexDeep")
    public ResponseEntity<?> estimated_cost_Capex_Deep(@RequestBody EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        dbUpdateService.estimated_cost_Capex_Deep(estimatedCostCapexDeepDto);
        ApiResponseData<?> response = ApiResponseData.builder().message("update successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getEstCostCapexDeepList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeepDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_Deep();
        ApiResponseData<?> response = ApiResponseData.builder().data(theDataList).message("successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/estCostCapexHP")
    public ResponseEntity<?> estimated_cost_Capex_HP(@RequestBody EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
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

    @PutMapping("/estCostOpexDeep")
    public ResponseEntity<?> estimated_cost_Opex_Deep(@RequestBody EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
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
    @PutMapping("/estCostOpexHP")
    public ResponseEntity<?> estimated_cost_Opex_HP(@RequestBody EstimatedCostOpexHpDto estimatedCostOpexHpDto) {
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
    @PutMapping("/gelDataWell")
    public ResponseEntity<?> gelDataWellData(@RequestBody GelDataWellDto gelDataWellDto) {
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



    @PutMapping("/heatLoadFuel")
    public ResponseEntity<?> heatLoadFuel(@RequestBody HeatLoadFuelsDto heatLoadFuelsDto) {
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



}



