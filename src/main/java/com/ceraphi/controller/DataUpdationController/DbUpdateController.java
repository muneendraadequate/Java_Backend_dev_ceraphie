package com.ceraphi.controller.DataUpdationController;

import com.ceraphi.dto.MasterDataTablesDto.*;
import com.ceraphi.dto.ProDataBaseModelDto;
import com.ceraphi.dto.WellInfoDto;
import com.ceraphi.services.Impl.DbUpdateService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DbUpdateController {
    @Autowired
    private DbUpdateService dbUpdateService;

    @PutMapping("/proDataUpdate")
    public ResponseEntity<?> updateData(@RequestBody ProDataBaseModelDto proDataBaseModelDto) {
        dbUpdateService.updateExistingData(proDataBaseModelDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/ProDataList")
    public ResponseEntity<List<ProDataBaseModelDto>> getDataList() {
        List<ProDataBaseModelDto> theDataList = dbUpdateService.getTheDataList();
        return ResponseEntity.ok(theDataList);
    }


    @PutMapping("/estCostCapexDeep")
    public ResponseEntity<?> estimated_cost_Capex_Deep(@RequestBody EstimatedCostCapexDeepDto estimatedCostCapexDeepDto) {
        dbUpdateService.estimated_cost_Capex_Deep(estimatedCostCapexDeepDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getEstCostCapexDeepList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_Deep() {
        List<EstimatedCostCapexDeepDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_Deep();
        return ResponseEntity.ok(theDataList);
    }

    @PutMapping("/estCostCapexHP")
    public ResponseEntity<?> estimated_cost_Capex_HP(@RequestBody EstimatedCostCapexHPDto estimatedCostCapexHPDto) {
        dbUpdateService.estimated_cost_Capex_HP(estimatedCostCapexHPDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getEstCostCapexHPList")
    public ResponseEntity<?> getTheDataListEst_Cost_Capex_HP() {
        List<EstimatedCostCapexHPDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Capex_HP();
        return ResponseEntity.ok(theDataList);
    }

    @PutMapping("/estCostOpexDeep")
    public ResponseEntity<?> estimated_cost_Opex_Deep(@RequestBody EstimatedCostOpexDeepDto estimatedCostOpexDeepDto) {
        dbUpdateService.estimated_cost_Opex_Deep(estimatedCostOpexDeepDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getEstCostOpexDeepList")
    public ResponseEntity<?> getTheDataListEst_Cost_Opex_Deep() {
        List<EstimatedCostOpexDeepDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Opex_Deep();
        return ResponseEntity.ok(theDataList);
    }
    @PutMapping("/estCostOpexHP")
    public ResponseEntity<?> estimated_cost_Opex_HP(@RequestBody EstimatedCostOpexHpDto estimatedCostOpexHpDto) {
        dbUpdateService.estimated_cost_Opex_HP(estimatedCostOpexHpDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getEstCostOpexHPList")
    public ResponseEntity<?> getTheDataListEst_Cost_Opex_HP() {
        List<EstimatedCostOpexHpDto> theDataList = dbUpdateService.getTheDataListEst_Cost_Opex_HP();
        return ResponseEntity.ok(theDataList);
    }
    @PutMapping("/gelDataWell")
    public ResponseEntity<?> gelDataWellData(@RequestBody GelDataWellDto gelDataWellDto) {
        dbUpdateService.gelDataWell(gelDataWellDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getGelDataWellList")
    public ResponseEntity<?> getTheDataListGelDataWell() {
        List<GelDataWellDto> theDataList = dbUpdateService.getTheDataListGelDataWell();
        return ResponseEntity.ok(theDataList);
    }



    @PutMapping("/heatLoadFuel")
    public ResponseEntity<?> heatLoadFuel(@RequestBody HeatLoadFuelsDto heatLoadFuelsDto) {
        dbUpdateService.heatLoadFuelsData(heatLoadFuelsDto);
        return ResponseEntity.ok("update successfully");
    }

    @GetMapping("/getHeatLoadFuelList")
    public ResponseEntity<?> heatLoadDataList() {
        List<HeatLoadFuelsDto> theDataList = dbUpdateService.getTheHeatLoadFuelList();
        return ResponseEntity.ok(theDataList);
    }






}
