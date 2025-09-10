package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Dispositivo extends BaseEntity {

    private String nome;
    private String tipo;
    private String modelo;
    private String status;
    private LocalDate dataInstalacao;

    @ManyToOne
    @JoinColumn(name = "propriedade_id")
    private Propriedade propriedade;

    @OneToMany(mappedBy = "dispositivo")
    private List<Setor> setores = new ArrayList<>();

    // Getters and setters
}
