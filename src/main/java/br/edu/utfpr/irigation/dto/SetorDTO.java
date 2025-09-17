package br.edu.utfpr.irigation.dto;

import java.util.UUID;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SetorDTO {
    private UUID id;

    @NotNull(message = "dispositivoId é obrigatório")
    private UUID dispositivoId;

    @NotBlank(message = "nome é obrigatório")
    @Size(max = 150, message = "nome deve ter no máximo 150 caracteres")
    private String nome;

    private Double area;

    @Size(max = 100, message = "tipoCultura deve ter no máximo 100 caracteres")
    private String tipoCultura;

    @Size(max = 50, message = "status deve ter no máximo 50 caracteres")
    private String status;

    // Simplificado: horários e última irrigação
    private String horariosIrrigacao; // JSON string or simple description
    private LocalDate ultimaIrrigacao;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getDispositivoId() { return dispositivoId; }
    public void setDispositivoId(UUID dispositivoId) { this.dispositivoId = dispositivoId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getArea() { return area; }
    public void setArea(Double area) { this.area = area; }
    public String getTipoCultura() { return tipoCultura; }
    public void setTipoCultura(String tipoCultura) { this.tipoCultura = tipoCultura; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getHorariosIrrigacao() { return horariosIrrigacao; }
    public void setHorariosIrrigacao(String horariosIrrigacao) { this.horariosIrrigacao = horariosIrrigacao; }
    public LocalDate getUltimaIrrigacao() { return ultimaIrrigacao; }
    public void setUltimaIrrigacao(LocalDate ultimaIrrigacao) { this.ultimaIrrigacao = ultimaIrrigacao; }
}
