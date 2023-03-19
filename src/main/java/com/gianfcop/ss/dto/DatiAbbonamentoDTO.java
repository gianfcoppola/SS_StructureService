package com.gianfcop.ss.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DatiAbbonamentoDTO {
    
    @Positive(message = "idStruttura must be an integer positive number") @Min(3) @Max(4)
    private int idStruttura;
    @Positive(message = "numeroMesiAbbonamento must be an integer positive number")
    private int numeroMesiAbbonamento;
}
