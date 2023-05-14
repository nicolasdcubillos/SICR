package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Reserva;
import puj.sicr.entidad.Restaurante;

import java.util.List;


public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Restaurante> getByPage(Pageable pageable);
}
