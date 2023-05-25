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
import puj.sicr.dto.MiembroDTO;
import puj.sicr.entidad.Miembro;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.entidad.TipoMiembro;
import puj.sicr.repository.MiembroRepository;
import puj.sicr.repository.SedeRestauranteRepository;
import puj.sicr.repository.TipoMiembroRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class MiembroService {

    Logger logger = LoggerFactory.getLogger(MiembroService.class);

    @Autowired
    private MiembroRepository repository;

    @Autowired
    private SedeRestauranteRepository sedeRestauranteRepository;

    @Autowired
    private TipoMiembroRepository tipoMiembroRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Miembro miembro = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(miembro));
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
            List<Miembro> coldatos = repository.findAll();
            List<MiembroDTO> respuestaObj = coldatos.stream().map((miembro) -> mapToDTO(miembro)).toList();
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

            Iterable<Miembro> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(MiembroDTO miembroDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(miembroDTO));
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
    public RespuestaServicioVO crearTX(Miembro miembro) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(miembro);
        respuesta.setObjeto(miembro);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(MiembroDTO miembroDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(miembroDTO));
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
    public RespuestaServicioVO actualizarTX(Miembro miembro) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        Miembro aux = null;
        aux = repository.findById(miembro.getId()).get();
        if (aux != null) {
            miembro.setId(aux.getId());
            repository.save(miembro);
            respuesta.setObjeto(miembro);
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

    private MiembroDTO mapToDTO(final Miembro miembro) {
        MiembroDTO miembroDTO = new MiembroDTO();
        miembroDTO.setId(miembro.getId());
        miembroDTO.setNombre(miembro.getNombre());
        miembroDTO.setApellido(miembro.getApellido());
        miembroDTO.setSalario(miembro.getSalario());
        miembroDTO.setTelefono(miembro.getTelefono());
        miembroDTO.setSedeRestaurante(miembro.getSedeRestaurante() == null ? null : miembro.getSedeRestaurante().getId());
        miembroDTO.setTipoMiembro(miembro.getTipoMiembro() == null ? null : miembro.getTipoMiembro().getId());
        return miembroDTO;
    }

    private Miembro mapToEntity(final MiembroDTO miembroDTO) {
        Miembro miembro = new Miembro();
        miembro.setId(miembroDTO.getId());
        miembro.setNombre(miembroDTO.getNombre());
        miembro.setApellido(miembroDTO.getApellido());
        miembro.setSalario(miembroDTO.getSalario());
        miembro.setTelefono(miembroDTO.getTelefono());
        final SedeRestaurante sedeRestaurante = miembroDTO.getSedeRestaurante() == null ? null : sedeRestauranteRepository.findById(miembroDTO.getSedeRestaurante()).get();
        miembro.setSedeRestaurante(sedeRestaurante);
        final TipoMiembro tipoMiembro = miembroDTO.getTipoMiembro() == null ? null : tipoMiembroRepository.findById(miembroDTO.getTipoMiembro()).get();
        miembro.setTipoMiembro(tipoMiembro);
        return miembro;
    }
}
