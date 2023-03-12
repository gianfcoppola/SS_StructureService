package com.gianfcop.ss.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CentroSportivo")
public class CentroSportivo {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //prenotazioni: calcio + tennis
    private int numeroPrenotazioniCalcio;
    private int numeroPrenotazioniTennis;
    private int numeroPrenotazioniPalestra;
    private int numeroPrenotazioniPiscina;
    //abbonamenti: piscina + tennis
    private int numeroAbbonamentiPiscina;   
    private int numeroAbbonamentiPalestra;
    
    private int incassiCalcio;
    private int incassiTennis;
    private int incassiPalestra;
    private int incassiPiscina;

    

}
