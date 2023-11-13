package br.com.moisessantana.gestao_vagas.modules.company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.moisessantana.gestao_vagas.exceptions.UserFoundException;
import br.com.moisessantana.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.moisessantana.gestao_vagas.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyService {

  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository.findByUsernameOrEmail(
      companyEntity.getUsername(),
      companyEntity.getEmail()
    ).ifPresent((company) -> {
      throw new UserFoundException();
    });

    return this.companyRepository.save(companyEntity);
  }
}
