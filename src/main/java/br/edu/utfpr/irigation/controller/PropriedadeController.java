package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.PropriedadeDTO;
import br.edu.utfpr.irigation.service.PropriedadeService;
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
@RequestMapping("/api/propriedades")
@Tag(name = "Propriedades", description = "Endpoints para gerenciar propriedades de irrigação")
public class PropriedadeController {
    private final PropriedadeService service;

    public PropriedadeController(PropriedadeService service) {
        this.service = service;
    }

    @Operation(summary = "Listar propriedades", description = "Retorna uma lista paginada de propriedades.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de propriedades recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public Page<PropriedadeDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                       @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @Operation(summary = "Buscar propriedade", description = "Retorna uma propriedade específica por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Propriedade encontrada",
                content = @Content(schema = @Schema(implementation = PropriedadeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    @GetMapping("/{id}")
    public PropriedadeDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @Operation(summary = "Criar propriedade", description = "Cria uma nova propriedade.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Propriedade criada com sucesso",
                content = @Content(schema = @Schema(implementation = PropriedadeDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<PropriedadeDTO> criar(@Valid @RequestBody PropriedadeDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @Operation(summary = "Atualizar propriedade", description = "Atualiza uma propriedade existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Propriedade atualizada com sucesso",
                content = @Content(schema = @Schema(implementation = PropriedadeDTO.class))),
        @ApiResponse(responseCode = "404", description = "Propriedade não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PropriedadeDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody PropriedadeDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir propriedade", description = "Remove uma propriedade existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Propriedade excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Propriedade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
