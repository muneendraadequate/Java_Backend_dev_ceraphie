package com.ceraphi.controller.ClientController;

import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.services.ClientDetailsService;
import com.ceraphi.utils.ClientDetailsMapper;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;
    private final ClientDetailsMapper clientDetailsMapper;

    public ClientDetailsController(ClientDetailsService clientDetailsService, ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsService = clientDetailsService;
        this.clientDetailsMapper = clientDetailsMapper;
    }

    @PostMapping("/add-client")
    public ResponseEntity<?> saveClientDetails(@Valid @RequestBody ClientDetailsDTO clientDetailsDTO, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder().errors(fieldErrors).status(HttpStatus.BAD_REQUEST.value()).message("Validation error").build();
            return ResponseEntity.badRequest().body(apiResponseData);
        } else {
            ClientDetailsDTO savedClientDetailsDTO = clientDetailsService.saveClientDetails(clientDetailsDTO);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .id(savedClientDetailsDTO.getClientKey())
                    .message("Client data saved successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Object> updateClientDetails(@PathVariable Long clientId, @Valid @RequestBody ClientDetailsDTO updatedClientDetailsDTO, BindingResult result) {
        if (result.hasErrors()) {
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .data(result.getFieldError().getDefaultMessage())
                    .build();
            return ResponseEntity.ok(apiResponseData);
        } else {
            ClientDetailsDTO updatedClientDetails = clientDetailsService.updateClientDetails(clientId, updatedClientDetailsDTO);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                    .status(HttpStatus.OK.value())
                    .id(updatedClientDetails.getClientKey())
                    .message("Client Updated successfully")
                    .build();
            return ResponseEntity.ok(apiResponseData);
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientDetails(@PathVariable Long clientId) {
        ClientDetailsDTO clientDetails = clientDetailsService.getClientDetails(clientId);
        return ResponseEntity.ok(clientDetails);

    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> deleteClientDetails(@PathVariable Long clientId) {
        clientDetailsService.deleteClientDetails(clientId);
        ApiResponseData<?> response = ApiResponseData.builder().message("client with  is deleted successfully").status(HttpStatus.OK.value()).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clients")
    public ResponseEntity<Object> getAllClientsDetails(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {

        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(clientDetailsService.allClients(pageNo, pageSize, sortBy, sortDir))
                .build();
        return ResponseEntity.ok(apiResponseData);
    }
}