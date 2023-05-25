package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservaDTO {

    private Integer id;

    @NotNull
    private Integer asientos;

    @NotNull
    private OffsetDateTime fecha;

    @NotNull
    private Integer horas;

    @NotNull
    private Integer usuario;

    @NotNull
    private Integer sedeRestaurante;

}
