package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.service.SedeRestauranteService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("SedeRestaurante")
public class SedeRestauranteController {
    private static final String origen = "*";

    @Autowired
    private SedeRestauranteService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody SedeRestaurante sedeRestaurante) {
        return service.crear(sedeRestaurante);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody SedeRestaurante sedeRestaurante) {
        return service.actualizar(sedeRestaurante);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
}
