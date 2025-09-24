package br.edu.utfpr.irigation.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration

// Configurações gerais da API
@OpenAPIDefinition(
  info = @Info(
    title = "Irrigation System API",
    version = "1.0.0",
    contact = @Contact(
      name = "Franciney", 
      email = "franciney@example.com"
    ),
    license = @License(
      name = "Apache 2.0", 
      url = "https://www.apache.org/licenses/LICENSE-2.0"
    ),
    description = "API para sistema de irrigação com autenticação JWT via AWS Cognito"
  ),
  servers = {
    @Server(
      url = "http://localhost:8081",
      description = "Development Server"
    )
  }
)

// Configurações de autenticação JWT
@SecurityScheme(
  name = "Authorization",
  type = SecuritySchemeType.HTTP,
  bearerFormat = "JWT",
  scheme = "bearer"
)
public class OpenAPIConfiguration {
}
