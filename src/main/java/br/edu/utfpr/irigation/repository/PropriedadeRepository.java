package br.edu.utfpr.irigation.model.repository;

import br.edu.utfpr.irigation.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PropriedadeRepository extends JpaRepository<Propriedade, UUID> {
}
