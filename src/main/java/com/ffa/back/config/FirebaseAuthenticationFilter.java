package com.ffa.back.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String idToken = header.substring(7);
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
                // Puedes almacenar información del usuario en el request para usarla más adelante
                request.setAttribute("firebaseUser", decodedToken);
            } catch (FirebaseAuthException e) {
                // Token inválido
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Firebase ID token.");
                return;
            }
        } else {
            // No se proporcionó token
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Firebase ID token.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}