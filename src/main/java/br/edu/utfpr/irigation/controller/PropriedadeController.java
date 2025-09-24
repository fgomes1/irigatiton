package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.PropriedadeDTO;
import br.edu.utfpr.irigation.service.PropriedadeService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/propriedades")
public class PropriedadeController {
    private final PropriedadeService service;

    public PropriedadeController(PropriedadeService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PropriedadeDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                       @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @GetMapping("/{id}")
    public PropriedadeDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<PropriedadeDTO> criar(@Valid @RequestBody PropriedadeDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @PutMapping("/{id}")
    public PropriedadeDTO atualizar(@PathVariable UUID id, @Valid @RequestBody PropriedadeDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
