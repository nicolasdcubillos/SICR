package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.TransferenciaItem;
import puj.sicr.entidad.Usuario;

import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Usuario> getByPage(Pageable pageable);

    Usuario findByUsername(String username);
}
