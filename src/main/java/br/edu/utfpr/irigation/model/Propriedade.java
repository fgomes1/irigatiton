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

    // Getters and setters
}
