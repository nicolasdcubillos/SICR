package puj.sicr.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ItemSedeRestaurante {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "itemId", nullable = false)
    @JsonIgnore
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sedeRestauranteId", nullable = false)
    @JsonIgnore
    private SedeRestaurante sedeRestaurante;

}
