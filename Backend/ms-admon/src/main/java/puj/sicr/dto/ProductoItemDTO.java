package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoItemDTO {

    private Integer id;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Integer unidadMedida;

    @NotNull
    private Integer item;

    @NotNull
    private Integer producto;

}
