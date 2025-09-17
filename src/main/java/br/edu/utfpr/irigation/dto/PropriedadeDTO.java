package br.edu.utfpr.irigation.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PropriedadeDTO {
    private UUID id;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 150, message = "nome deve ter no máximo 150 caracteres")
    private String nome;

    @Size(max = 250, message = "localizacao deve ter no máximo 250 caracteres")
    private String localizacao;

    private Double tamanho;

    @NotNull(message = "usuarioId é obrigatório")
    private UUID usuarioId;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Double getTamanho() { return tamanho; }
    public void setTamanho(Double tamanho) { this.tamanho = tamanho; }
    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}
