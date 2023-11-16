package br.com.moisessantana.gestao_vagas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  private SecurityMiddleware securityMiddleware;

  @Autowired
  private SecurityCandidateMiddleware securityCandidateMiddleware;
  
  private static final String[] SWAGGER_LIST = {
    "/swagger-ui/**",
    "/swagger-ui.html",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui/**/**",
    "/swagger-ui/**/**/**",
    "/swagger-ui/**/**/**/**",
    "/swagger-ui/**/**/**/**/**",
    "/swagger-ui/**/**/**/**/**/**",
    "/swagger-ui/**/**/**/**/**/**/**",
    "/swagger-ui/**/**/**/**/**/**/**/**",
    "/swagger-ui/**/**/**/**/**/**/**/**/**",
    "/sw"
  };

  @Bean
  SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
      .authorizeHttpRequests((auth) -> {
        auth.requestMatchers("/candidate").permitAll()
          .requestMatchers("/candidate/auth").permitAll()
          .requestMatchers("/company").permitAll()
          .requestMatchers("/company/auth").permitAll()
          .requestMatchers(SWAGGER_LIST).permitAll();
        auth.anyRequest().authenticated();
      })
      .addFilterBefore(securityCandidateMiddleware, BasicAuthenticationFilter.class)
      .addFilterBefore(securityMiddleware, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
