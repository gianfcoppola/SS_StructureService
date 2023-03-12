package com.gianfcop.ss.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.ui.Model;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
  
  

    public static boolean checkScope(Jwt jwt, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException{
        String roles = jwt.getClaimAsMap("realm_access").get("roles").toString();
        List<String> roleList = Arrays.stream(roles.replace("[","").replace("]", "").replace("\"", "").split(",")).toList();
        
        if(!roleList.contains("admin_centro_sportivo")){
            log.error("ACCESS FORBIDDEN 403");
            model.addAttribute("forbidden", "1");
            return false;
        }
        return true;
    }



    
}
