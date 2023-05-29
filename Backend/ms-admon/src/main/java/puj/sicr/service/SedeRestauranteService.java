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
import puj.sicr.dto.SedeRestauranteDTO;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.entidad.Restaurante;
import puj.sicr.repository.RestauranteRepository;
import puj.sicr.repository.SedeRestauranteRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class SedeRestauranteService {

    Logger logger = LoggerFactory.getLogger(SedeRestauranteService.class);

    @Autowired
    private SedeRestauranteRepository repository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            SedeRestaurante sedeRestaurante = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(sedeRestaurante));
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
            List<SedeRestaurante> coldatos = repository.findAll();
            List<SedeRestauranteDTO> respuestaObj = coldatos.stream().map((sedeRestaurante) -> mapToDTO(sedeRestaurante)).toList();
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

            Iterable<SedeRestaurante> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(SedeRestauranteDTO sedeRestauranteDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(sedeRestauranteDTO));
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
    public RespuestaServicioVO crearTX(SedeRestaurante sedeRestaurante) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(sedeRestaurante);
        respuesta.setObjeto(sedeRestaurante);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(SedeRestauranteDTO sedeRestauranteDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(sedeRestauranteDTO));
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
    public RespuestaServicioVO actualizarTX(SedeRestaurante sedeRestaurante) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        SedeRestaurante aux = null;
        aux = repository.findById(sedeRestaurante.getId()).get();
        if (aux != null) {
            sedeRestaurante.setId(aux.getId());
            repository.save(sedeRestaurante);
            respuesta.setObjeto(sedeRestaurante);
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

    private SedeRestauranteDTO mapToDTO(final SedeRestaurante sedeRestaurante) {
        SedeRestauranteDTO sedeRestauranteDTO = new SedeRestauranteDTO();
        sedeRestauranteDTO.setId(sedeRestaurante.getId());
        sedeRestauranteDTO.setNombre(sedeRestaurante.getNombre());
        sedeRestauranteDTO.setDireccion(sedeRestaurante.getDireccion());
        sedeRestauranteDTO.setLatitud(sedeRestaurante.getLatitud());
        sedeRestauranteDTO.setLongitud(sedeRestaurante.getLongitud());
        sedeRestauranteDTO.setFechaApertura(sedeRestaurante.getFechaApertura());
        sedeRestauranteDTO.setFechaCierre(sedeRestaurante.getFechaCierre());
        sedeRestauranteDTO.setCapacidad(sedeRestaurante.getCapacidad());
        sedeRestauranteDTO.setRestaurante(sedeRestaurante.getRestaurante() == null ? null : sedeRestaurante.getRestaurante().getId());
        return sedeRestauranteDTO;
    }

    public SedeRestaurante mapToEntity(final SedeRestauranteDTO sedeRestauranteDTO) {
        SedeRestaurante sedeRestaurante = new SedeRestaurante();
        sedeRestaurante.setId(sedeRestauranteDTO.getId());
        sedeRestaurante.setNombre(sedeRestauranteDTO.getNombre());
        sedeRestaurante.setDireccion(sedeRestauranteDTO.getDireccion());
        sedeRestaurante.setLatitud(sedeRestauranteDTO.getLatitud());
        sedeRestaurante.setLongitud(sedeRestauranteDTO.getLongitud());
        sedeRestaurante.setFechaApertura(sedeRestauranteDTO.getFechaApertura());
        sedeRestaurante.setFechaCierre(sedeRestauranteDTO.getFechaCierre());
        sedeRestaurante.setCapacidad(sedeRestauranteDTO.getCapacidad());
        final Restaurante restaurante = sedeRestauranteDTO.getRestaurante() == null ? null : restauranteRepository.findById(sedeRestauranteDTO.getRestaurante()).get();
        sedeRestaurante.setRestaurante(restaurante);
        return sedeRestaurante;
    }
}
