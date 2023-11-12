package br.com.moisessantana.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("Username ou email já existe");
  }
}
