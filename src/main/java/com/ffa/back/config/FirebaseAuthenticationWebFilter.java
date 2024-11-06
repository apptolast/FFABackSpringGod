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
@Order(-10)  // Alta prioridad en la cadena de filtros
public class FirebaseAuthenticationWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // 1. Obtiene el header de autorización
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 2. Verifica si existe el token Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // 3. Extrae el token
            String idToken = authHeader.substring(7);

            // 4. Verifica el token con Firebase
            return Mono.fromCallable(() -> FirebaseAuth.getInstance().verifyIdToken(idToken))
                    .subscribeOn(Schedulers.boundedElastic())
                    .flatMap(decodedToken -> {
                        // 5. Crea un UserDetails con la información del token
                        UserDetails userDetails = User.withUsername(decodedToken.getUid())
                                .password("")
                                .roles("USER")
                                .build();

                        // 6. Crea la autenticación
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        // 7. Continúa la cadena de filtros con la autenticación
                        return chain.filter(exchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                    })
                    .onErrorResume(e -> {
                        // 8. Maneja errores de autenticación
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        } else {
            // 9. Si no hay token, continúa la cadena
            return chain.filter(exchange);
        }
    }
}