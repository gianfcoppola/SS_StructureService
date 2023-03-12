package com.gianfcop.ss.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gianfcop.ss.model.Struttura;

@Repository
public interface StrutturaRepository extends JpaRepository<Struttura, Integer>{

    
}
