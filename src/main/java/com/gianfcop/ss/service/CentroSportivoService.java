package com.gianfcop.ss.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gianfcop.ss.dto.DatiStruttura1;
import com.gianfcop.ss.dto.DatiStruttura2;
import com.gianfcop.ss.dto.IncassiDTO;
import com.gianfcop.ss.dto.NumeroAbbonamentiDTO;
import com.gianfcop.ss.dto.NumeroPrenotazioniDTO;
import com.gianfcop.ss.mapper.CentroSportivoMapper;
import com.gianfcop.ss.mapper.StrutturaMapper;
import com.gianfcop.ss.model.CentroSportivo;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.repository.CentroSportivoRepository;
import com.gianfcop.ss.repository.StrutturaRepository;

@Service
public class CentroSportivoService {
    
    @Autowired
    private CentroSportivoRepository centroSportivoRepository;

    @Autowired
    private StrutturaRepository strutturaRepository;

    private static final String CENTRO_SPORTIVO_404_STRING = "Il centro sportivo non esiste";
    private static final String STRUTTURA_404_STRING = "La struttura richiesta non esiste";


      //NUOVA PRENOTAZIONE
    public DatiStruttura1 aggiornaDatiStruttura1(int idStruttura){

        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));

        Struttura struttura = strutturaRepository.findById(idStruttura)
            .orElseThrow(() -> new NoSuchElementException(STRUTTURA_404_STRING));

        if(idStruttura == 1){
            centroSportivo.setIncassiCalcio(centroSportivo.getIncassiCalcio() + struttura.getPrezzoPrenotazione());
            centroSportivo.setNumeroPrenotazioniCalcio(centroSportivo.getNumeroPrenotazioniCalcio() + 1);
        }
        else{
            centroSportivo.setIncassiTennis(centroSportivo.getIncassiTennis() + struttura.getPrezzoPrenotazione());
            centroSportivo.setNumeroPrenotazioniTennis(centroSportivo.getNumeroPrenotazioniTennis() + 1);
        }

        centroSportivoRepository.save(centroSportivo);
        return StrutturaMapper.toDatiStruttura1(centroSportivo, struttura);

    }

    //NUOVO ABBONAMENTO
    public DatiStruttura2 effettuaAbbonamento(int idStruttura, int numeroMesiAbbonamento){
        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));

        Struttura struttura = strutturaRepository.findById(idStruttura)
            .orElseThrow(() -> new NoSuchElementException(STRUTTURA_404_STRING));

        int prezzoAbbonamento = struttura.getPrezzoAbbonamentoMensile() * numeroMesiAbbonamento;

        
        if(idStruttura == 3){
            centroSportivo.setIncassiPiscina(centroSportivo.getIncassiPiscina() + prezzoAbbonamento);
            centroSportivo.setNumeroAbbonamentiPiscina(centroSportivo.getNumeroAbbonamentiPiscina() + 1);
        }
        else{
            centroSportivo.setIncassiPalestra(centroSportivo.getIncassiPalestra() + prezzoAbbonamento);
            centroSportivo.setNumeroAbbonamentiPalestra(centroSportivo.getNumeroAbbonamentiPalestra() + 1);
        }

        centroSportivoRepository.save(centroSportivo);

        return StrutturaMapper.toDatiStruttura2(centroSportivo, struttura);
    }


    //NUOVA PRENOTAZIONE
    public DatiStruttura2 aggiornaDatiStruttura2(int idStruttura){

        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));

        Struttura struttura = strutturaRepository.findById(idStruttura)
            .orElseThrow(() -> new NoSuchElementException(STRUTTURA_404_STRING));

        if(idStruttura == 3){
            centroSportivo.setNumeroPrenotazioniPiscina(centroSportivo.getNumeroPrenotazioniPiscina() +1);
        }
        else{
            centroSportivo.setNumeroPrenotazioniPalestra(centroSportivo.getNumeroPrenotazioniPalestra() + 1);
        }

        centroSportivoRepository.save(centroSportivo);
        return StrutturaMapper.toDatiStruttura2(centroSportivo, struttura);

    }


    public CentroSportivo creaCentroSportivo(CentroSportivo centroSportivo){
        return centroSportivoRepository.save(centroSportivo);
    }


    public NumeroPrenotazioniDTO getDatiPrenotazioni(){
        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));

        return CentroSportivoMapper.toNumeroPrenotazioniDTO(centroSportivo);
    }

    public NumeroAbbonamentiDTO getDatiAbbonamenti(){
        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));
        return CentroSportivoMapper.toNumeroAbbonamentiDTO(centroSportivo);
    }

    public IncassiDTO getIncassiCentroSportivo(){
        CentroSportivo centroSportivo = centroSportivoRepository.findById(1)
            .orElseThrow(() -> new NoSuchElementException(CENTRO_SPORTIVO_404_STRING));
        return CentroSportivoMapper.toIncassiDTO(centroSportivo);
    }

}
