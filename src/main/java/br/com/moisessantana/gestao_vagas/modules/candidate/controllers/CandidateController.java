package br.com.moisessantana.gestao_vagas.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moisessantana.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.moisessantana.gestao_vagas.modules.candidate.services.CreateCandidateService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateService createCandidateService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var result = this.createCandidateService.execute(candidateEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    
  }
}
