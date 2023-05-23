package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.ProductoItem;
import puj.sicr.entidad.ProductoPedido;

import java.util.List;


@Repository
public interface ProductoPedidoRepository extends JpaRepository<ProductoPedido, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<ProductoPedido> getByPage(Pageable pageable);
}
