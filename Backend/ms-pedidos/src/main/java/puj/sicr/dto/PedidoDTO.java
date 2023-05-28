package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;


@Getter
@Setter
public class PedidoDTO {

    private Integer id;

    @NotNull
    private Double subtotal;

    @NotNull
    private Double total;

    @NotNull
    private OffsetDateTime fecha;

    @NotNull
    private Integer miembro;

    @NotNull
    private Integer estadoPedido;

    @NotNull
    private Integer sedeRestaurante;

    @NotNull
    private Integer usuario;

}
