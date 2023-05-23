package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.ReservaMesa;

import java.util.List;


@Repository
public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<ReservaMesa> getByPage(Pageable pageable);
}
