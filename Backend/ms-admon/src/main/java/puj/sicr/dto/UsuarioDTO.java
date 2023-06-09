package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String username;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 255)
    private String nombres;

    @NotNull
    @Size(max = 255)
    private String apellidos;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String telefono;

    @NotNull
    @Size(max = 255)
    private String direccion;

    @NotNull
    @Size(max = 255)
    private String latitud;

    @NotNull
    @Size(max = 255)
    private String longitud;

    @NotNull
    private Integer tipoUsuario;

}
