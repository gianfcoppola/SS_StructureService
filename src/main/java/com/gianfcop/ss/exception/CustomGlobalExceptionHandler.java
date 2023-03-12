package com.gianfcop.ss.exception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gianfcop.ss.service.StrutturaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomGlobalExceptionHandler {

    @Autowired
    private StrutturaService strutturaService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler(BindException.class)
    public String handleBindException(BindException ex, HttpServletRequest request,
            Model model, @AuthenticationPrincipal Jwt jwt) throws ServletException {

        log.error(ex.getMessage());

        String idUtente = jwt.getClaims().get("sub").toString();
        String nomeUtente = jwt.getClaimAsString("name");
        model.addAttribute("nomeUtente", nomeUtente);
        model.addAttribute("idUtente", idUtente);

        String returnPage = "index";
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/strutture/edit")) {

            model.addAttribute("strutture", strutturaService.getDatiStrutture());
            model.addAttribute("badRequest", "1");
            returnPage = "dati-strutture";
        } 

        return returnPage;

    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex) throws ServletException {

        logoutProcedure();

        return "index";

    }

    private void logoutProcedure() throws ServletException {

        request.logout(); // Invalida l'autenticazione corrente
        SecurityContextHolder.getContext().setAuthentication(null); // Elimina l'autenticazione corrente
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida la sessione
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0); // Elimina i cookie
                cookie.setValue(null);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

    }


}
