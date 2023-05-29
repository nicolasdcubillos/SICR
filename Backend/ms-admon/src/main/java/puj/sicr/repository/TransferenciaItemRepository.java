package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.dto.SolicitudTransferenciaInventarioDTO;
import puj.sicr.entidad.TransferenciaItem;

import java.util.List;


@Repository
public interface TransferenciaItemRepository extends JpaRepository<TransferenciaItem, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<TransferenciaItem> getByPage(Pageable pageable);

    @Query("SELECT new puj.sicr.dto.SolicitudTransferenciaInventarioDTO(c.id , c.sedeRestauranteDestino.nombre, c.estadoTransferenciaItem.nombre, " +
            " c.estadoTransferenciaItem.id, c.item.nombre, c.cantidad ) " +
            " FROM TransferenciaItem c WHERE c.sedeRestauranteOrigen.id = :idSede ")
    List<SolicitudTransferenciaInventarioDTO> getBySedeIdOrigen(Integer idSede);

    @Query("SELECT new puj.sicr.dto.SolicitudTransferenciaInventarioDTO( c.id, c.sedeRestauranteOrigen.nombre, c.estadoTransferenciaItem.nombre, " +
            " c.estadoTransferenciaItem.id, c.item.nombre, c.cantidad) FROM TransferenciaItem c "+
            " WHERE c.sedeRestauranteDestino.id = :idSede ")
    List<SolicitudTransferenciaInventarioDTO> getBySedeIdDestino(Integer idSede);
}
