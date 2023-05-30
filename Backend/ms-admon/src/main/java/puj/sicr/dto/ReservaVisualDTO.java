package puj.sicr.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class ReservaVisualDTO {
    private Integer asientos;
    private OffsetDateTime fecha;
    private Integer horas;
    private String usuarioNombre;

    public ReservaVisualDTO(Integer asientos, OffsetDateTime fecha, Integer horas, String usuarioNombre) {
        this.asientos = asientos;
        this.fecha = fecha;
        this.horas = horas;
        this.usuarioNombre = usuarioNombre;
    }
}
