package br.com.moisessantana.gestao_vagas.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "company")
public class CompanyEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Pattern(regexp = "\\S+", message = "O campo (username) não pode estar vazio nem conter espaços.")
  private String username;

  @Email(message = "O campo (email) deve conter um e-mail válido.")
  private String email;

  @Length(min = 6, message = "O campo (password) deve conter no mínimo 6 caracteres.")
  private String password;

  private String website;

  private String name;

  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
