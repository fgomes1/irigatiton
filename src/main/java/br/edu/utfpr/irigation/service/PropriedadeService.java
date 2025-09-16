package br.edu.utfpr.irigation.service;

import br.edu.utfpr.irigation.model.Propriedade;
import br.edu.utfpr.irigation.repository.PropriedadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class PropriedadeService {
    @Autowired
    private PropriedadeRepository propriedadeRepository;

    public List<Propriedade> listar() {
        return propriedadeRepository.findAll();
    }

    public Propriedade buscar(UUID id) {
        return propriedadeRepository.findById(id).orElse(null);
    }

    public Propriedade salvar(Propriedade propriedade) {
        return propriedadeRepository.save(propriedade);
    }

    public void deletar(UUID id) {
        propriedadeRepository.deleteById(id);
    }
}
