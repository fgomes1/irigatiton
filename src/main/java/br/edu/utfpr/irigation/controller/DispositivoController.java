package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.DispositivoDTO;
import br.edu.utfpr.irigation.service.DispositivoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/dispositivos")
public class DispositivoController {
    private final DispositivoService service;

    public DispositivoController(DispositivoService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DispositivoDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                       @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @GetMapping("/{id}")
    public DispositivoDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<DispositivoDTO> criar(@Valid @RequestBody DispositivoDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public DispositivoDTO atualizar(@PathVariable UUID id, @Valid @RequestBody DispositivoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
