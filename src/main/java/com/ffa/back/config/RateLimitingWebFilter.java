package com.ffa.back.config;

import io.github.bucket4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingWebFilter implements WebFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Refill refill = Refill.greedy(100, Duration.ofMinutes(1)); // 100 tokens por minuto
        Bandwidth limit = Bandwidth.classic(100, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        Bucket bucket = buckets.computeIfAbsent(ip, k -> createNewBucket());

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            exchange.getResponse().getHeaders().add("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return chain.filter(exchange);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            exchange.getResponse().getHeaders().add("X-Rate-Limit-Retry-After-Millis", String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000));
            return exchange.getResponse().setComplete();
        }
    }
}