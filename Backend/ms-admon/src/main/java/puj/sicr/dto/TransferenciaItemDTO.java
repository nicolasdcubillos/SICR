package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransferenciaItemDTO {

    private Integer id;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Integer estadoTransferenciaItem;

    @NotNull
    private Integer item;

    @NotNull
    private Integer sedeRestauranteDestino;

    @NotNull
    private Integer sedeRestauranteOrigen;

}
