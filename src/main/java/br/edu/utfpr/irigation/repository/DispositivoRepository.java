package br.edu.utfpr.irigation.repository;

import br.edu.utfpr.irigation.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {
}
