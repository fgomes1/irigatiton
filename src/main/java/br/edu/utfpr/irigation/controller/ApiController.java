package br.edu.utfpr.irigation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        // Obtém as claims do token que foram definidas pelo interceptor
        Object cognitoUser = request.getAttribute("cognitoUser");
        
        return ResponseEntity.ok()
                .body("Perfil do usuário autenticado. Claims: " + cognitoUser);
    }

    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("Este é um endpoint protegido por JWT!");
    }
}
