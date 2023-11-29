package com.ceraphi.controller;

import com.ceraphi.dto.NewWellCreationDto;
import com.ceraphi.entities.CountriesList;
import com.ceraphi.services.Impl.NewWellsProjectService;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class NewWellProjectController {
    @Autowired
    private NewWellsProjectService newWellsProjectService;

    @PostMapping("/saveTheData")
    private ResponseEntity<?> saveTheData(@RequestBody NewWellCreationDto newWellCreationDto) {
        ApiResponseData apiResponseData = newWellsProjectService.saveTheNewWellProject(newWellCreationDto);
        return ResponseEntity.ok(apiResponseData);
    }

    @PutMapping("/updateNewWellsData/{id}")
    public ResponseEntity<?> updateNewWellsData(@RequestBody NewWellCreationDto newWellCreationDto, @PathVariable("id") Long id) {
        ApiResponseData apiResponseData = newWellsProjectService.updateTheNewWellProject(newWellCreationDto, id);
        return ResponseEntity.ok(apiResponseData);
    }

    @GetMapping("/getProjectById/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable("id") Long id) {
        ApiResponseData apiResponseData = newWellsProjectService.getProjectById(id);
        return ResponseEntity.ok(apiResponseData);
    }


    @GetMapping("/getCountryCodes")
    public List<CountriesList> getAllCountries() {
        return newWellsProjectService.getAllCountries();
    }
}

