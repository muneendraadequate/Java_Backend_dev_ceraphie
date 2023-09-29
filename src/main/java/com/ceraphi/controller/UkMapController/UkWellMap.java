package com.ceraphi.controller.UkMapController;

import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.services.UkMapService;
import com.ceraphi.utils.UkWells.Wells;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UkWellMap {
    @Autowired
    private UkMapService ukMapService;


    @GetMapping("/ukMapWells")
    public ResponseEntity<?> getUkMapWells(@RequestParam String country) {

        List<Wells> ukwells = ukMapService.getUkwells(country);
if(ukwells.isEmpty()){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country not found");
}      else {
    return ResponseEntity.status(HttpStatus.OK).body(ukwells);
}




    }
}
