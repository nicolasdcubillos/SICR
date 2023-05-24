package puj.sicr.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class TransferenciaItem {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoTransferenciaItemId", nullable = false)
    private EstadoTransferenciaItem estadoTransferenciaItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sedeRestaurantedestinoId", nullable = false)
    private SedeRestaurante sedeRestauranteDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sedeRestauranteOrigenId", nullable = false)
    private SedeRestaurante sedeRestauranteOrigen;

}
