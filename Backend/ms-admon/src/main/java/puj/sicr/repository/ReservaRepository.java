package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.Reserva;

import java.util.List;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Reserva> getByPage(Pageable pageable);
}
