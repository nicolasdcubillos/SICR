package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SolicitarInventarioDto implements Serializable {
    private Integer restauranteId;
    private Integer sedeOrigenId;
    private Integer itemId;
    private Integer cantidad;
}
