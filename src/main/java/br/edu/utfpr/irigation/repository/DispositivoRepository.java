package br.edu.utfpr.irigation.model.repository;

import br.edu.utfpr.irigation.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
}
