package com.ffa.back.config;

import com.google.firebase.auth.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Order(-10)
public class FirebaseAuthenticationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String idToken = authHeader.substring(7);
            return Mono.fromCallable(() -> FirebaseAuth.getInstance().verifyIdToken(idToken))
                    .subscribeOn(Schedulers.boundedElastic()).flatMap(decodedToken -> {
                        UserDetails userDetails = User.withUsername(decodedToken.getUid())
                                .password("") // No es necesario
                                .roles("USER") // Ajusta según tus necesidades
                                .build();

                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    })
                    .onErrorResume(e -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        } else {
            // Si no hay token, decide si permitir o denegar el acceso
            return chain.filter(exchange);
        }
    }
}
