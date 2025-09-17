package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Usuario extends BaseEntity {
    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Propriedade> propriedades = new ArrayList<>();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public List<Propriedade> getPropriedades() { return propriedades; }
    public void setPropriedades(List<Propriedade> propriedades) { this.propriedades = propriedades; }
}
