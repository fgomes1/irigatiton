package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Usuario;
import br.edu.utfpr.irigation.repository.UsuarioRepository;
import br.edu.utfpr.irigation.dto.UsuarioDTO;
import br.edu.utfpr.irigation.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<UsuarioDTO> listar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return usuarioRepository.findAll(pageable).map(this::toDTO);
    }

    public UsuarioDTO buscar(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario %s não encontrado".formatted(id)));
        return toDTO(usuario);
    }

    public UsuarioDTO salvar(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return toDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO atualizar(UUID id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario %s não encontrado".formatted(id)));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(dto.getSenha());
        }
        return toDTO(usuarioRepository.save(usuario));
    }

    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario %s não encontrado".formatted(id));
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setSenha(usuario.getSenha());
        return dto;
    }
}
