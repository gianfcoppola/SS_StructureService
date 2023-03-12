package com.gianfcop.ss.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gianfcop.ss.model.CentroSportivo;

@Repository
public interface CentroSportivoRepository extends JpaRepository<CentroSportivo, Integer>{
    
}
