package br.edu.utfpr.irigation.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Usuario extends BaseEntity {
    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "usuario")
    private List<Propriedade> propriedades = new ArrayList<>();

    // Getters and setters
}
