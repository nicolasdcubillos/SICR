package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;

// Ejemplo de Data Transfer Object
@Data
public class CategoriaDto implements Serializable {
    private String nombreCategoria;
    private String tipo;
}
