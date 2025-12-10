package br.edu.utfpr.irigation.controller;

import br.edu.utfpr.irigation.dto.UsuarioDTO;
import br.edu.utfpr.irigation.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Endpoints para gerenciar usuários")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar usuários", description = "Retorna uma lista paginada de usuários.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários recuperada com sucesso",
                content = @Content(schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public Page<UsuarioDTO> listar(@RequestParam(defaultValue = "0") int pagina,
                                   @RequestParam(defaultValue = "10") int tamanho) {
        return service.listar(pagina, tamanho);
    }

    @Operation(summary = "Buscar usuário", description = "Retorna um usuário específico por ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @Operation(summary = "Criar usuário", description = "Cria um novo usuário.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário (público).")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody UsuarioDTO dto) {
        var salvo = service.salvar(dto);
        return ResponseEntity.status(201).body(salvo);
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir usuário", description = "Remove um usuário existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
