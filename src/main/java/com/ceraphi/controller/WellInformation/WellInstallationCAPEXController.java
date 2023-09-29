package com.ceraphi.controller.WellInformation;

import com.ceraphi.dto.WellInstallationCAPEXDto;
import com.ceraphi.services.WellInstallationCAPEXService;
import com.ceraphi.utils.ApiResponseData;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class WellInstallationCAPEXController {
private WellInstallationCAPEXService wellInstallationCAPEXService;
public WellInstallationCAPEXController(WellInstallationCAPEXService wellInstallationCAPEXService){
    this.wellInstallationCAPEXService=wellInstallationCAPEXService;

}


    @PostMapping("/wellInstallationCAPEX")
    public ResponseEntity<?> saveWellInstallation(@RequestBody WellInstallationCAPEXDto wellInstallationCAPEXDto){
        ApiResponseData<?> apiResponseData = wellInstallationCAPEXService.saveWellInstallationCAPEX(wellInstallationCAPEXDto);
        if (apiResponseData.getStatus() == HttpStatus.OK.value() || apiResponseData.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            return ResponseEntity.ok(apiResponseData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    }





