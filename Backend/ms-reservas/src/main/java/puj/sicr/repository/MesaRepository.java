package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Menu;
import puj.sicr.entidad.Mesa;

import java.util.List;


@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Mesa> getByPage(Pageable pageable);
}
