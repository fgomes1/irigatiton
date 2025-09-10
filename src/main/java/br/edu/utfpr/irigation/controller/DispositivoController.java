package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.model.Dispositivo;
import br.edu.utfpr.irigation.repository.DispositivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @GetMapping
    public List<Dispositivo> listar() {
        return dispositivoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Dispositivo buscar(@PathVariable UUID id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Dispositivo criar(@RequestBody Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    @PutMapping("/{id}")
    public Dispositivo atualizar(@PathVariable UUID id, @RequestBody Dispositivo dispositivo) {
        dispositivo.setId(id);
        return dispositivoRepository.save(dispositivo);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        dispositivoRepository.deleteById(id);
    }
}
