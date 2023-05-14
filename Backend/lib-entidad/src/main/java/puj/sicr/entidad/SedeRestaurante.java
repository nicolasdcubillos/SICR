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
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class SedeRestaurante {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String latitud;

    @Column(nullable = false)
    private String longitud;

    @Column(nullable = false)
    private OffsetDateTime fechaApertura;

    @Column(nullable = false)
    private OffsetDateTime fechaCierre;

    @Column(nullable = false)
    private Integer capacidad;

    @OneToMany(mappedBy = "sedeRestauranteDestino")
    private Set<TransferenciaItem> sedeRestauranteDestinoTransferenciaItems;

    @OneToMany(mappedBy = "sedeRestauranteOrigen")
    private Set<TransferenciaItem> sedeRestauranteOrigenTransferenciaItems;

    @OneToMany(mappedBy = "sedeRestaurante")
    private Set<Menu> sedeRestauranteMenus;

    @OneToMany(mappedBy = "sedeRestaurante")
    private Set<ItemSedeRestaurante> sedeRestauranteItemSedeRestaurantes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "miembroId", nullable = false)
    private Miembro miembro;

    @OneToMany(mappedBy = "sedeRestaurante")
    private Set<Pedido> sedeRestaurantePedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restauranteId", nullable = false)
    private Restaurante restaurante;

    @OneToMany(mappedBy = "sedeRestaurante")
    private Set<Reserva> sedeRestauranteReservas;

}
