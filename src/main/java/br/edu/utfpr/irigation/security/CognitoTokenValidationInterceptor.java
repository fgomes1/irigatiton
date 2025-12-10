package br.edu.utfpr.irigation.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CognitoTokenValidationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CognitoTokenValidationInterceptor.class);
    private static final String SECRET_KEY = "local-secret-key"; 

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Permitir solicitações OPTIONS (preflight CORS) sem token
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                DecodedJWT jwt = JWT.decode(token);
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
                algorithm.verify(jwt);
                
                request.setAttribute("user", jwt.getClaims());
                return true;
                
            } catch (JWTVerificationException e) {
                logger.error("Token JWT inválido: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Erro ao validar token: {}", e.getMessage());
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"error\": \"Token inv\u00e1lido ou ausente\"}");
        response.setContentType("application/json");
        return false;
    }
}
