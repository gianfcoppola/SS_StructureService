package com.gianfcop.ss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.gianfcop.ss.dto.StrutturaDtoIn;
import com.gianfcop.ss.dto.StrutturaDtoOut;
import com.gianfcop.ss.dto.StrutturaUpdateDTO;
import com.gianfcop.ss.mapper.StrutturaMapper;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.repository.StrutturaRepository;

@Service
public class StrutturaService {
    
    private StrutturaRepository strutturaRepository;

    public StrutturaService(StrutturaRepository strutturaRepository) {
        this.strutturaRepository = strutturaRepository;
    }

    public Struttura insertStruttura(StrutturaDtoIn strutturaDtoIn){

        Struttura struttura = Struttura.builder()
            .descrizione(strutturaDtoIn.getDescrizione())
            .prezzoPrenotazione(strutturaDtoIn.getPrezzoPrenotazione())
            .prezzoAbbonamentoMensile(strutturaDtoIn.getPrezzoAbbonamentoMensile())
            .id(strutturaDtoIn.getId())
            .build();

        return strutturaRepository.save(struttura);
    }

    public Struttura getStrutturaById(int id){
        return strutturaRepository
            .findById(id)
            .orElseThrow(() -> new NoSuchElementException("La struttura richiesta non esiste"));

    }


    public List<Struttura> getAllStrutture(){

        return strutturaRepository.findAll();

    }

    public List<StrutturaDtoOut> getAllStruttureDtoOut(){

        List<Struttura> strutture = strutturaRepository.findAll();
        List<StrutturaDtoOut> struttureDtoOuts = new ArrayList<>();
        for(Struttura s: strutture)
            struttureDtoOuts.add(StrutturaMapper.toStrutturaDtoOut(s));

        return struttureDtoOuts;

    }

    public List<StrutturaUpdateDTO> getDatiStrutture(){
        List<Struttura> strutture = strutturaRepository.findAll();
        List<StrutturaUpdateDTO> str = new ArrayList<>();

        for(Struttura s: strutture)
            str.add(StrutturaMapper.toStrutturaUpdateDTO(s));

        return str;
    }

    public List<String> getAllNomiStrutture(){

        return strutturaRepository.findAll()
            .stream()
            .map(s -> s.getDescrizione())
            .toList();

    }

    public Struttura modificaStruttura(int idStruttura, Struttura struttura){
        Struttura s = strutturaRepository
            .findById(idStruttura)
            .orElseThrow(() -> new NoSuchElementException("La struttura richiesta non esiste"));
        s.setPrezzoAbbonamentoMensile(struttura.getPrezzoAbbonamentoMensile());
        s.setPrezzoPrenotazione(struttura.getPrezzoPrenotazione());
        return strutturaRepository.save(s);
    }


    


}
