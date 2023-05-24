package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Restaurante;
import puj.sicr.entidad.SedeRestaurante;

import java.util.List;


@Repository
public interface SedeRestauranteRepository extends JpaRepository<SedeRestaurante, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<SedeRestaurante> getByPage(Pageable pageable);
}
