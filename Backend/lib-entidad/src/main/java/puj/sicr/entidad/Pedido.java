package puj.sicr.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double total;

    @Column(nullable = false)
    private OffsetDateTime fecha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miembroId", nullable = false)
    private Miembro miembro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoPedidoId", nullable = false)
    private EstadoPedido estadoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sedeRestauranteId", nullable = false)
    private SedeRestaurante sedeRestaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido")
    private List<ProductoPedido> pedidoProductoPedidos;

}
