package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.dto.InicioSesionDto;
import puj.sicr.service.SeguridadService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("/Seguridad")
public class SeguridadController {
    private static final String origen = "*";

    @Autowired
    private SeguridadService service;

    @CrossOrigin(origins = origen)
    @RequestMapping(value = "/IniciarSesion", method = RequestMethod.POST)
    public @ResponseBody RespuestaServicioVO iniciarSesion(@RequestBody InicioSesionDto inicioSesionDto) {
        return service.iniciarSesion(inicioSesionDto);
    }


}
