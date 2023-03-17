package com.gianfcop.ss.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gianfcop.ss.model.Struttura;
import com.gianfcop.ss.repository.StrutturaRepository;
import com.gianfcop.ss.security.SecurityUtil;
import com.netflix.discovery.EurekaClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@SpringBootTest
@AutoConfigureMockMvc
public class StrutturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SecurityUtil securityUtil;


    @Test @WithMockUser
    public void getInfoStrutture_200() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/info");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].descrizione", is("Campo da calcetto")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].descrizione", is("Campo da tennis")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].descrizione", is("Piscina")))
                .andExpect(jsonPath("$[3].id", is(4)))
                .andExpect(jsonPath("$[3].descrizione", is("Palestra")))
                .andReturn();
    }


    @Test @WithMockUser
    public void getNomiStrutture_200() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/nomi");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0]", is("Campo da calcetto")))
                .andExpect(jsonPath("$[1]", is("Campo da tennis")))
                .andExpect(jsonPath("$[2]", is("Piscina")))
                .andExpect(jsonPath("$[3]", is("Palestra")))
                .andReturn();
    }

    @Test @WithMockUser
    public void idStrutturaToNome_200() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/nome/2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Campo da tennis"))
                .andReturn();
    }


    
    @Test
    public void loadDatiStrutture_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/dati")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("dati-strutture"))
                .andExpect(model().attributeExists("strutture"))
                .andReturn();
    }

    @Test
    public void loadDatiStrutture_401() throws Exception {

        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/dati");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void modificaDatiStruttura_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/edit/1")
                .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(view().name("modifica-struttura"))
                .andExpect(model().attributeExists("struttura"))
                .andExpect(model().attribute("struttura", hasProperty("id", is(1))))
                .andExpect(model().attribute("struttura", hasProperty("descrizione", is("Campo da calcetto"))))
                .andExpect(model().attribute("struttura", hasProperty("prezzoPrenotazione", is(50))))
                .andExpect(model().attribute("struttura", hasProperty("prezzoAbbonamentoMensile", is(0))))
                .andReturn();

    }

    @Test
    public void updateStruttura_200() throws Exception {

        Jwt jwt = Mockito.mock(Jwt.class);
        Mockito.when(jwt.getClaims()).thenReturn(Map.of("sub", "1234", "name", "Mario Rossi"));

        Mockito.when(securityUtil.checkScope(any(Jwt.class), any(Model.class))).thenReturn(true);

        Struttura struttura = new Struttura(1, "Campo da calcetto", 50, 0);
        StrutturaRepository strutturaRepository = Mockito.mock(StrutturaRepository.class);
        Mockito.when(strutturaRepository.findById(1)).thenReturn(Optional.of(struttura));
        Mockito.when(strutturaRepository.save(any(Struttura.class))).thenReturn(struttura);

        
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
            .post("/strutture/edit/1") // Aggiungi l'id della struttura che vuoi modificare
            .param("id", "1")
            .param("descrizione", "Campo da calcetto")
            .param("prezzoPrenotazione", "50")
            .param("prezzoAbbonamentoMensile", "0")
            .with(authentication(new JwtAuthenticationToken(jwt, Collections.emptyList())))
            .with(csrf());

        MvcResult mvcResult = mockMvc.perform(mockRequest)
            .andExpect(status().isOk())
            .andExpect(view().name("dati-strutture"))
            .andExpect(model().attributeExists("strutture"))
            //.andExpect(model().attribute("strutturaModificata", "1"))
            .andReturn();

        mvcResult.getModelAndView();

    }


    /* 
    @Test
    public void getInfoStrutture_401() throws Exception {


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/info");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void getNomiStrutture_401() throws Exception {

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/strutture/nomi");

        mockMvc.perform(mockRequest)
                .andExpect(status().isUnauthorized())
                .andReturn();
    }
    */

    
    
}
