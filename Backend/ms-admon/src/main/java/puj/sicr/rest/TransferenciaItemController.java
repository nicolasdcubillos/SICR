package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.dto.SolicitarInventarioDto;
import puj.sicr.entidad.TransferenciaItem;
import puj.sicr.service.TransferenciaItemService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("TransferenciaItem")
public class TransferenciaItemController {
    private static final String origen = "*";

    @Autowired
    private TransferenciaItemService service;

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
    public @ResponseBody RespuestaServicioVO crear(@RequestBody TransferenciaItem transferenciaItem) {
        return service.crear(transferenciaItem);

    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizar(@RequestBody TransferenciaItem transferenciaItem) {
        return service.actualizar(transferenciaItem);
    }

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/eliminar", method = RequestMethod.GET)
    public @ResponseBody RespuestaServicioVO eliminar(Integer id) {
        return service.eliminar(id);
    }
    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/solicitarInventario", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO solicitarInventario(@RequestBody SolicitarInventarioDto solicitarInventarioDto) {
        return service.solicitarInventario(solicitarInventarioDto);
    }
    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/actualizarEstadoTransferencia", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO actualizarEstadoTransferencia(Integer id) {
        return service.actualizarEstadoTransferencia(id);
    }
}
