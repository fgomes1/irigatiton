package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Propriedade;
import br.edu.utfpr.irigation.repository.PropriedadeRepository;
import br.edu.utfpr.irigation.repository.UsuarioRepository;
import br.edu.utfpr.irigation.dto.PropriedadeDTO;
import br.edu.utfpr.irigation.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PropriedadeService {
    @Autowired
    private PropriedadeRepository propriedadeRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<PropriedadeDTO> listar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return propriedadeRepository.findAll(pageable).map(this::toDTO);
    }

    public PropriedadeDTO buscar(UUID id) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Propriedade %s não encontrada".formatted(id)));
        return toDTO(propriedade);
    }

    public PropriedadeDTO salvar(PropriedadeDTO dto) {
        Propriedade propriedade = new Propriedade();
        propriedade.setNome(dto.getNome());
        propriedade.setLocalizacao(dto.getLocalizacao());
        propriedade.setTamanho(dto.getTamanho());
        var usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuario %s não encontrado".formatted(dto.getUsuarioId())));
        propriedade.setUsuario(usuario);
        return toDTO(propriedadeRepository.save(propriedade));
    }

    public PropriedadeDTO atualizar(UUID id, PropriedadeDTO dto) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Propriedade %s não encontrada".formatted(id)));
        propriedade.setNome(dto.getNome());
        propriedade.setLocalizacao(dto.getLocalizacao());
        propriedade.setTamanho(dto.getTamanho());
        if (dto.getUsuarioId() != null) {
            var usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuario %s não encontrado".formatted(dto.getUsuarioId())));
            propriedade.setUsuario(usuario);
        }
        return toDTO(propriedadeRepository.save(propriedade));
    }

    public void deletar(UUID id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new NotFoundException("Propriedade %s não encontrada".formatted(id));
        }
        propriedadeRepository.deleteById(id);
    }

    private PropriedadeDTO toDTO(Propriedade p) {
        PropriedadeDTO dto = new PropriedadeDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setLocalizacao(p.getLocalizacao());
        dto.setTamanho(p.getTamanho());
        dto.setUsuarioId(p.getUsuario() != null ? p.getUsuario().getId() : null);
        return dto;
    }
}
