package puj.sicr.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import puj.sicr.dto.ReservaDTO;
import puj.sicr.dto.ReservaVisualDTO;
import puj.sicr.dto.SolicitudTransferenciaInventarioDTO;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.Reserva;

import java.util.List;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    @Query("SELECT c FROM EstadoProducto	c ")
    List<Reserva> getByPage(Pageable pageable);

    @Query("SELECT new puj.sicr.dto.ReservaVisualDTO(c.asientos,c.fecha,c.horas,c.usuario.username) FROM Reserva c "+
            " WHERE c.sedeRestaurante.id = :idSede ")
    List<ReservaVisualDTO> findBySedeId(Integer idSede);


}
