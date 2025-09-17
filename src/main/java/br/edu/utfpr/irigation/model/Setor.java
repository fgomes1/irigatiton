package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Setor extends BaseEntity {
    private String nome;
    private Double area;
    private String tipoCultura;
    private String status;
    @Column(columnDefinition = "TEXT")
    private String horariosIrrigacao;
    private LocalDate ultimaIrrigacao;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

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
    public Dispositivo getDispositivo() { return dispositivo; }
    public void setDispositivo(Dispositivo dispositivo) { this.dispositivo = dispositivo; }
}
