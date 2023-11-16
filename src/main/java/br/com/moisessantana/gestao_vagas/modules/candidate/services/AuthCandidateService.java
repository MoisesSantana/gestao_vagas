package br.com.moisessantana.gestao_vagas.modules.candidate.services;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.moisessantana.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.moisessantana.gestao_vagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.moisessantana.gestao_vagas.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateService {

  @Value("${security.token.secret.candidate}")
  private String secret;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;
  
  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateDTO.username())
      .orElseThrow(() -> {
        throw new UsernameNotFoundException("Usuário/senha inválidos");
      });
    
    var passwordMatches = this.passwordEncoder.matches(
      authCandidateDTO.password(),
      candidate.getPassword()
    );

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secret);

    var expiresIn = Instant.now().plus(Duration.ofDays(7));

    var token = JWT.create()
      .withIssuer("gestao_vagas")
      .withExpiresAt(expiresIn)
      .withClaim("roles", Arrays.asList("CANDIDATE"))
      .withSubject(candidate.getId().toString())
      .sign(algorithm);

    AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder()
      .access_token(token)
      .expires_in(expiresIn.toEpochMilli())
      .build();

    return authCandidateResponse;
  }
}
