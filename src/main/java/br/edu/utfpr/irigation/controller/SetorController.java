package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.SetorDTO;
import br.edu.utfpr.irigation.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/setores")
@Tag(name = "Setores", description = "Endpoints para gerenciar setores de irrigação")

public class SetorController {
    private final SetorService service;

    public SetorController(SetorService service) {
        this.service = service;
    }

    @Operation(summary = "Listar setores", description = "Retorna uma lista paginada de setores.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de setores recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public Page<SetorDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                 @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @Operation(summary = "Buscar setor", description = "Retorna um setor específico por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Setor encontrado",
                content = @Content(schema = @Schema(implementation = SetorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Setor não encontrado")
    })
    @GetMapping("/{id}")
    public SetorDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @Operation(summary = "Criar setor", description = "Cria um novo setor.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Setor criado com sucesso",
                content = @Content(schema = @Schema(implementation = SetorDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<SetorDTO> criar(@Valid @RequestBody SetorDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @Operation(summary = "Atualizar setor", description = "Atualiza um setor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Setor atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = SetorDTO.class))),
        @ApiResponse(responseCode = "404", description = "Setor não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SetorDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody SetorDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir setor", description = "Remove um setor existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Setor excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Setor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
