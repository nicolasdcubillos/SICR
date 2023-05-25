package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MesaDTO {

    private Integer id;

    @NotNull
    private Integer asientos;

}
