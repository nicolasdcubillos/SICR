package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class RealizarPedidoDTO {
    private Integer id;

    @NotNull
    private Double subtotal;

    @NotNull
    private Double total;

    @NotNull
    private OffsetDateTime fecha;

    private Integer miembro;

    @NotNull
    private Integer sedeRestaurante;

    @NotNull
    private Integer usuario;

    @NotNull
    private List<PedidoProductoDTO> pedidoProductos;
}
