package com.ceraphi.services.Impl;

import ch.qos.logback.core.net.server.Client;
import com.ceraphi.dto.ClientDetailsDTO;
import com.ceraphi.dto.GeneralInformationDto;
import com.ceraphi.entities.GeneralInformation;
import com.ceraphi.entities.User;
import com.ceraphi.repository.UserRepository;
import com.ceraphi.utils.ApiResponseData;
import com.ceraphi.utils.ClientResponse;
import com.ceraphi.entities.ClientDetails;
import com.ceraphi.exceptions.ResourceNotFoundException;
import com.ceraphi.repository.ClientDetailsRepository;
import com.ceraphi.services.ClientDetailsService;
import com.ceraphi.utils.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private final ClientDetailsRepository clientDetailsRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ClientDetailsServiceImpl(UserRepository userRepository, ClientDetailsRepository clientDetailsRepository, ModelMapper modelMapper) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ClientDetailsDTO saveClientDetails(ClientDetailsDTO clientDetailsDto) {
        // Check if the user exists based on userId
        Optional<User> userOptional = userRepository.findById(clientDetailsDto.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ClientDetails clientDetails = new ClientDetails();
            clientDetails.setEmail(clientDetailsDto.getEmail());
            clientDetails.setClientName(clientDetailsDto.getClientName());
            clientDetails.setClientType(clientDetailsDto.getClientType());
            clientDetails.setUser(user);
            clientDetails.setAddress(clientDetailsDto.getAddress());
            clientDetails.setCity(clientDetailsDto.getCity());
            clientDetails.setCountry(clientDetailsDto.getCountry());
            clientDetails.setPostalCode(clientDetailsDto.getPostalCode());
            clientDetails.setLocalCurrency(clientDetailsDto.getLocalCurrency());
            clientDetails.setRestriction(clientDetailsDto.isRestriction());
            clientDetails.setGeopoliticalData(clientDetailsDto.getGeopoliticalData());
            clientDetails.setRestrictionDetails(clientDetailsDto.getRestrictionDetails());
            clientDetails.setLanguage(clientDetailsDto.getLanguage());
            clientDetails.setIs_deleted(false);
            ClientDetails save = clientDetailsRepository.save(clientDetails);
            ClientDetailsDTO mapDto = modelMapper.map(save, ClientDetailsDTO.class);
            mapDto.setClientKey(save.getId());
            return mapDto;

        } else {
            throw new EntityNotFoundException("User not found with userId: " + clientDetailsDto.getUserId());
        }
    }


