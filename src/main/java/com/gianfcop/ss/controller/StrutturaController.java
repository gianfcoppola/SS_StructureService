package com.gianfcop.ss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gianfcop.ss.dto.StrutturaDtoOut;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.security.SecurityUtil;
import com.gianfcop.ss.service.StrutturaService;

import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


@RequestMapping("/strutture")
@Controller
@Validated
public class StrutturaController {

    private static final String INDEX_PAGE = "index";

    @Autowired
    private SecurityUtil securityUtil;
    
    private StrutturaService strutturaService;

    public StrutturaController(StrutturaService strutturaService) {
        this.strutturaService = strutturaService;
    }

    @GetMapping("/info")
    public ResponseEntity<List<StrutturaDtoOut>> getInfoStrutture(){
        return new ResponseEntity<>(strutturaService.getAllStruttureDtoOut(), HttpStatus.OK);
    }

    @GetMapping("/nomi")
    public ResponseEntity<List<String>> getNomiStrutture(){
        return new ResponseEntity<>(strutturaService.getAllNomiStrutture(), HttpStatus.OK);
    }

    @GetMapping("/nome/{id}")
    public ResponseEntity<String> idStrutturaToNome(@PathVariable("id") @Positive @Min(1) @Max(4) int idStruttura){
        return new ResponseEntity<>(strutturaService.getStrutturaById(idStruttura).getDescrizione(), HttpStatus.OK);
    }

    @GetMapping("/dati")
    public String loadDatiStrutture(Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException{

        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;
            
        model.addAttribute("strutture", strutturaService.getDatiStrutture());
        return "dati-strutture";
    }

    @GetMapping("/edit/{id}")
	public String modificaDatiStruttura(@PathVariable("id") @Positive @Min(1) @Max(4) int id, Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException {

        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;

        model.addAttribute("struttura", strutturaService.getStrutturaById(id));
        return "modifica-struttura";
        
	}



    @PostMapping("/edit/{id}")
	public String updateStruttura(@PathVariable("id") @Positive @Min(1) @Max(4) int id, 
        @ModelAttribute("struttura") @Valid Struttura struttura, Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException {

        if (!securityUtil.checkScope(jwt, model))
            return INDEX_PAGE;

        strutturaService.modificaStruttura(id, struttura);
        model.addAttribute("strutturaModificata", "1");
        model.addAttribute("strutture", strutturaService.getDatiStrutture());
        return "dati-strutture";
        
	}




}
