package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RealizarReservaDto implements Serializable {
    private Integer restauranteId;
    private Integer sedeReservaId;
    private Integer usuarioReservaId;
    private Date fechaReserva;
    private Integer cantidadPersonas;
}
