package br.edu.utfpr.irigation.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public class SetorDTO {
    private UUID id;
    private String nome;
    private Double area;
    private String tipoCultura;
    private String status;
    private LocalDateTime horarioIrrigacao;
    private LocalDateTime ultimaIrrigacao;
    private Boolean foiIrrigadoNaHora;
    // Getters e setters
}
