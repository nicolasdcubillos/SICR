package puj.sicr.dto;

import lombok.Data;

@Data
public class ItemSedeDTO {
    private Integer id;
    private Integer cantidad;
    private Integer item;
    private Integer sedeRestaurante;
    private String nombre;
    private Double costoUnitario;
}
