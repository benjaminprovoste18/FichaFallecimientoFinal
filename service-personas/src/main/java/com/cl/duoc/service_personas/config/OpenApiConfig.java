package com.cl.duoc.service_personas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Ficha Social para Personas Fallecidas - Personas")
                        .description("Microservicio encargado de administrar la información personal de las personas fallecidas dentro del Sistema de Ficha Social.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Alonso Pino, Benjamín Provoste"))
                        .license(new License().name("MIT"))
                        .extensions(Map.of(
                                "x-institucion", new StringSchema().example("Duoc UC"),
                                "x-carrera", new StringSchema().example("Ingeniería en Informática")
                        )));
    }
}
