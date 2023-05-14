package puj.sicr.entidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Item {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double costoUnitario;

    @OneToMany(mappedBy = "item")
    private Set<TransferenciaItem> itemTransferenciaItems;

    @OneToMany(mappedBy = "item")
    private Set<ItemSedeRestaurante> itemItemSedeRestaurantes;

    @OneToMany(mappedBy = "item")
    private Set<ProductoItem> itemProductoItems;

}
