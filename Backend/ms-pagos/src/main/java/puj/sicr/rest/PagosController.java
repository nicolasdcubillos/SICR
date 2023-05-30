package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.Categoria;
import puj.sicr.service.PagosService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("Pagos")
public class PagosController {
    private static final String origen = "*";

    @Autowired
    private PagosService service;

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/pagar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO pagar() {
        return service.pagar();
    }

}
