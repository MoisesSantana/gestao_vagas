package br.com.moisessantana.gestao_vagas.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moisessantana.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.moisessantana.gestao_vagas.modules.candidate.services.CreateCandidateService;
import br.com.moisessantana.gestao_vagas.modules.candidate.services.ProfileCandidateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateService createCandidateService;

  @Autowired
  private ProfileCandidateService profileCandidateService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateService.execute(candidateEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @GetMapping()
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> get(HttpServletRequest request) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateService.execute(UUID.fromString(idCandidate.toString()));
      return ResponseEntity.status(HttpStatus.OK).body(profile);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
