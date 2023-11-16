package br.com.moisessantana.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.moisessantana.gestao_vagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityCandidateMiddleware extends OncePerRequestFilter {

  @Autowired
  private JWTCandidateProvider jwtCandidateProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (request.getRequestURI().startsWith("/candidate")) {
          if (authorization != null) {
            var token = this.jwtCandidateProvider.validateToken(authorization);
            
            if (token == null) {
              response.setStatus(HttpStatus.UNAUTHORIZED.value());
              return;
            }
  
            request.setAttribute("candidate_id", token.getSubject());
            var roles = token.getClaim("roles").asList((Object.class));

            var grants = roles.stream()
              .map((role) -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);
            Collections.emptyList();
            SecurityContextHolder.getContext().setAuthentication(auth);
          }
  
        }
        
        filterChain.doFilter(request, response);
  }
  
}
