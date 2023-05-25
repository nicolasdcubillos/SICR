package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MiembroDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    @Size(max = 255)
    private String apellido;

    @NotNull
    private Double salario;

    @NotNull
    @Size(max = 255)
    private String telefono;

    @NotNull
    private Integer sedeRestaurante;

    @NotNull
    private Integer tipoMiembro;

}
