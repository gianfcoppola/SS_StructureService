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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gianfcop.ss.dto.StrutturaDtoIn;
import com.gianfcop.ss.dto.StrutturaDtoOut;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.security.SecurityUtil;
import com.gianfcop.ss.service.StrutturaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;


@RequestMapping("/strutture")
@Controller
@Validated
public class StrutturaController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;
    
    private StrutturaService strutturaService;

    public StrutturaController(StrutturaService strutturaService) {
        this.strutturaService = strutturaService;
    }

    @GetMapping("/info")
    public ResponseEntity<List<StrutturaDtoOut>> getInfoStrutture(){
        return new ResponseEntity<List<StrutturaDtoOut>>(strutturaService.getAllStruttureDtoOut(), HttpStatus.OK);
    }

    @GetMapping("/nomi")
    public ResponseEntity<List<String>> getNomiStrutture(){
        return new ResponseEntity<List<String>>(strutturaService.getAllNomiStrutture(), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertStruttura(@RequestBody @Valid StrutturaDtoIn strutturaDtoIn){
        return new ResponseEntity<>("Struttura " + strutturaService.insertStruttura(strutturaDtoIn).getId() + " creata correttamente", HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Struttura>> getAllStrutture(){
        return new ResponseEntity<List<Struttura>>(strutturaService.getAllStrutture(), HttpStatus.OK) ;
    }

    /* 
    @GetMapping("/tipo-struttura/{tipo}")
    public ResponseEntity<List<Struttura>> getAllStruttureBySport(@PathVariable("tipo") int tipo){
        return new ResponseEntity<List<Struttura>>(strutturaService.getStruttureByTipoStruttura(tipo), HttpStatus.OK) ;
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<Struttura> getStrutturaById(@PathVariable("id") @Positive @Min(1) @Max(4) int id){
        return new ResponseEntity<Struttura>(strutturaService.getStrutturaById(id), HttpStatus.OK) ;
    }

    /* 
    @PutMapping("/{id}")
    public ResponseEntity<Struttura> modificaStrutturaById(@PathVariable("id") int idStruttura, @RequestBody StrutturaDtoIn strutturaDtoIn){
        return new ResponseEntity<Struttura>(strutturaService.updateStruttura(idStruttura, strutturaDtoIn), HttpStatus.OK) ;
    }
    */

    @GetMapping("/nome/{id}")
    public ResponseEntity<String> idStrutturaToNome(@PathVariable("id") @Positive @Min(1) @Max(4) int idStruttura){
        return new ResponseEntity<String>(strutturaService.getStrutturaById(idStruttura).getDescrizione(), HttpStatus.OK);
    }

    @GetMapping("/dati")
    public String loadDatiStrutture(Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException{

        if (!SecurityUtil.checkScope(jwt, model, request, response))
            return "index";
            
        model.addAttribute("strutture", strutturaService.getDatiStrutture());
        return "dati-strutture";
    }

    @GetMapping("/edit/{id}")
	public String modificaDatiStruttura(@PathVariable("id") @Positive @Min(1) @Max(4) int id, Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException {

        if (!SecurityUtil.checkScope(jwt, model, request, response))
            return "index";

        model.addAttribute("struttura", strutturaService.getStrutturaById(id));
        return "modifica-struttura";
        
	}



    @PostMapping("/edit/{id}")
	public String updateStruttura(@PathVariable("id") @Positive @Min(1) @Max(4) int id, 
        @ModelAttribute("struttura") @Valid Struttura struttura, Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException {

        if (!SecurityUtil.checkScope(jwt, model, request, response))
            return "index";

        strutturaService.modificaStruttura(id, struttura);
        model.addAttribute("strutturaModificata", "1");
        model.addAttribute("strutture", strutturaService.getDatiStrutture());
        return "dati-strutture";
        
	}




}
