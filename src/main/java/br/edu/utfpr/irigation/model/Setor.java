package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Setor extends BaseEntity {
    private String nome;
    private Double area;
    private String tipoCultura;
    private String status;
    private LocalDateTime horarioIrrigacao;
    private LocalDateTime ultimaIrrigacao;
    private Boolean foiIrrigadoNaHora;

    @ManyToOne
    @JoinColumn(name = "dispositivo_id")
    private Dispositivo dispositivo;

    // Getters and setters
}
