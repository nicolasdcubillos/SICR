package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Pedido;
import puj.sicr.entidad.ProductoItem;

import java.util.List;


@Repository
public interface ProductoItemRepository extends JpaRepository<ProductoItem, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<ProductoItem> getByPage(Pageable pageable);
}
