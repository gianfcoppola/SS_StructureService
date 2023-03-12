package com.gianfcop.ss.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Strutture")
public class Struttura {

    @Id
    @Positive
    private int id;
    @NotBlank(message = "descrizione obbligatoria") @Size(max = 255, message = "la descrizione potrà contenere al massimo 255 caratteri")
    private String descrizione;
    @Positive @Max(100) @Min(10)
    private int prezzoPrenotazione;
    @Min(0) @Max(200) 
    private int prezzoAbbonamentoMensile;

}
