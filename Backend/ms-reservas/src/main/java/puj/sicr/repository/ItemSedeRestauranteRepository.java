package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Item;
import puj.sicr.entidad.ItemSedeRestaurante;

import java.util.List;


@Repository
public interface ItemSedeRestauranteRepository extends JpaRepository<ItemSedeRestaurante, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<ItemSedeRestaurante> getByPage(Pageable pageable);
}
