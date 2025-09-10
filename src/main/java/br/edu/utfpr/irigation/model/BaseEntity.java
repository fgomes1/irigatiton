package br.edu.utfpr.irigation.model;

import java.util.UUID;

import jakarta.persistence.Id;

public abstract class BaseEntity {
    @Id
    private UUID id = UUID.randomUUID();

    
}
