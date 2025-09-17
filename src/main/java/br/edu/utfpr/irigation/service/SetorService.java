package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Setor;
import br.edu.utfpr.irigation.repository.SetorRepository;
import br.edu.utfpr.irigation.repository.DispositivoRepository;
import br.edu.utfpr.irigation.dto.SetorDTO;
import br.edu.utfpr.irigation.exception.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SetorService {
    @Autowired
    private SetorRepository setorRepository;
    @Autowired
    private DispositivoRepository dispositivoRepository;

    public Page<SetorDTO> listar(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return setorRepository.findAll(pageable).map(this::toDTO);
    }

    public SetorDTO buscar(UUID id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor %s não encontrado".formatted(id)));
        return toDTO(setor);
    }

    public SetorDTO salvar(SetorDTO dto) {
        Setor setor = new Setor();
        setor.setNome(dto.getNome());
        setor.setArea(dto.getArea());
        setor.setTipoCultura(dto.getTipoCultura());
        setor.setStatus(dto.getStatus());
        setor.setHorariosIrrigacao(dto.getHorariosIrrigacao());
        setor.setUltimaIrrigacao(dto.getUltimaIrrigacao());
        var dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                .orElseThrow(() -> new NotFoundException("Dispositivo %s não encontrado".formatted(dto.getDispositivoId())));
        setor.setDispositivo(dispositivo);
        return toDTO(setorRepository.save(setor));
    }

    public SetorDTO atualizar(UUID id, SetorDTO dto) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Setor %s não encontrado".formatted(id)));
        setor.setNome(dto.getNome());
        setor.setArea(dto.getArea());
        setor.setTipoCultura(dto.getTipoCultura());
        setor.setStatus(dto.getStatus());
        setor.setHorariosIrrigacao(dto.getHorariosIrrigacao());
        setor.setUltimaIrrigacao(dto.getUltimaIrrigacao());
        if (dto.getDispositivoId() != null) {
            var dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                    .orElseThrow(() -> new NotFoundException("Dispositivo %s não encontrado".formatted(dto.getDispositivoId())));
            setor.setDispositivo(dispositivo);
        }
        return toDTO(setorRepository.save(setor));
    }

    public void deletar(UUID id) {
        if (!setorRepository.existsById(id)) {
            throw new NotFoundException("Setor %s não encontrado".formatted(id));
        }
        setorRepository.deleteById(id);
    }

    private SetorDTO toDTO(Setor s) {
        SetorDTO dto = new SetorDTO();
        dto.setId(s.getId());
        dto.setNome(s.getNome());
        dto.setArea(s.getArea());
        dto.setTipoCultura(s.getTipoCultura());
        dto.setStatus(s.getStatus());
        dto.setHorariosIrrigacao(s.getHorariosIrrigacao());
        dto.setUltimaIrrigacao(s.getUltimaIrrigacao());
        dto.setDispositivoId(s.getDispositivo() != null ? s.getDispositivo().getId() : null);
        return dto;
    }
}
