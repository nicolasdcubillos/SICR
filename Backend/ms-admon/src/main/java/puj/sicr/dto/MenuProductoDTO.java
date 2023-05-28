package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuProductoDTO {

    private Integer id;

    @NotNull
    private Integer producto;

    @NotNull
    private Integer menu;

}
