package br.edu.utfpr.irigation.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuração do interceptor para validação de tokens JWT
 * 
 * Este interceptor é aplicado a todos os endpoints que correspondem
 * ao padrão "/api/**" para proteger recursos que exigem autenticação.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CognitoTokenValidationInterceptor cognitoTokenValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cognitoTokenValidationInterceptor)
                .addPathPatterns("/api/**") // Protege endpoints /api/**
                .excludePathPatterns("/auth/**", "/h2-console/**", 
                    "/swagger-ui/**", "/v3/api-docs/**"); // Exclui endpoints de auth, H2 console e Swagger
    }
}
