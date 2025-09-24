package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.DispositivoDTO;
import br.edu.utfpr.irigation.service.DispositivoService;
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
@RequestMapping("/api/dispositivos")
@Tag(name = "Dispositivos", description = "Endpoints para gerenciar dispositivos")
public class DispositivoController {
    private final DispositivoService service;

    public DispositivoController(DispositivoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar dispositivos", description = "Retorna uma lista paginada de dispositivos.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de dispositivos recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public Page<DispositivoDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                       @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @Operation(summary = "Buscar dispositivo", description = "Retorna um dispositivo específico por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo encontrado",
                content = @Content(schema = @Schema(implementation = DispositivoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @GetMapping("/{id}")
    public DispositivoDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @Operation(summary = "Criar dispositivo", description = "Cria um novo dispositivo.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dispositivo criado com sucesso",
                content = @Content(schema = @Schema(implementation = DispositivoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<DispositivoDTO> criar(@Valid @RequestBody DispositivoDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

        @Operation(summary = "Atualizar dispositivo", description = "Atualiza um dispositivo existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = DispositivoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("{id}")
    public ResponseEntity<DispositivoDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody DispositivoDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

        @Operation(summary = "Excluir dispositivo", description = "Remove um dispositivo existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Dispositivo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
