package com.ceraphi.repository;

import com.ceraphi.entities.ClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    Optional<ClientDetails> findByEmail(String email);

    @Query("SELECT cd FROM ClientDetails cd WHERE cd.clientName LIKE %:searchTerm% OR cd.country LIKE %:searchTerm% OR cd.id LIKE %:searchTerm%")
    List<ClientDetails> searchPosts(@Param("searchTerm") String searchTerm);

    boolean existsByEmail(String email);
}
