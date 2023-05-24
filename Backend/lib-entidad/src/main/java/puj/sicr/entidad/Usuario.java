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
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String latitud;

    @Column(nullable = false)
    private String longitud;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
     private List<Pedido> usuarioPedidos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoUsuarioId", nullable = false)
    @JsonIgnore
    private TipoUsuario tipoUsuario;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
     private List<Reserva> usuarioReservas;

}
