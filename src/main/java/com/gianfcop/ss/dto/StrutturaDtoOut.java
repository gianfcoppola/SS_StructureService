package com.gianfcop.ss.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StrutturaDtoOut {
    
    private int id;
    private String descrizione;
}
