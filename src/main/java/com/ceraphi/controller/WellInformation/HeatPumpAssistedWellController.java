package com.ceraphi.controller.WellInformation;

import com.ceraphi.services.HeatPumpAssistedWellService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HeatPumpAssistedWellController {
private HeatPumpAssistedWellService heatPumpAssistedWellService;
public HeatPumpAssistedWellController(HeatPumpAssistedWellService heatPumpAssistedWellService){
    this.heatPumpAssistedWellService=heatPumpAssistedWellService;

}

//public ResponseEntity<?> heatPumpAssistWell(@RequestBody HeatPumpAssistWellDto heatPumpAssistPumpDto){
//return null;

}
//}
