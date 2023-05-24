package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InicioSesionDto implements Serializable {
    private String username;
    private String password;
}
