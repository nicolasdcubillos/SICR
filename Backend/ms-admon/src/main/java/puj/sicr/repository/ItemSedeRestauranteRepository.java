package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Item;
import puj.sicr.entidad.ItemSedeRestaurante;

import java.util.List;


@Repository
public interface ItemSedeRestauranteRepository extends JpaRepository<ItemSedeRestaurante, Integer> {
    @Query("SELECT c FROM ItemSedeRestaurante	c ")
    List<ItemSedeRestaurante> getByPage(Pageable pageable);

    @Query("SELECT c FROM ItemSedeRestaurante c" +
            " WHERE c.cantidad >= :cantidad AND c.item.id = :id " +
            " AND c.sedeRestaurante.restaurante.id = :restauranteId" +
            " ORDER BY c.cantidad")
    List <ItemSedeRestaurante> findByItemIdDisponiblesAndRestauranteId(Integer id, Integer restauranteId, Integer cantidad);


    @Query("SELECT c FROM ItemSedeRestaurante c" +
            " WHERE c.sedeRestaurante.id = :sedeRestauranteId and c.item.id = :itemId")
    ItemSedeRestaurante getByItemIdAndSedeRestauranteId(Integer itemId, Integer sedeRestauranteId);

    @Query("SELECT c FROM ItemSedeRestaurante c" +
            " WHERE c.sedeRestaurante.id = :sedeRestauranteId")
    List<ItemSedeRestaurante> getBySedeId(Integer sedeRestauranteId);
}
