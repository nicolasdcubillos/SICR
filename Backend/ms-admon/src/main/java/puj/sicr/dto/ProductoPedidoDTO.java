package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductoPedidoDTO {

    private Integer id;

    @NotNull
    private Integer cantidad;

    @NotNull
    private Integer producto;

    @NotNull
    private Integer pedido;

}
