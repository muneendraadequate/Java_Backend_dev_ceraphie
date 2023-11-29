package com.ceraphi.repository;

import com.ceraphi.entities.CountriesList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountriesRepo extends JpaRepository<CountriesList,Long> {
}
