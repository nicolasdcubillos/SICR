package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.TipoMiembro;
import puj.sicr.entidad.TipoUsuario;

import java.util.List;


@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<TipoUsuario> getByPage(Pageable pageable);
}
