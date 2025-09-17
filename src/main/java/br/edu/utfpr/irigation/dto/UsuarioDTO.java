package br.edu.utfpr.irigation.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {
    private UUID id;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 100, message = "nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    @Size(max = 150, message = "email deve ter no máximo 150 caracteres")
    private String email;

    @NotBlank(message = "senha é obrigatória")
    @Size(min = 6, max = 100, message = "senha deve ter entre 6 e 100 caracteres")
    private String senha;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
