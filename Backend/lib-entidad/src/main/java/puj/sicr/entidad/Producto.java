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
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

import java.util.List;
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
    private String nombre;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
     private List<MenuProducto> productoMenuProductos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoriaId", nullable = false)
    @JsonIgnore
    private Categoria categoria;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
     private List<ProductoPedido> productoProductoPedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estadoProductoId", nullable = false)
    @JsonIgnore
    private EstadoProducto estadoProducto;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
     private List<ProductoItem> productoProductoItems;

    @Column(nullable = false)
    private Double precio;

}
