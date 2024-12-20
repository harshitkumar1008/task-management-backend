package com.harshitkumar.task_management_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    public SwaggerConfig() {
    }

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = (new SecurityScheme()).type(Type.HTTP).scheme("Bearer").bearerFormat("JWT");
        SecurityRequirement securityRequirement = (new SecurityRequirement()).addList("bearerAuth");
        return (new OpenAPI()).info((new Info()).title("Task Application API").version("1.0").description("API documentation for the Task Management Application")).addSecurityItem(securityRequirement).components((new Components()).addSecuritySchemes("bearerAuth", securityScheme));
    }
}

