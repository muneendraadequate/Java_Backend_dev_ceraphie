package com.ceraphi.controller.WellInformation;


import com.ceraphi.utils.ApiResponseData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api")
public class SummaryController {
    public String InstallationCAPEX = "£1,500,000";
    public String ConnectionCAPEX = "£700,000";
    public String LoanRepayment = "£958,550";
    public String TotalOPEX = "£347,100";
    public String TotalCAPEXOPEXLoanRepayment = "£3,505,650";
    public String TotalRevenue = "£4,338,800";
    public String TotalProfit = "£833,150";
    public String DebtService = "£126,342";
    public String DebtServiceCoverageRatio = "1.26";

    @GetMapping("/summary")
    public ResponseEntity<?> summaryData() {
        SummaryController summaryController = new SummaryController();
        List<SummaryController> list = new ArrayList<>();
        list.add(summaryController);
        ApiResponseData<Object> responseData = ApiResponseData.builder().data(list).status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(responseData);
    }









}
