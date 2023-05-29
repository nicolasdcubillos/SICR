package puj.sicr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class SolicitudTransferenciaInventarioDTO implements Serializable {
    private Integer idSolicitud;
    private String nombreSede;
    private String nombreEstado;
    private Integer estadoId;
    private String nombreItem;
    private Integer cantidadItem;

    public SolicitudTransferenciaInventarioDTO(Integer idSolicitud, String nombreSede, String nombreEstado, Integer estadoId, String nombreItem, Integer cantidadItem) {
        this.idSolicitud = idSolicitud;
        this.nombreSede = nombreSede;
        this.nombreEstado = nombreEstado;
        this.estadoId = estadoId;
        this.nombreItem = nombreItem;
        this.cantidadItem = cantidadItem;
    }
}

