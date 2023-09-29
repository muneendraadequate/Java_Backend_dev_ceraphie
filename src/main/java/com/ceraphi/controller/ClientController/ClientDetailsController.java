package com.ceraphi.controller.ClientController;

import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.dto.CostCalculatorDto;
import com.ceraphi.repository.ClientDetailsRepository;
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
@RequestMapping("/api")
@CrossOrigin("*")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;
    private final ClientDetailsRepository clientDetailsRepository;
    private final ClientDetailsMapper clientDetailsMapper;

    public ClientDetailsController(ClientDetailsRepository clientDetailsRepository,ClientDetailsService clientDetailsService, ClientDetailsMapper clientDetailsMapper) {
        this.clientDetailsService = clientDetailsService;
        this.clientDetailsMapper = clientDetailsMapper;
        this.clientDetailsRepository = clientDetailsRepository;
    }

    @PostMapping("/add-client")
    public ResponseEntity<?> saveClientDetails(@Valid @RequestBody ClientDetailsDTO clientDetailsDTO, BindingResult result) {
        boolean b = clientDetailsRepository.existsByEmail(clientDetailsDTO.getEmail());
        if (result.hasErrors() || b==true) {
            ApiResponseData apiResponseData1 = new ApiResponseData();
            List fieldErrors = apiResponseData1.getFieldErrors(result);
            ApiResponseData<?> apiResponseData = ApiResponseData.builder().errors(fieldErrors).status(HttpStatus.ALREADY_REPORTED.value()).message("Validation error").message("Email error").build();
            return ResponseEntity.ok(apiResponseData);
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

    @GetMapping("/clients/{clientId}")
    public ResponseEntity<?> getClientDetails(@PathVariable Long clientId) {
        List<ClientDetailsDTO> clientDetails = clientDetailsService.getClientDetails(clientId);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(clientDetails)
                .build();
        return ResponseEntity.ok(apiResponseData);
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
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {

        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(clientDetailsService.allClients(pageNo, pageSize, sortBy, sortDir))
                .build();
        return ResponseEntity.ok(apiResponseData);
    }
    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientDetailsById(@PathVariable Long clientId) {
        ClientDetailsDTO clientDetailsDTO = clientDetailsService.getClientDetailsById(clientId);
        ApiResponseData<?> apiResponseData = ApiResponseData.builder()
                .status(HttpStatus.OK.value())
                .data(clientDetailsDTO)
                .build();
        return ResponseEntity.ok(apiResponseData);}

}