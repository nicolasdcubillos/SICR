package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.EstadoProducto;

import java.util.List;


public interface EstadoProductoRepository extends JpaRepository<EstadoProducto, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<EstadoProducto> getByPage(Pageable pageable);
}
