package puj.sicr.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @OneToMany(mappedBy = "sedeRestaurante")
    @JsonIgnore
    private List<Miembro> sedeRestauranteMiembros;

    @OneToMany(mappedBy = "sedeRestauranteDestino")
    @JsonIgnore
    private List<TransferenciaItem> sedeRestauranteDestinoTransferenciaItems;

    @OneToMany(mappedBy = "sedeRestauranteOrigen")
    @JsonIgnore
    private List<TransferenciaItem> sedeRestauranteOrigenTransferenciaItems;

    @OneToMany(mappedBy = "sedeRestaurante")
    @JsonIgnore
    private List<Menu> sedeRestauranteMenus;

    @OneToMany(mappedBy = "sedeRestaurante")
    @JsonIgnore
    private List<ItemSedeRestaurante> sedeRestauranteItemSedeRestaurantes;

    @OneToMany(mappedBy = "sedeRestaurante")
    @JsonIgnore
    private List<Pedido> sedeRestaurantePedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restauranteId", nullable = false)
    @JsonIgnore
    private Restaurante restaurante;

    @OneToMany(mappedBy = "sedeRestaurante")
    @JsonIgnore
    private List<Reserva> sedeRestauranteReservas;

}
