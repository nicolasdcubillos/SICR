package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.dto.ProductoItemDTO;
import puj.sicr.entidad.ProductoItem;
import puj.sicr.service.ProductoItemService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("ProductoItem")
public class ProductoItemController {
    private static final String origen = "*";

    @Autowired
    private ProductoItemService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody ProductoItemDTO productoItem) {
        return service.crear(productoItem);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody ProductoItemDTO productoItem) {
        return service.actualizar(productoItem);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/getByProductoId", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO getByProductoId(Integer id) {
        return service.getByProductoId(id);

    }

}
