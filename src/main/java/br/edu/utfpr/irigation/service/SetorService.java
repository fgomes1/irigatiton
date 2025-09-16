package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Setor;
import br.edu.utfpr.irigation.repository.SetorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class SetorService {
    @Autowired
    private SetorRepository setorRepository;

    public List<Setor> listar() {
        return setorRepository.findAll();
    }

    public Setor buscar(UUID id) {
        return setorRepository.findById(id).orElse(null);
    }

    public Setor salvar(Setor setor) {
        return setorRepository.save(setor);
    }

    public void deletar(UUID id) {
        setorRepository.deleteById(id);
    }
}
