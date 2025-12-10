package br.edu.utfpr.irigation.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.edu.utfpr.irigation.dto.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import br.edu.utfpr.irigation.model.Usuario;
import br.edu.utfpr.irigation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints para gerenciar autenticação de usuários")

public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private static final String SECRET_KEY = "local-secret-key"; 

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Operation(summary = "Login de usuário", description = "Realiza a autenticação do usuário localmente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Tentativa de login local
            List<Usuario> users = usuarioRepository.findByEmail(authRequest.getUsername());
            Usuario user = null;
            if (!users.isEmpty()) {
                user = users.get(0);
                logger.info("Usuário encontrado por email: {}", user.getEmail());
            } else {
                 users = usuarioRepository.findByNome(authRequest.getUsername());
                 if (!users.isEmpty()) {
                     user = users.get(0);
                     logger.info("Usuário encontrado por nome: {}", user.getNome());
                 } else {
                     logger.warn("Usuário não encontrado: {}", authRequest.getUsername());
                 }
            }

            if (user != null) {
                if (user.getSenha().equals(authRequest.getPassword())) {
                    logger.info("Login com sucesso para: {}", user.getEmail());
                    String token = JWT.create()
                            .withSubject(user.getId().toString())
                            .withClaim("username", user.getNome())
                            .withClaim("email", user.getEmail())
                            .withIssuer("local-auth")
                            .sign(Algorithm.HMAC256(SECRET_KEY));

                    Map<String, Object> result = new HashMap<>();
                    Map<String, String> authResult = new HashMap<>();
                    authResult.put("AccessToken", token);
                    authResult.put("IdToken", token); // Reutilizando o mesmo token
                    authResult.put("RefreshToken", "local-refresh-token-not-implemented");
                    result.put("AuthenticationResult", authResult);

                    return ResponseEntity.ok(result);
                } else {
                    logger.warn("Senha incorreta para usuário: {}", user.getEmail());
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuário ou senha inválidos"));

        } catch (Exception e) {
            logger.error("Erro no login: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @Operation(summary = "Validar token", description = "Valida um token JWT local.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token válido",
                content = @Content(schema = @Schema(implementation = Map.class))),
        @ApiResponse(responseCode = "401", description = "Token inválido"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                DecodedJWT jwt = JWT.decode(token);
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                algorithm.verify(jwt);

                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("claims", jwt.getClaims());
                return ResponseEntity.ok(response);

            } catch (JWTVerificationException e) {
                logger.error("Token JWT inválido: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Erro ao validar token: {}", e.getMessage());
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("valid", false, "message", "Token inválido"));
    }

    @Operation(summary = "Renovar token", description = "Renova um token de acesso (Simulado para local).")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> payload) {
       // Refresh token simplifies to not implemented or just return ok for now as we don't handle expiration strictly yet
       return ResponseEntity.ok(Map.of("message", "Refresh não implementado para auth local simples"));
    }
}
