package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Dispositivo;
import br.edu.utfpr.irigation.repository.DispositivoRepository;
import br.edu.utfpr.irigation.repository.PropriedadeRepository;
import br.edu.utfpr.irigation.dto.DispositivoDTO;
import br.edu.utfpr.irigation.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private PropriedadeRepository propriedadeRepository;

    public Page<DispositivoDTO> listar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return dispositivoRepository.findAll(pageable).map(this::toDTO);
    }

    public DispositivoDTO buscar(UUID id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo %s não encontrado".formatted(id)));
        return toDTO(dispositivo);
    }

    public DispositivoDTO salvar(DispositivoDTO dto) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(dto.getNome());
        dispositivo.setModelo(dto.getModelo());
        dispositivo.setStatus(dto.getStatus());
        dispositivo.setDataInstalacao(dto.getDataInstalacao());
        var propriedade = propriedadeRepository.findById(dto.getPropriedadeId())
                .orElseThrow(() -> new NotFoundException("Propriedade %s não encontrada".formatted(dto.getPropriedadeId())));
        dispositivo.setPropriedade(propriedade);
        return toDTO(dispositivoRepository.save(dispositivo));
    }

    public DispositivoDTO atualizar(UUID id, DispositivoDTO dto) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dispositivo %s não encontrado".formatted(id)));
        dispositivo.setNome(dto.getNome());
        dispositivo.setModelo(dto.getModelo());
        dispositivo.setStatus(dto.getStatus());
        dispositivo.setDataInstalacao(dto.getDataInstalacao());
        if (dto.getPropriedadeId() != null) {
            var propriedade = propriedadeRepository.findById(dto.getPropriedadeId())
                    .orElseThrow(() -> new NotFoundException("Propriedade %s não encontrada".formatted(dto.getPropriedadeId())));
            dispositivo.setPropriedade(propriedade);
        }
        return toDTO(dispositivoRepository.save(dispositivo));
    }

    public void deletar(UUID id) {
        if (!dispositivoRepository.existsById(id)) {
            throw new NotFoundException("Dispositivo %s não encontrado".formatted(id));
        }
        dispositivoRepository.deleteById(id);
    }

    private DispositivoDTO toDTO(Dispositivo d) {
        DispositivoDTO dto = new DispositivoDTO();
        dto.setId(d.getId());
        dto.setNome(d.getNome());
        dto.setModelo(d.getModelo());
        dto.setStatus(d.getStatus());
        dto.setDataInstalacao(d.getDataInstalacao());
        dto.setPropriedadeId(d.getPropriedade() != null ? d.getPropriedade().getId() : null);
        return dto;
    }
}
