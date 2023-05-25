package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TipoMiembroDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String nombre;

}
