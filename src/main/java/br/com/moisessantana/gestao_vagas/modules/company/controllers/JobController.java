package br.com.moisessantana.gestao_vagas.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moisessantana.gestao_vagas.modules.company.entities.JobEntity;
import br.com.moisessantana.gestao_vagas.modules.company.service.CreateJobService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {
  
  @Autowired
  private CreateJobService createJobService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody JobEntity jobEntity) {
    try {
      var result = this.createJobService.execute(jobEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
