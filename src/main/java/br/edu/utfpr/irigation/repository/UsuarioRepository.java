package br.edu.utfpr.irigation.repository;

import br.edu.utfpr.irigation.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    List<Usuario> findByEmail(String email);
    List<Usuario> findByNome(String nome);
}
