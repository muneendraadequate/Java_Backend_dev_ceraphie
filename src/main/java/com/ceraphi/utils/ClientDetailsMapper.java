package com.ceraphi.utils;

import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.entities.ClientDetails;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientDetailsMapper {

    private final ModelMapper modelMapper;

    public ClientDetailsMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ClientDetailsDTO toDto(ClientDetails clientDetails) {
        return modelMapper.map(clientDetails, ClientDetailsDTO.class);
    }

    public ClientDetails toEntity(ClientDetailsDTO clientDetailsDTO) {
        return modelMapper.map(clientDetailsDTO, ClientDetails.class);
    }
//    public List<ClientDetailsDTO> toDtoList(List<ClientDetails> clientDetailsList) {
//        return clientDetailsList.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//    public ClientDetailsDTO toDto(ClientDetails clientDetails) {
//        ClientDetailsDTO dto = new ClientDetailsDTO();
//        dto.setClientId(clientDetails.getId());
//        dto.setClientName(clientDetails.getClientName());
//        dto.setCountry(clientDetails.getCountry());
//        // Set other fields of the DTO based on the ClientDetails entity
//
//        return dto;
//    }
}
