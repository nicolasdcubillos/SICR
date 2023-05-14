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
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Producto {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private Integer sedeRestauranteId;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "producto")
    private Set<MenuProducto> productoMenuProductos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoriaId", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "producto")
    private Set<ProductoPedido> productoProductoPedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoProductoId", nullable = false)
    private EstadoProducto estadoProducto;

    @OneToMany(mappedBy = "producto")
    private Set<ProductoItem> productoProductoItems;

}
