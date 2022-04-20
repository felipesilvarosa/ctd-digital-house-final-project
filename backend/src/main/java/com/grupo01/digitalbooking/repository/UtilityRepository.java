package com.grupo01.digitalbooking.repository;

import com.grupo01.digitalbooking.domain.Utility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtilityRepository extends JpaRepository<Utility,Long> {

    @Query("SELECT u FROM Utility u where u.name in :name")
    List<Utility> findAllByNameIgnoreCase(@Param("name") List<String> name);

}
