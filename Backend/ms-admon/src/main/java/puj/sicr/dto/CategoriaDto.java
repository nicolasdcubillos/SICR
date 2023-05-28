package puj.sicr.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoriaDto implements Serializable {
    private Integer id;
    private String nombre;
    private List<ProductoDTO> productos;
}
