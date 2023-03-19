package com.gianfcop.ss.controller;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gianfcop.ss.dto.DatiAbbonamentoDTO;
import com.gianfcop.ss.security.SecurityUtil;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class CentroSportivoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityUtil securityUtil;


    @Test
    public void analisiDatiPrenotazioni_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/prenotazioni/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("statistiche-prenotazioni"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeExists("oggi"))
                .andExpect(model().attribute("oggi", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(model().attributeExists("numeroPrenotazioni"))
                .andExpect(model().attributeExists("chartData"))
                .andReturn();
    }

    @Test
    public void analisiDatiPrenotazioni_401() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/prenotazioni/data-analysis");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void analisiDatiPrenotazioni_403_indexPage() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/prenotazioni/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeDoesNotExist("oggi"))
                .andExpect(model().attributeDoesNotExist("numeroPrenotazioni"))
                .andExpect(model().attributeDoesNotExist("charData"))
                .andReturn();
    }


    @Test
    public void analisiDatiAbbonamenti_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/abbonamenti/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("statistiche-abbonamenti"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeExists("oggi"))
                .andExpect(model().attribute("oggi", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(model().attributeExists("numeroAbbonamenti"))
                .andExpect(model().attributeExists("chartData"))
                .andReturn();
    }

    @Test
    public void analisiDatiAbbonamenti_401() throws Exception {

       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/abbonamenti/data-analysis");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void analisiDatiAbbonamenti_403() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(false);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/abbonamenti/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeDoesNotExist("oggi"))
                .andExpect(model().attributeDoesNotExist("numeroAbbonamenti"))
                .andExpect(model().attributeDoesNotExist("chartData"))
                .andReturn();
    }

    @Test
    public void analisiIncassiCentroSportivo_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/incassi/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("statistiche-incassi"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeExists("oggi"))
                .andExpect(model().attribute("oggi", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
                .andExpect(model().attributeExists("sommaIncassiTotali"))
                .andExpect(model().attributeExists("chartData"))
                .andReturn();
    }

    @Test
    public void analisiIncassiCentroSportivo_401() throws Exception {

       MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/incassi/data-analysis");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void analisiIncassiCentroSportivo_403() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(false);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/centro-sportivo/incassi/data-analysis")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeDoesNotExist("idUtente"))
                .andExpect(model().attributeDoesNotExist("oggi"))
                .andExpect(model().attributeDoesNotExist("sommaIncassiTotali"))
                .andExpect(model().attributeDoesNotExist("chartData"))
                .andReturn();
    }

    @Test @WithMockUser
    public void nuovaPrenotazione_200() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuova-prenotazione/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.incassiTotali").exists())
                .andExpect(jsonPath("$.numeroPrenotazioni").exists())
                .andReturn();
    }


    @Test
    public void nuovaPrenotazione_401() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuova-prenotazione/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @WithMockUser
    public void nuovaPrenotazione_403() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuova-prenotazione/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test @WithMockUser
    public void nuovoAbbonamento_200() throws Exception {

        DatiAbbonamentoDTO datiAbbonamentoDTO = new DatiAbbonamentoDTO();
        datiAbbonamentoDTO.setIdStruttura(3);
        datiAbbonamentoDTO.setNumeroMesiAbbonamento(4);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuovo-abbonamento")
                .content(new ObjectMapper().writeValueAsString(datiAbbonamentoDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$.incassiTotali").exists())
                .andExpect(jsonPath("$.numeroPrenotazioni").exists())
                .andExpect(jsonPath("$.numeroAbbonamenti").exists())
                .andReturn();
    }

    @Test @WithMockUser
    public void nuovoAbbonamento_400() throws Exception {

        DatiAbbonamentoDTO datiAbbonamentoDTO = new DatiAbbonamentoDTO();
        datiAbbonamentoDTO.setIdStruttura(5);
        datiAbbonamentoDTO.setNumeroMesiAbbonamento(-1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuovo-abbonamento")
                .content(new ObjectMapper().writeValueAsString(datiAbbonamentoDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void nuovoAbbonamento_401() throws Exception {

        DatiAbbonamentoDTO datiAbbonamentoDTO = new DatiAbbonamentoDTO();
        datiAbbonamentoDTO.setIdStruttura(3);
        datiAbbonamentoDTO.setNumeroMesiAbbonamento(4);
        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuovo-abbonamento")
                .content(new ObjectMapper().writeValueAsString(datiAbbonamentoDTO))
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test @WithMockUser
    public void nuovoAbbonamento_403() throws Exception {

        DatiAbbonamentoDTO datiAbbonamentoDTO = new DatiAbbonamentoDTO();
        datiAbbonamentoDTO.setIdStruttura(3);
        datiAbbonamentoDTO.setNumeroMesiAbbonamento(4);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/centro-sportivo/nuovo-abbonamento")
                .content(new ObjectMapper().writeValueAsString(datiAbbonamentoDTO));

        mockMvc.perform(mockRequest)
                .andExpect(status().isForbidden())
                .andReturn();
    }

    
    

}
