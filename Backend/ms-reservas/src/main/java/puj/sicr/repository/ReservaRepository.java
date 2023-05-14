package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.Reserva;

import java.util.List;


public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Reserva> getByPage(Pageable pageable);
}
