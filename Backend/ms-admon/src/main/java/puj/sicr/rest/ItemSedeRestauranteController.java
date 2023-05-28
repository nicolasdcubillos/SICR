package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.dto.ItemSedeRestauranteDTO;
import puj.sicr.dto.SolicitarInventarioDto;
import puj.sicr.entidad.Item;
import puj.sicr.entidad.ItemSedeRestaurante;
import puj.sicr.service.ItemSedeRestauranteService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("ItemSedeRestaurante")
public class ItemSedeRestauranteController {
    private static final String origen = "*";

    @Autowired
    private ItemSedeRestauranteService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody ItemSedeRestauranteDTO itemSedeRestaurante ) {
        return service.crear(itemSedeRestaurante);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody ItemSedeRestauranteDTO itemSedeRestaurante ) {
        return service.actualizar(itemSedeRestaurante);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/getBySedeId", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO getBySedeId(Integer id) {
        return service.getBySedeId(id);
    }

}