//    @Override
//    public ClientDetailsDTO saveClientDetails(ClientDetailsDTO clientDetailsDto) {
//        // Continue with client creation
//        ClientDetails clientDetails = modelMapper.map(clientDetailsDto, ClientDetails.class);
//        clientDetails.setUser(userRepository.findById(clientDetailsDto.getUserId()).get());
//        ClientDetails savedClientDetails = clientDetailsRepository.save(clientDetails);
//        ClientDetailsDTO mapDto = modelMapper.map(savedClientDetails, ClientDetailsDTO.class);
//        mapDto.setClientKey(savedClientDetails.getId());
//        return mapDto;
//    }
//    @Override
//    public ApiResponseData<ClientDetailsDTO> saveClientDetails(ClientDetailsDTO clientDetailsDto) {
//        String email = clientDetailsDto.getEmail();
//
//        // Check if email already exists
//        if (clientDetailsRepository.existsByEmail(email)) {
//            // Email is already taken, return a response indicating that
//            return ApiResponseData.<ClientDetailsDTO>builder()
//                    .status(HttpStatus.BAD_REQUEST.value())
//                    .message("Email already exists")
//                    .build();
//        }
//
//        // Continue with client creation
//        ClientDetails clientDetails = modelMapper.map(clientDetailsDto, ClientDetails.class);
//        ClientDetails savedClientDetails = clientDetailsRepository.save(clientDetails);
//        ClientDetailsDTO mapDto = modelMapper.map(savedClientDetails, ClientDetailsDTO.class);
//        mapDto.setClientKey(savedClientDetails.getId());
//
//        return ApiResponseData.<ClientDetailsDTO>builder()
//                .status(HttpStatus.OK.value())
//                .data(mapDto)
//                .message("Client data saved successfully")
//                .build();
//    }

    @Override
    public ClientDetailsDTO updateClientDetails(Long clientId, ClientDetailsDTO updatedClientDetailsDTO) {
        ClientDetails existingClientDetails = clientDetailsRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client details not found", "id", clientId));
        // Update the existing client details with the new data
        existingClientDetails.setClientName(updatedClientDetailsDTO.getClientName());
        existingClientDetails.setClientType(updatedClientDetailsDTO.getClientType());
        existingClientDetails.setEmail(updatedClientDetailsDTO.getEmail());
        existingClientDetails.setLanguage(updatedClientDetailsDTO.getLanguage());
        existingClientDetails.setAddress(updatedClientDetailsDTO.getAddress());
        existingClientDetails.setCity(updatedClientDetailsDTO.getCity());
        existingClientDetails.setCountry(updatedClientDetailsDTO.getCountry());
        existingClientDetails.setPostalCode(updatedClientDetailsDTO.getPostalCode());
        existingClientDetails.setLocalCurrency(updatedClientDetailsDTO.getLocalCurrency());
        existingClientDetails.setRestrictionDetails(updatedClientDetailsDTO.getRestrictionDetails());
        existingClientDetails.setGeopoliticalData(updatedClientDetailsDTO.getGeopoliticalData());
        existingClientDetails.setRestrictionDetails(updatedClientDetailsDTO.getRestrictionDetails());
        existingClientDetails.setRestriction(updatedClientDetailsDTO.isRestriction());

        ClientDetails updatedClientDetails = clientDetailsRepository.save(existingClientDetails);
        ClientDetailsDTO clientDetailsDTO = new ClientDetailsDTO();
        clientDetailsDTO.setClientKey(clientId);
        return clientDetailsDTO;

    }

    @Override
    public List<ClientDetailsDTO> getClientDetails(Long clientId) {
        List<ClientDetails> clientDetails = clientDetailsRepository.findAllByUserId(clientId);

        // Sort the list in descending order by a specific field (e.g., id)
        clientDetails.sort(Comparator.comparingLong(ClientDetails::getId).reversed());

        List<ClientDetailsDTO> clients = clientDetails.stream()
                .map(s -> mapToDto(s))
                .collect(Collectors.toList());

        return clients;
    }

    @Override
    public ClientDetailsDTO getClientDetailsById(Long clientId) {

        Optional<ClientDetails> byId = clientDetailsRepository.findById(clientId);
        ClientDetails clientDetails = byId.get();

        ClientDetailsDTO clientDetailsDTO = modelMapper.map(clientDetails, ClientDetailsDTO.class);
        return clientDetailsDTO;
    }


    @Override
    public ClientResponse getAllClients(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ClientDetails> client = clientDetailsRepository.findAll(pageable);
        List<ClientDetails> clientDetails = client.getContent();
        List<ClientDetailsDTO> clients = clientDetails.stream()
                .map(clientDetail -> modelMapper.map(clientDetail, ClientDetailsDTO.class))
                .collect(Collectors.toList());
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setClientDetails(clients);
        clientResponse.setPageNumber(client.getNumber());
        clientResponse.setPageSize(client.getSize());
        clientResponse.setTotalPages(client.getTotalPages());
        clientResponse.setTotalElements(client.getTotalElements());
        clientResponse.setLast(client.isLast());
        return clientResponse;

    }

    @Override
    public void deleteClientDetails(Long clientId) {
        ClientDetails clientDetails = clientDetailsRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client", "id", clientId));
        clientDetails.setIs_deleted(true);
        clientDetailsRepository.save(clientDetails);
    }

    @Override
    public List<ClientDetailsDTO> searchClients(String query) {
        List<ClientDetails> clientDetails = clientDetailsRepository.searchPosts(query);
        List<ClientDetailsDTO> clients = clientDetails.stream()
                .map(clientDetail -> modelMapper.map(clientDetail, ClientDetailsDTO.class))
                .collect(Collectors.toList());
        return clients;
    }

    @Override
    public List<ClientDetailsDTO> getAllClients() {
        List<ClientDetails> clients = clientDetailsRepository.findAll();
        List<ClientDetailsDTO> clientsDetails = clients.stream()
                .map(clientDetail -> modelMapper.map(clientDetail, ClientDetailsDTO.class))
                .collect(Collectors.toList());
        return clientsDetails;
    }

    @Override
    public PageableResponse allClients(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ClientDetails> client = clientDetailsRepository.findAll(pageable);
        List<ClientDetails> clientDetails = client.getContent();
        List<ClientDetailsDTO> clients = clientDetails.stream()
                .map(s -> mapToDto(s))
                .collect(Collectors.toList());
        return PageableResponse.builder()
                .clients(clients)
                .totalElements(client.getNumberOfElements())
                .totalPages(client.getTotalPages())
                .number(client.getNumber())
                .last(client.isLast())
                .build();

    }

    public ClientDetailsDTO mapToDto(ClientDetails clientDetails) {
        ClientDetailsDTO clientDetailsDTO = modelMapper.map(clientDetails, ClientDetailsDTO.class);

        clientDetailsDTO.setClientKey(clientDetails.getId());
        return clientDetailsDTO;
    }

}