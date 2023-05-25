package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoDTO {

    private Integer id;

    @NotNull
    private Integer sedeRestauranteId;

    @NotNull
    @Size(max = 255)
    private String nombre;

    @NotNull
    private Integer categoria;

    @NotNull
    private Integer estadoProducto;

}
