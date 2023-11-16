package br.com.moisessantana.gestao_vagas.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.moisessantana.gestao_vagas.providers.JWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityMiddleware extends OncePerRequestFilter {

  @Autowired
  private JWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    
    String authorization = request.getHeader("Authorization");

    if (request.getRequestURI().startsWith("/company")) {
      if (authorization != null && authorization.startsWith("Bearer ")) {
        var subjectToken = this.jwtProvider.validateToken(authorization);
        
        if (subjectToken.isEmpty()) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          return;
        }
  
        request.setAttribute("company_id", subjectToken);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subjectToken, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
  
}
