package br.com.moisessantana.gestao_vagas.modules.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.moisessantana.gestao_vagas.modules.company.entities.JobEntity;
import br.com.moisessantana.gestao_vagas.modules.company.repositories.JobRepository;

@Service
public class CreateJobService {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity) {
    return this.jobRepository.save(jobEntity);
  }
}
