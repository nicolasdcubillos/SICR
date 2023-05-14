package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.EstadoTransferenciaItem;
import puj.sicr.entidad.Item;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Item> getByPage(Pageable pageable);
}
