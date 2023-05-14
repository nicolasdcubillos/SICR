package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.EstadoTransferenciaItem;
import puj.sicr.service.EstadoTransferenciaItemService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("EstadoTransferenciaItem")
public class EstadoTransferenciaItemController {
    private static final String origen = "*";

    @Autowired
    private EstadoTransferenciaItemService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody EstadoTransferenciaItem estadoTransferenciaItem) {
        return service.crear(estadoTransferenciaItem);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody EstadoTransferenciaItem estadoTransferenciaItem) {
        return service.actualizar(estadoTransferenciaItem);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
}
