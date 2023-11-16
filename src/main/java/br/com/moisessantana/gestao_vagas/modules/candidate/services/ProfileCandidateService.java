package br.com.moisessantana.gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.moisessantana.gestao_vagas.modules.candidate.CandidateRepository;
import br.com.moisessantana.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

@Service
public class ProfileCandidateService {

  @Autowired
  private CandidateRepository candidateRepository;
  
  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
      .orElseThrow(() -> {
        throw new UsernameNotFoundException("Candidato não encontrado");
      });

    var candidateDTO = ProfileCandidateResponseDTO.builder()
      .id(candidate.getId())
      .username(candidate.getUsername())
      .description(candidate.getDescription())
      .email(candidate.getEmail())
      .name(candidate.getName())
      .build();

    return candidateDTO;
  }
}
