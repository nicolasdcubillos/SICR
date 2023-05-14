package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.ProductoPedido;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Producto> getByPage(Pageable pageable);
}
