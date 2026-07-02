package com.cl.duoc.service_acceso.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Ficha Social para Personas Fallecidas - Acceso")
                        .description("Microservicio encargado de administrar el acceso seguro a las fichas sociales.")
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
