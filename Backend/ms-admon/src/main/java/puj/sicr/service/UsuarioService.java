package puj.sicr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puj.sicr.dto.UsuarioDTO;
import puj.sicr.entidad.TipoUsuario;
import puj.sicr.entidad.Usuario;
import puj.sicr.repository.TipoUsuarioRepository;
import puj.sicr.repository.UsuarioRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class UsuarioService {

    Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Usuario usuario = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(usuario));
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("La transacción fue exitosa.");
        } catch (DataAccessException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            logger.error(e.getMessage());
        } catch (Exception e) {

            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        return respuesta;
    }

    public RespuestaServicioVO getAll() {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            List <Usuario> coldatos = repository.findAll();
            List <UsuarioDTO> respuestaObj = coldatos.stream().map((usuario) -> mapToDTO(usuario)).toList();
            respuesta.setObjeto(respuestaObj);
            respuesta.setExitosa(true);
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        ;
        return respuesta;
    }

    public RespuestaServicioVO getByPage(Integer numeroPagina, Integer registrosPorPagina, String ordenarPor,
                                         Boolean ordenAscendente) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Pageable pageable = null;
            if (numeroPagina == 0) {
                respuesta.setCantidadTotal(repository.count());
            }
            if (ordenAscendente) {
                pageable = PageRequest.of(numeroPagina.intValue(), registrosPorPagina.intValue(),
                        Sort.by(ordenarPor).ascending());
            } else {
                pageable = PageRequest.of(numeroPagina.intValue(), registrosPorPagina.intValue(),
                        Sort.by(ordenarPor).descending());
            }

            Iterable<Usuario> page = repository.getByPage(pageable);
            respuesta.setObjeto(page);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("La transaccion fue exitosa");
        } catch (DataAccessException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        return respuesta;
    }

    public RespuestaServicioVO crear(UsuarioDTO usuarioDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            usuarioDTO.setUsername(usuarioDTO.getUsername().toUpperCase());
            respuesta = crearTX(mapToEntity(usuarioDTO));
        } catch (DataAccessException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage() + " - " + e.getRootCause().getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        return respuesta;
    }

    @Transactional(rollbackFor = Exception.class)
    public RespuestaServicioVO crearTX(Usuario usuario) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(usuario);
        respuesta.setObjeto(usuario);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(UsuarioDTO usuarioDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(usuarioDTO));
        } catch (DataAccessException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage() + " - " + e.getRootCause().getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        return respuesta;
    }

    @Transactional(rollbackFor = Exception.class)
    public RespuestaServicioVO actualizarTX(Usuario usuario) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        Usuario aux = null;
        aux = repository.findById(usuario.getId()).get();
        if (aux != null) {
            usuario.setId(aux.getId());
            repository.save(usuario);
            respuesta.setObjeto(usuario);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("Transacción exitosa.");

        } else {
            respuesta.setObjeto(aux);
            respuesta.setExitosa(false);
            respuesta.setDescripcionRespuesta("No se encontró la entidad.");
        }
        return respuesta;
    }

    public RespuestaServicioVO eliminar(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = eliminarTX(id);
        } catch (DataAccessException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionRespuesta("Error integridad SQL.");
            respuesta.setDescripcionExcepcion(e.getMessage() + " - " + e.getRootCause().getMessage());
            logger.error(e.getMessage());
        } catch (IllegalArgumentException e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionRespuesta("Entidad nulo, error.");
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
        return respuesta;
    }

    @Transactional(rollbackFor = Exception.class)
    public RespuestaServicioVO eliminarTX(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.deleteById(id);
        respuesta.setObjeto(null);
        respuesta.setExitosa(true);
        respuesta.setDescripcionRespuesta("Transacción exitosa.");
        return respuesta;
    }

    private UsuarioDTO mapToDTO(final Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setNombres(usuario.getNombres());
        usuarioDTO.setApellidos(usuario.getApellidos());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setTelefono(usuario.getTelefono());
        usuarioDTO.setDireccion(usuario.getDireccion());
        usuarioDTO.setLatitud(usuario.getLatitud());
        usuarioDTO.setLongitud(usuario.getLongitud());
        usuarioDTO.setTipoUsuario(usuario.getTipoUsuario() == null ? null : usuario.getTipoUsuario().getId());
        return usuarioDTO;
    }

    private Usuario mapToEntity(final UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setNombres(usuarioDTO.getNombres());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setLatitud(usuarioDTO.getLatitud());
        usuario.setLongitud(usuarioDTO.getLongitud());
        final TipoUsuario tipoUsuario = usuarioDTO.getTipoUsuario() == null ? null : tipoUsuarioRepository.findById(usuarioDTO.getTipoUsuario()).get();
        usuario.setTipoUsuario(tipoUsuario);
        return usuario;
    }
}
