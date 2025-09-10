package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.model.Setor;
import br.edu.utfpr.irigation.repository.SetorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setores")
public class SetorController {
    @Autowired
    private SetorRepository setorRepository;

    @GetMapping
    public List<Setor> listar() {
        return setorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Setor buscar(@PathVariable UUID id) {
        return setorRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Setor criar(@RequestBody Setor setor) {
        return setorRepository.save(setor);
    }

    @PutMapping("/{id}")
    public Setor atualizar(@PathVariable UUID id, @RequestBody Setor setor) {
        setor.setId(id);
        return setorRepository.save(setor);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        setorRepository.deleteById(id);
    }
}
