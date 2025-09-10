package br.edu.utfpr.irigation.repository;

import br.edu.utfpr.irigation.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SetorRepository extends JpaRepository<Setor, UUID> {
}
