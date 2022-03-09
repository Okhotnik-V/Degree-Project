package com.degree.cto.security;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/authorize/confirm")
    public String authorize(@CurrentSecurityContext SecurityContext securityContext, HttpServletRequest request, @RequestParam String url) {
        Principal principal = (Principal) securityContext.getAuthentication().getPrincipal();
        KeycloakPrincipal<KeycloakSecurityContext> keycloakPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
        AccessToken accessToken = keycloakPrincipal.getKeycloakSecurityContext().getToken();
        securityService.createProfile(
                accessToken.getGivenName(),
                accessToken.getFamilyName(),
                accessToken.getEmail(),
                String.valueOf(accessToken.getRealmAccess().getRoles()),
                request.getUserPrincipal().getName()
        );
        return "redirect:" + url;
    }
}
