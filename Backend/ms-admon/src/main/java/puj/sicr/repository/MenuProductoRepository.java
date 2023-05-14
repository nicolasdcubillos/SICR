package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.ItemSedeRestaurante;
import puj.sicr.entidad.MenuProducto;

import java.util.List;


public interface MenuProductoRepository extends JpaRepository<MenuProducto, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<MenuProducto> getByPage(Pageable pageable);
}
