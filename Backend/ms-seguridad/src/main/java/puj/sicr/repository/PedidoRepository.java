package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Miembro;
import puj.sicr.entidad.Pedido;

import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Pedido> getByPage(Pageable pageable);
}
