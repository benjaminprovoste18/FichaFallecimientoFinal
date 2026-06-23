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
        private static final String BIENES_URL = "http://localhost:8086/bienes/rut/";
        private static final String DEFUNCION_URL = "http://localhost:8087/defuncion/rut/";
        private static final String DOCUMENTOS_URL = "http://localhost:8088/documentos/rut/";
        private static final String BENEFICIOS_URL = "http://localhost:8092/beneficios/rut/";
        private static final String DEUDAS_URL = "http://localhost:8093/deudas/rut/";

    @Override
    public FichaSocialDTO obtenerFichaCompleta(String rut) {
        WebClient personasClient = webClientBuilder.baseUrl(PERSONAS_URL).build();
        WebClient finanzasClient = webClientBuilder.baseUrl(FINANZAS_URL).build();
        WebClient familiaClient = webClientBuilder.baseUrl(FAMILIA_URL).build();
        WebClient accesoClient = webClientBuilder.baseUrl(ACCESO_URL).build();
        WebClient bienesClient = webClientBuilder.baseUrl(BIENES_URL).build();
        WebClient defuncionClient = webClientBuilder.baseUrl(DEFUNCION_URL).build();
        WebClient documentosClient = webClientBuilder.baseUrl(DOCUMENTOS_URL).build();
        WebClient beneficiosClient = webClientBuilder.baseUrl(BENEFICIOS_URL).build();
        WebClient deudasClient = webClientBuilder.baseUrl(DEUDAS_URL).build();

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

        Mono<BienDTO> bienMono = bienesClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(BienDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<DefuncionDTO> defuncionMono = defuncionClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(DefuncionDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<DocumentoDTO> documentoMono = documentosClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(DocumentoDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<BeneficioDTO> beneficioMono = beneficiosClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(BeneficioDTO.class)
                .onErrorResume(e -> Mono.empty());

        Mono<DeudaDTO> deudaMono = deudasClient.get()
                .uri(rut)
                .retrieve()
                .bodyToMono(DeudaDTO.class)
                .onErrorResume(e -> Mono.empty());

        return Mono.zip(personaMono.defaultIfEmpty(new PersonaDTO()),
                        finanzaMono.defaultIfEmpty(new FinanzaDTO()),
                        familiaMono.defaultIfEmpty(new FamiliaDTO()),
                        accesoMono.defaultIfEmpty(new AccesoDTO()),
                        bienMono.defaultIfEmpty(new BienDTO()),
                        defuncionMono.defaultIfEmpty(new DefuncionDTO()),
                        documentoMono.defaultIfEmpty(new DocumentoDTO()),
                        beneficioMono.defaultIfEmpty(new BeneficioDTO()))
                .flatMap(tuple -> deudaMono.defaultIfEmpty(new DeudaDTO())
                        .map(deuda -> {
                            FichaSocialDTO fichaSocialDTO = new FichaSocialDTO();
                            fichaSocialDTO.setPersona(tuple.getT1());
                            fichaSocialDTO.setFinanza(tuple.getT2());
                            fichaSocialDTO.setFamilia(tuple.getT3());
                            fichaSocialDTO.setAcceso(tuple.getT4());
                            fichaSocialDTO.setBien(tuple.getT5());
                            fichaSocialDTO.setDefuncion(tuple.getT6());
                            fichaSocialDTO.setDocumento(tuple.getT7());
                            fichaSocialDTO.setBeneficio(tuple.getT8());
                            fichaSocialDTO.setDeuda(deuda);
                            return fichaSocialDTO;
                        }))
                .block();
    }
}
