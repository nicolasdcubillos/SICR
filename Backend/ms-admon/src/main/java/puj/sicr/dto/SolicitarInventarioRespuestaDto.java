package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SolicitarInventarioRespuestaDto implements Serializable {
    private String nombreSedeRestauranteSolicitante;
    private String nombreSedeRestaurante;
    private String direccion;
    private String latitud;
    private String longitud;
    private String nombreItem;
    private Integer cantidad;
}
