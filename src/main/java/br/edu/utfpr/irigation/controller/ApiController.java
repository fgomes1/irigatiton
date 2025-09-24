package br.edu.utfpr.irigation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@Tag(name = "API", description = "Endpoints para perfil e autenticação")
public class ApiController {

    @Operation(summary = "Obter perfil do usuário", description = "Retorna as informações do perfil do usuário autenticado.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil do usuário recuperado com sucesso",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        // Obtém as claims do token que foram definidas pelo interceptor
        Object cognitoUser = request.getAttribute("cognitoUser");
        
        return ResponseEntity.ok()
                .body("Perfil do usuário autenticado. Claims: " + cognitoUser);
    }

    @Operation(summary = "Endpoint protegido", description = "Endpoint de teste para verificar autenticação JWT.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Acesso permitido",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "Não autorizado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/protected")
    public ResponseEntity<?> protectedEndpoint() {
        return ResponseEntity.ok("Este é um endpoint protegido por JWT!");
    }
}
