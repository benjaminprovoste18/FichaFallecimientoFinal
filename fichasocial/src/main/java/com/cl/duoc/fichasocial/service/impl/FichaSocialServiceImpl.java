package com.cl.duoc.fichasocial.service.impl;

import com.cl.duoc.fichasocial.dto.*;
import com.cl.duoc.fichasocial.service.FichaSocialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FichaSocialServiceImpl implements FichaSocialService {

    private final WebClient.Builder webClientBuilder;

    private static final String PERSONAS_URL = "http://localhost:8081/personas/";
    private static final String FINANZAS_URL = "http://localhost:8082/finanzas/";
    private static final String FAMILIA_URL = "http://localhost:8083/familia/";
    private static final String ACCESO_URL = "http://localhost:8084/acceso/";

    @Override
    public FichaSocialDTO obtenerFichaCompleta(String rut) {
        WebClient personasClient = webClientBuilder.baseUrl(PERSONAS_URL).build();
        WebClient finanzasClient = webClientBuilder.baseUrl(FINANZAS_URL).build();
        WebClient familiaClient = webClientBuilder.baseUrl(FAMILIA_URL).build();
        WebClient accesoClient = webClientBuilder.baseUrl(ACCESO_URL).build();

        Mono<PersonaDTO> personaMono = personasClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(PersonaDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<FinanzaDTO> finanzaMono = finanzasClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(FinanzaDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<FamiliaDTO> familiaMono = familiaClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(FamiliaDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<AccesoDTO> accesoMono = accesoClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(AccesoDTO.class)
                .onErrorResume(e -> Mono.empty());

        return Mono.zip(personaMono.defaultIfEmpty(new PersonaDTO()),
                        finanzaMono.defaultIfEmpty(new FinanzaDTO()),
                        familiaMono.defaultIfEmpty(new FamiliaDTO()),
                        accesoMono.defaultIfEmpty(new AccesoDTO()))
                .map(tuple -> new FichaSocialDTO(
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3(),
                        tuple.getT4()))
                .block();
    }
}
