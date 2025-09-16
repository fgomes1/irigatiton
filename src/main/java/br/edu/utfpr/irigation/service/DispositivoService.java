package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Dispositivo;
import br.edu.utfpr.irigation.repository.DispositivoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    public List<Dispositivo> listar() {
        return dispositivoRepository.findAll();
    }

    public Dispositivo buscar(UUID id) {
        return dispositivoRepository.findById(id).orElse(null);
    }

    public Dispositivo salvar(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    public void deletar(UUID id) {
        dispositivoRepository.deleteById(id);
    }
}
