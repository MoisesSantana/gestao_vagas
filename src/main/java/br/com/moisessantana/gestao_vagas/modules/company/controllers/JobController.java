package br.com.moisessantana.gestao_vagas.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.moisessantana.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.moisessantana.gestao_vagas.modules.company.entities.JobEntity;
import br.com.moisessantana.gestao_vagas.modules.company.service.CreateJobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/job")
public class JobController {
  
  @Autowired
  private CreateJobService createJobService;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createjobDTO, HttpServletRequest request) {
    try {
      var companyId = request.getAttribute("company_id");

      var jobEntity = JobEntity.builder()
        .benefits(createjobDTO.getBenefits())
        .description(createjobDTO.getDescription())
        .level(createjobDTO.getLevel())
        .companyId(UUID.fromString(companyId.toString()))
        .build();

      var result = this.createJobService.execute(jobEntity);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
