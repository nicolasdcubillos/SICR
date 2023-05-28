package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.Mesa;
import puj.sicr.entidad.Miembro;

import java.util.List;


@Repository
public interface MiembroRepository extends JpaRepository<Miembro, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Miembro> getByPage(Pageable pageable);

    @Query("SELECT c FROM Miembro c WHERE c.sedeRestaurante.id = :id")
    List<Miembro> findBySedeRestauranteId(Integer id);
}
