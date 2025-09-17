package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Propriedade extends BaseEntity {
    private String nome;
    private String localizacao;
    private Double tamanho;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "propriedade")
    private List<Dispositivo> dispositivos = new ArrayList<>();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Double getTamanho() { return tamanho; }
    public void setTamanho(Double tamanho) { this.tamanho = tamanho; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<Dispositivo> getDispositivos() { return dispositivos; }
    public void setDispositivos(List<Dispositivo> dispositivos) { this.dispositivos = dispositivos; }
}
