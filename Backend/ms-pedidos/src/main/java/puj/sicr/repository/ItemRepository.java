package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.EstadoTransferenciaItem;
import puj.sicr.entidad.Item;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Item> getByPage(Pageable pageable);

    @Query("SELECT i FROM Item i INNER JOIN ProductoItem pi ON i.id = pi.item.id WHERE pi.id =:id ")
    Item getItemByProductoItemId(Integer id);
}
