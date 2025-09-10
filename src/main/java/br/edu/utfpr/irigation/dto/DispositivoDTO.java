package br.edu.utfpr.irigation.dto;

import java.util.UUID;
import java.time.LocalDate;

public class DispositivoDTO {
    private UUID id;
    private String nome;
    private String tipo;
    private String modelo;
    private String status;
    private LocalDate dataInstalacao;
    // Getters e setters
}
