package com.ceraphi.repository;

import com.ceraphi.entities.GeneralInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface GeneralInformationRepository extends JpaRepository<GeneralInformation, Long> {
//    List<GeneralInformation> findAllByUser(Long id);

    // Custom query to count project types by country
//    @Query("SELECT w.country, " +
//            "SUM(CASE WHEN w.ProjectType = 'new well' THEN 1 ELSE 0 END) AS newWellsCount, " +
//            "SUM(CASE WHEN w.ProjectType = 'repurposed well' THEN 1 ELSE 0 END) AS repurposedWellsCount " +
//            "FROM GeneralInformation w GROUP BY w.country")
//    List<Object[]> countProjectTypesByCountry();
    @Query("SELECT w.country, " +
            "SUM(CASE WHEN w.ProjectType = 'new well' THEN 1 ELSE 0 END) AS newWellsCount, " +
            "SUM(CASE WHEN w.ProjectType = 'repurposed well' THEN 1 ELSE 0 END) AS repurposedWellsCount " +
            "FROM GeneralInformation w " +
            "WHERE w.user = :userId " +
            "GROUP BY w.country")
    List<Object[]> countProjectTypesByCountryAndUserId(@Param("userId") Long userId);

//    @Query("SELECT SUM(CASE WHEN w.ProjectType = 'new well' THEN 1 ELSE 0 END), " +
//            "SUM(CASE WHEN w.ProjectType = 'repurposed well' THEN 1 ELSE 0 END) " +
//            "FROM GeneralInformation w")
//    List<Object[]> countBothProjectTypes();
@Query("SELECT " +
        "SUM(CASE WHEN w.ProjectType = 'new well' THEN 1 ELSE 0 END) AS newWellsCount, " +
        "SUM(CASE WHEN w.ProjectType = 'repurposed well' THEN 1 ELSE 0 END) AS repurposedWellsCount " +
        "FROM GeneralInformation w " +
        "WHERE w.user = :userId")
List<Object[]> countWellTypesByUserId(@Param("userId") Long userId);

    List<GeneralInformation> findAllByCountry(String country);

    Optional<GeneralInformation> findByClientId(String aLong);
    List<GeneralInformation> findAllByUser(Long userId);


}