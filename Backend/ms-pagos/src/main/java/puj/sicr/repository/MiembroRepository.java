package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Mesa;
import puj.sicr.entidad.Miembro;

import java.util.List;


public interface MiembroRepository extends JpaRepository<Miembro, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Miembro> getByPage(Pageable pageable);
}
