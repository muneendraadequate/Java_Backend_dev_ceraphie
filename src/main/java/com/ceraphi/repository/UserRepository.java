package com.ceraphi.repository;


import com.ceraphi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


//    Optional<User> findByUsername(String username);
    User findByUsername(String email);

    User getByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean findByPassword(String password);

}
