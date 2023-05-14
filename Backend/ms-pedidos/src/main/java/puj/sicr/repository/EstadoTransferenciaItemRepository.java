package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.EstadoProducto;
import puj.sicr.entidad.EstadoTransferenciaItem;

import java.util.List;


public interface EstadoTransferenciaItemRepository extends JpaRepository<EstadoTransferenciaItem, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<EstadoTransferenciaItem> getByPage(Pageable pageable);
}
