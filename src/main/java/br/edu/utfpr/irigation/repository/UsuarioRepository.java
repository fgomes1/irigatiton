package br.edu.utfpr.irigation.repository;

import br.edu.utfpr.irigation.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
