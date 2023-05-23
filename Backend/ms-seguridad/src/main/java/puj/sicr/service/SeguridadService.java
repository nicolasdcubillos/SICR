package puj.sicr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import puj.sicr.dto.InicioSesionDto;
import puj.sicr.dto.RespuestaInicioSesionDto;
import puj.sicr.entidad.Usuario;
import puj.sicr.repository.UsuarioRepository;
import puj.sicr.security.jwt.JwtProvider;
import puj.sicr.security.service.UserDetailsServiceImpl;
import puj.sicr.vo.RespuestaServicioVO;

@Service
public class SeguridadService {

    Logger logger = LoggerFactory.getLogger(SeguridadService.class);

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private UserDetailsServiceImpl jwtService;

    @Autowired
    private JwtProvider jwtProvider;

    public RespuestaServicioVO iniciarSesion (InicioSesionDto inicioSesionDto) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        String username = inicioSesionDto.getUsername();
        String password = inicioSesionDto.getPassword();
        Usuario usuario = repository.findByUsername(username.toUpperCase());
        if (usuario != null) {
            if (usuario.getPassword().equals(password)) {
                RespuestaInicioSesionDto respuestaObj = new RespuestaInicioSesionDto();
                respuestaObj.setNombres(usuario.getNombres());
                respuestaObj.setApellidos(usuario.getApellidos());
                respuestaObj.setUsuarioId(usuario.getId().toString());
                respuestaObj.setRolId(usuario.getTipoUsuario().getId().toString());
                respuestaObj.setRolNombre(usuario.getTipoUsuario().getRol());
                respuestaObj.setUsername(usuario.getUsername());

                final UserDetails userDetails = jwtService.loadUserByUsername(usuario.getUsername());
                final String token = jwtProvider.generateToken(userDetails, usuario);

                respuestaObj.setToken(token);
                respuesta.setObjeto(respuestaObj);
            } else {
                respuesta.setObjeto(null);
                respuesta.setExitosa(true);
                respuesta.setDescripcionRespuesta("Contraseña incorrecta.");
            }
        } else {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionRespuesta("No se encontró un usuario con el username " + username);
        }
        return respuesta;
    }
}
