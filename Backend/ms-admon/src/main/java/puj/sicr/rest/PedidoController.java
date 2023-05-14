package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.Pedido;
import puj.sicr.service.PedidoService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("Pedido")
public class PedidoController {
    private static final String origen = "*";

    @Autowired
    private PedidoService service;

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO getById(Integer id) {
        return service.getById(id);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO getAll() {
        return service.getAll();
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/crear", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO crear(@RequestBody Pedido pedido) {
        return service.crear(pedido);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody Pedido pedido) {
        return service.actualizar(pedido);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
}
