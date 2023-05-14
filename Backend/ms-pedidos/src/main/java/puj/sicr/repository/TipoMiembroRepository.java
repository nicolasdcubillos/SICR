package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.entidad.TipoMiembro;

import java.util.List;


public interface TipoMiembroRepository extends JpaRepository<TipoMiembro, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<TipoMiembro> getByPage(Pageable pageable);
}
