package com.cl.duoc.apigateway.filter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String SEPARATOR = "====================================================";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        LocalDateTime now = LocalDateTime.now();
        String fechaHora = now.format(FORMATTER);
        String metodo = exchange.getRequest().getMethod().name();
        String ruta = exchange.getRequest().getURI().getPath();
        long start = System.currentTimeMillis();

        System.out.println(SEPARATOR);
        System.out.println("API GATEWAY");
        System.out.println("Fecha : " + fechaHora);
        System.out.println("Método: " + metodo);
        System.out.println("Ruta   : " + ruta);
        System.out.println(SEPARATOR);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            long durationMs = System.currentTimeMillis() - start;
            var status = exchange.getResponse().getStatusCode();

            String estadoHttp = status != null
                ? status.value() + " " + status
                : "UNKNOWN";

            System.out.println();
            System.out.println("Estado HTTP: " + estadoHttp);
            System.out.println("Tiempo: " + durationMs + " ms");
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
