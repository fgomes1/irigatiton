package br.edu.utfpr.irigation.controller;


import br.edu.utfpr.irigation.model.Propriedade;

import br.edu.utfpr.irigation.model.repository.PropriedadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/propriedades")
public class PropriedadeController {
    @Autowired
    private PropriedadeRepository propriedadeRepository;

    @GetMapping
    public List<Propriedade> listar() {
        return propriedadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Propriedade buscar(@PathVariable UUID id) {
        return propriedadeRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Propriedade criar(@RequestBody Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    @PutMapping("/{id}")
    public Propriedade atualizar(@PathVariable UUID id, @RequestBody Propriedade propriedade) {
        propriedade.setId(id);
        return propriedadeRepository.save(propriedade);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        propriedadeRepository.deleteById(id);
    }
}
