package puj.sicr.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import java.util.List;
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
    @JsonIgnore
     private List<TransferenciaItem> itemTransferenciaItems;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
     private List<ItemSedeRestaurante> itemItemSedeRestaurantes;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
     private List<ProductoItem> itemProductoItems;

}
