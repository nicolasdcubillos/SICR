package puj.sicr.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReservaMesaDTO {

    private Integer id;

    @NotNull
    private Integer reserva;

    @NotNull
    private Integer mesa;

}
