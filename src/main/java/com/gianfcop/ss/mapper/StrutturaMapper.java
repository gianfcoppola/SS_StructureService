package com.gianfcop.ss.mapper;

import com.gianfcop.ss.dto.DatiStruttura1;
import com.gianfcop.ss.dto.DatiStruttura2;
import com.gianfcop.ss.dto.StrutturaDtoOut;
import com.gianfcop.ss.dto.StrutturaUpdateDTO;
import com.gianfcop.ss.model.CentroSportivo;
import com.gianfcop.ss.model.Struttura;

public class StrutturaMapper {

    private StrutturaMapper() {
        throw new IllegalStateException("Utility class");
    }
    
    public static StrutturaDtoOut toStrutturaDtoOut(Struttura struttura){
        return StrutturaDtoOut.builder()
            .id(struttura.getId())
            .descrizione(struttura.getDescrizione())
            .build();
    }

    public static DatiStruttura1 toDatiStruttura1(CentroSportivo centroSportivo, Struttura struttura){
        
        DatiStruttura1 datiStruttura1 = new DatiStruttura1();
        int idStruttura = struttura.getId();
        if(idStruttura == 1){
            datiStruttura1.setNumeroPrenotazioni(centroSportivo.getNumeroPrenotazioniCalcio());
            datiStruttura1.setIncassiTotali(centroSportivo.getIncassiCalcio());
        }
        else{
            datiStruttura1.setNumeroPrenotazioni(centroSportivo.getNumeroPrenotazioniTennis());
            datiStruttura1.setIncassiTotali(centroSportivo.getIncassiTennis());
        }

        return datiStruttura1;
        
    }

    public static DatiStruttura2 toDatiStruttura2(CentroSportivo centroSportivo, Struttura struttura){
        
        DatiStruttura2 datiStruttura2 = new DatiStruttura2();
        int idStruttura = struttura.getId();
        if(idStruttura == 3){
            datiStruttura2.setNumeroPrenotazioni(centroSportivo.getNumeroPrenotazioniPiscina());
            datiStruttura2.setNumeroAbbonamenti(centroSportivo.getNumeroAbbonamentiPiscina());
            datiStruttura2.setIncassiTotali(centroSportivo.getIncassiPiscina());
        }
        else{
            datiStruttura2.setNumeroPrenotazioni(centroSportivo.getNumeroPrenotazioniPalestra());
            datiStruttura2.setNumeroAbbonamenti(centroSportivo.getNumeroAbbonamentiPalestra());
            datiStruttura2.setIncassiTotali(centroSportivo.getIncassiPalestra());
        }

        return datiStruttura2;
        
    }

    public static StrutturaUpdateDTO toStrutturaUpdateDTO(Struttura struttura){
        String prezzoAbbonamentoMensile;
        if(struttura.getPrezzoAbbonamentoMensile() == 0)
            prezzoAbbonamentoMensile = "-";
        else
            prezzoAbbonamentoMensile = String.valueOf(struttura.getPrezzoAbbonamentoMensile());
        return StrutturaUpdateDTO.builder()
            .id(struttura.getId())
            .descrizione(struttura.getDescrizione())
            .prezzoPrenotazione(struttura.getPrezzoPrenotazione())
            .prezzoAbbonamentoMensile(prezzoAbbonamentoMensile)
            .build();
    }
    
}
