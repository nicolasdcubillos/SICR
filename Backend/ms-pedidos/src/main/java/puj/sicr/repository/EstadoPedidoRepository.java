package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.EstadoPedido;
import puj.sicr.entidad.EstadoProducto;

import java.util.List;


@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<EstadoPedido> getByPage(Pageable pageable);
}
