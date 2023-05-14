package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.Categoria;
import puj.sicr.service.CategoriaService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("Categoria")
public class CategoriaController {
    private static final String origen = "*";

    @Autowired
    private CategoriaService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody Categoria categoria) {
        return service.crear(categoria);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody Categoria categoria) {
        return service.actualizar(categoria);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
}
