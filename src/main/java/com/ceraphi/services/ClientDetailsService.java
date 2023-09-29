package com.ceraphi.services;

import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.entities.ClientDetails;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.ClientResponse;
import com.ceraphi.utils.PageableResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ClientDetailsService {
  ClientDetailsDTO saveClientDetails(ClientDetailsDTO clientDetailsDTO);

    ClientDetailsDTO updateClientDetails(Long clientId, ClientDetailsDTO updatedClientDetailsDTO);

  List<ClientDetailsDTO> getClientDetails(Long clientId);
  ClientDetailsDTO getClientDetailsById(Long clientId);

    ClientResponse getAllClients(int pageNo, int pageSize, String sortBy, String sortDir);

    void deleteClientDetails(Long clientId);

    List<ClientDetailsDTO> searchClients(String query);

    List<ClientDetailsDTO> getAllClients();

    PageableResponse allClients(int pageNo, int pageSize, String sortBy, String sortDir);

}