package puj.sicr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puj.sicr.entidad.Categoria;
import puj.sicr.service.CategoriaService;
import puj.sicr.service.UsuarioService;
import puj.sicr.vo.RespuestaServicioVO;


@RestController
@RequestMapping("Acceso")
public class SeguridadController {
    private static final String origen = "*";

    @Autowired
    private UsuarioService service;

}
