package com.gianfcop.ss.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gianfcop.ss.dto.DatiAbbonamentoDTO;
import com.gianfcop.ss.dto.DatiStruttura2;
import com.gianfcop.ss.dto.IncassiDTO;
import com.gianfcop.ss.dto.NumeroAbbonamentiDTO;
import com.gianfcop.ss.dto.NumeroPrenotazioniDTO;
import com.gianfcop.ss.security.SecurityUtil;
import com.gianfcop.ss.service.CentroSportivoService;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

@Controller
@Validated
public class CentroSportivoController {


    @Autowired
    private CentroSportivoService centroSportivoService;

    @Autowired
    private SecurityUtil securityUtil;

    private static final String INDEX_PAGE = "index";
    private static final String PISCINA_STRING = "piscina";
    private static final String PALESTRA_STRING = "palestra";
    private static final String CHARDATA_STRING = "chartData";
    private static final String DATE_FORMAT_STRING = "dd-MM-yyyy";

    @GetMapping("/index")
    public String homePage(){
        return INDEX_PAGE;
    }

    @GetMapping("/centro-sportivo/prenotazioni/data-analysis")
    public String analisiDatiPrenotazioni(Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException{
       
        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;

        NumeroPrenotazioniDTO numeroPrenotazioniDTO = centroSportivoService.getDatiPrenotazioni();
        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("calcio", numeroPrenotazioniDTO.getNumeroPrenotazioniCalcio());
        graphData.put("tennis", numeroPrenotazioniDTO.getNumeroPrenotazioniTennis());
        graphData.put(PISCINA_STRING, numeroPrenotazioniDTO.getNumeroPrenotazioniPiscina());
        graphData.put(PALESTRA_STRING, numeroPrenotazioniDTO.getNumeroPrenotazioniPalestra());
        model.addAttribute(CHARDATA_STRING, graphData);

        String oggi = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_STRING));
        model.addAttribute("oggi", oggi);

        int numeroPrenotazioni = numeroPrenotazioniDTO.getNumeroPrenotazioniCalcio()
            + numeroPrenotazioniDTO.getNumeroPrenotazioniTennis() 
            + numeroPrenotazioniDTO.getNumeroPrenotazioniPiscina() 
            + numeroPrenotazioniDTO.getNumeroPrenotazioniPalestra();
        model.addAttribute("numeroPrenotazioni", numeroPrenotazioni);

        return "statistiche-prenotazioni";
    }

    @GetMapping("/centro-sportivo/abbonamenti/data-analysis")
    public String analisiDatiAbbonamenti(Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException{

        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;

        NumeroAbbonamentiDTO numeroAbbonamentiDTO = centroSportivoService.getDatiAbbonamenti();
        Map<String, Integer> graphData = new TreeMap<>();
        
        graphData.put(PISCINA_STRING, numeroAbbonamentiDTO.getNumeroAbbonamentiPiscina());
        graphData.put(PALESTRA_STRING, numeroAbbonamentiDTO.getNumeroAbbonamentiPalestra());
        model.addAttribute(CHARDATA_STRING, graphData);
        String oggi = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_STRING));
        model.addAttribute("oggi", oggi);

        int numeroAbbonamenti = numeroAbbonamentiDTO.getNumeroAbbonamentiPalestra() + numeroAbbonamentiDTO.getNumeroAbbonamentiPiscina();
        model.addAttribute("numeroAbbonamenti", numeroAbbonamenti);

        return "statistiche-abbonamenti";
    }

    @GetMapping("/centro-sportivo/incassi/data-analysis")
    public String analisiIncassiCentroSportivo(Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException{

        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;

        IncassiDTO incassiDTO = centroSportivoService.getIncassiCentroSportivo();
        Map<String, Integer> graphData = new TreeMap<>();
        
        graphData.put("calcio", incassiDTO.getIncassiCalcio());
        graphData.put("tennis", incassiDTO.getIncassiTennis());
        graphData.put(PISCINA_STRING, incassiDTO.getIncassiPiscina());
        graphData.put(PALESTRA_STRING, incassiDTO.getIncassiPalestra());
        model.addAttribute(CHARDATA_STRING, graphData);
        String oggi = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT_STRING));
        model.addAttribute("oggi", oggi);
        int incassiTotali = incassiDTO.getIncassiCalcio() + incassiDTO.getIncassiTennis() + incassiDTO.getIncassiPiscina() + incassiDTO.getIncassiPalestra();
        model.addAttribute("sommaIncassiTotali", incassiTotali);
        return "statistiche-incassi";
    }


    @PutMapping("/centro-sportivo/nuova-prenotazione/{idStruttura}")
    public ResponseEntity<?> nuovaPrenotazione(@PathVariable("idStruttura") @Positive(message = "idStruttura must be a positive number") @Max(4) int idStruttura){

        if(idStruttura == 1 || idStruttura == 2)
            return new ResponseEntity<>(centroSportivoService.aggiornaDatiStruttura1(idStruttura), HttpStatus.OK);
        else
            return new ResponseEntity<>(centroSportivoService.aggiornaDatiStruttura2(idStruttura), HttpStatus.OK);

    }

    @PutMapping("/centro-sportivo/nuovo-abbonamento")
    public ResponseEntity<DatiStruttura2> nuovoAbbonamento(@RequestBody @Valid DatiAbbonamentoDTO datiAbbonamento){

        return new ResponseEntity<>(centroSportivoService.effettuaAbbonamento(datiAbbonamento.getIdStruttura(), datiAbbonamento.getNumeroMesiAbbonamento()), HttpStatus.OK);
    }
    

    
}
