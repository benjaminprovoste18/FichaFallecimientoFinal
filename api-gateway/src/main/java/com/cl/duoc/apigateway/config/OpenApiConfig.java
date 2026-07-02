package com.cl.duoc.apigateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Sistema de Ficha Social para Personas Fallecidas")
                .version("1.0")
                .description("API Gateway que proporciona acceso a los microservicios del sistema de gestión de fichas sociales para personas fallecidas. "
                    + "Este sistema integra múltiples servicios especializados en personas, finanzas, familia, acceso, fichas sociales, bienes, "
                    + "defunciones, documentos, beneficios y deudas.")
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("desarrollo@duoc.cl")
                    .url("https://www.duoc.cl")));
    }
}
