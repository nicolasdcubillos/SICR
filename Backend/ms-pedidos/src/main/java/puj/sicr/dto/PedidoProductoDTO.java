package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PedidoProductoDTO implements Serializable {
    private Integer id;

    @NotNull
    private Integer pedidoProductoId;

    @NotNull
    private Integer pedidoProductoCantidad;
}
