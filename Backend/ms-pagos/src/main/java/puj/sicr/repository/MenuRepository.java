package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puj.sicr.entidad.Menu;
import puj.sicr.entidad.MenuProducto;

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Menu> getByPage(Pageable pageable);
}
