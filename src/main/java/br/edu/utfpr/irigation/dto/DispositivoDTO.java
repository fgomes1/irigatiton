package br.edu.utfpr.irigation.dto;

import java.util.UUID;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DispositivoDTO {
    private UUID id;

    @NotNull(message = "propriedadeId é obrigatório")
    private UUID propriedadeId;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 150, message = "nome deve ter no máximo 150 caracteres")
    private String nome;

    @Size(max = 200, message = "modelo deve ter no máximo 200 caracteres")
    private String modelo;

    @Size(max = 50, message = "status deve ter no máximo 50 caracteres")
    private String status;

    @NotNull(message = "dataInstalacao é obrigatória")
    private LocalDate dataInstalacao;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPropriedadeId() { return propriedadeId; }
    public void setPropriedadeId(UUID propriedadeId) { this.propriedadeId = propriedadeId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDate getDataInstalacao() { return dataInstalacao; }
    public void setDataInstalacao(LocalDate dataInstalacao) { this.dataInstalacao = dataInstalacao; }
}
