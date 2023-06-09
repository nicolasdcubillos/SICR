package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.EstadoPedido;
import puj.sicr.service.EstadoPedidoService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("EstadoPedido")
public class EstadoPedidoController {
    private static final String origen = "*";

    @Autowired
    private EstadoPedidoService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody EstadoPedido estadoPedido) {
        return service.crear(estadoPedido);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody EstadoPedido estadoPedido) {
        return service.actualizar(estadoPedido);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
}
