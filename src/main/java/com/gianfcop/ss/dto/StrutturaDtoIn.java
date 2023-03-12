package com.gianfcop.ss.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StrutturaDtoIn {

    @Positive(message = "structured id must be an integer positive number")
    private int id;
    @NotBlank(message = "structure description must not be null and must to contain at least one non-whitespace character")
    @Size(max = 255, message = "structure description must containt a maximum number of 255 characters")
    private String descrizione;
    @Positive(message = "booking price must be an integer positive number")
    private int prezzoPrenotazione;
    @Positive(message = "subscription price must be an integer positive number")
    private int prezzoAbbonamentoMensile;
}
