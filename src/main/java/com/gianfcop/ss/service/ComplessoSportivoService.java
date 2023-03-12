package com.gianfcop.ss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gianfcop.ss.dto.DatiStruttura1;
import com.gianfcop.ss.dto.DatiStruttura2;
import com.gianfcop.ss.mapper.StrutturaMapper;
import com.gianfcop.ss.model.CentroSportivo;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.repository.CentroSportivoRepository;
import com.gianfcop.ss.repository.StrutturaRepository;

@Service
public class ComplessoSportivoService {
    
    @Autowired
    private CentroSportivoRepository centroSportivoRepository;

    @Autowired
    private StrutturaRepository strutturaRepository;


      //NUOVA PRENOTAZIONE
    public DatiStruttura1 aggiornaDatiStruttura1(int idStruttura){

        CentroSportivo centroSportivo = centroSportivoRepository.findById(1).get();

        Struttura struttura = strutturaRepository.findById(idStruttura).get();

        if(idStruttura == 1){
            centroSportivo.setIncassiCalcio(centroSportivo.getIncassiCalcio() + struttura.getPrezzoPrenotazione());
            centroSportivo.setNumeroPrenotazioniCalcio(centroSportivo.getNumeroPrenotazioniCalcio() + 1);
        }
        else{
            centroSportivo.setIncassiTennis(centroSportivo.getIncassiTennis() + struttura.getPrezzoPrenotazione());
            centroSportivo.setNumeroPrenotazioniTennis(centroSportivo.getNumeroPrenotazioniTennis() + 1);
        }

        return StrutturaMapper.toDatiStruttura1(centroSportivo, struttura);

    }

  //NUOVO ABBONAMENTO
    public DatiStruttura2 effettuaAbbonamento(int idStruttura, int numeroMesiAbbonamento){
        CentroSportivo centroSportivo = centroSportivoRepository.findById(1).get();

        Struttura struttura = strutturaRepository.findById(idStruttura).get();

        int prezzoAbbonamento = struttura.getPrezzoAbbonamentoMensile() * numeroMesiAbbonamento;

        
        if(idStruttura == 3){
            centroSportivo.setIncassiPiscina(centroSportivo.getIncassiPiscina() + prezzoAbbonamento);
            centroSportivo.setNumeroAbbonamentiPiscina(centroSportivo.getNumeroAbbonamentiPiscina() + 1);
        }
        else{
            centroSportivo.setIncassiPalestra(centroSportivo.getIncassiPalestra() + prezzoAbbonamento);
            centroSportivo.setNumeroAbbonamentiPalestra(centroSportivo.getNumeroAbbonamentiPalestra() + 1);
        }

        return StrutturaMapper.toDatiStruttura2(centroSportivo, struttura);
    }


    //NUOVA PRENOTAZIONE
    public DatiStruttura2 aggiornaDatiStruttura2(int idStruttura){

        CentroSportivo centroSportivo = centroSportivoRepository.findById(1).get();

        Struttura struttura = strutturaRepository.findById(idStruttura).get();


        if(idStruttura == 3){
            centroSportivo.setNumeroPrenotazioniPiscina(centroSportivo.getNumeroPrenotazioniPiscina() +1);
        }
        else{
            centroSportivo.setNumeroPrenotazioniPalestra(centroSportivo.getNumeroPrenotazioniPalestra() + 1);
        }

        return StrutturaMapper.toDatiStruttura2(centroSportivo, struttura);

    }
}
