package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SedeRestauranteDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String direccion;

    @NotNull
    @Size(max = 255)
    private String latitud;

    @NotNull
    @Size(max = 255)
    private String longitud;

    @NotNull
    private OffsetDateTime fechaApertura;

    @NotNull
    private OffsetDateTime fechaCierre;

    @NotNull
    private Integer capacidad;

    @NotNull
    private Integer restaurante;

}
