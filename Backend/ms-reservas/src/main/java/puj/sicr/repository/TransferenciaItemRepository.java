package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.entidad.TipoUsuario;
import puj.sicr.entidad.TransferenciaItem;

import java.util.List;


@Repository
public interface TransferenciaItemRepository extends JpaRepository<TransferenciaItem, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<TransferenciaItem> getByPage(Pageable pageable);
}
