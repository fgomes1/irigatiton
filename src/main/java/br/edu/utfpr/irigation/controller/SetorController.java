package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.SetorDTO;
import br.edu.utfpr.irigation.service.SetorService;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/setores")
public class SetorController {
    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    @GetMapping
    public Page<SetorDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                 @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @GetMapping("/{id}")
    public SetorDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<SetorDTO> criar(@Valid @RequestBody SetorDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public SetorDTO atualizar(@PathVariable UUID id, @Valid @RequestBody SetorDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
