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
import puj.sicr.dto.PedidoDTO;
import puj.sicr.entidad.Miembro;
import puj.sicr.entidad.Pedido;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.entidad.Usuario;
import puj.sicr.entidad.EstadoPedido;
import puj.sicr.repository.*;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class PedidoService {

    Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private SedeRestauranteRepository sedeRestauranteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Pedido pedido = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(pedido));
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
            List<Pedido> coldatos = repository.findAll();
            List<PedidoDTO> respuestaObj = coldatos.stream().map((pedido) -> mapToDTO(pedido)).toList();
            System.out.println(coldatos);
            respuesta.setObjeto(coldatos);
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

            Iterable<Pedido> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(PedidoDTO pedidoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(pedidoDTO));
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
    public RespuestaServicioVO crearTX(Pedido pedido) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(pedido);
        respuesta.setObjeto(pedido);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(PedidoDTO pedidoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(pedidoDTO));
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
    public RespuestaServicioVO actualizarTX(Pedido pedido) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        Pedido aux = null;
        aux = repository.findById(pedido.getId()).get();
        if (aux != null) {
            pedido.setId(aux.getId());
            repository.save(pedido);
            respuesta.setObjeto(pedido);
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

    private PedidoDTO mapToDTO(final Pedido pedido) {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(pedido.getId());
        pedidoDTO.setSubtotal(pedido.getSubtotal());
        pedidoDTO.setTotal(pedido.getTotal());
        pedidoDTO.setFecha(pedido.getFecha());
        pedidoDTO.setMiembro(pedido.getMiembro() == null ? null : pedido.getMiembro().getId());
        pedidoDTO.setEstadoPedido(pedido.getEstadoPedido() == null ? null : pedido.getEstadoPedido().getId());
        pedidoDTO.setSedeRestaurante(pedido.getSedeRestaurante() == null ? null : pedido.getSedeRestaurante().getId());
        pedidoDTO.setUsuario(pedido.getUsuario() == null ? null : pedido.getUsuario().getId());
        return pedidoDTO;
    }

    private Pedido mapToEntity(final PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setId(pedidoDTO.getId());
        pedido.setSubtotal(pedidoDTO.getSubtotal());
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setFecha(pedidoDTO.getFecha());
        final Miembro miembro = pedidoDTO.getMiembro() == null ? null : miembroRepository.findById(pedidoDTO.getMiembro()).get();
        pedido.setMiembro(miembro);
        final EstadoPedido estadoPedido = pedidoDTO.getEstadoPedido() == null ? null : estadoPedidoRepository.findById(pedidoDTO.getEstadoPedido()).get();
        pedido.setEstadoPedido(estadoPedido);
        final SedeRestaurante sedeRestaurante = pedidoDTO.getSedeRestaurante() == null ? null : sedeRestauranteRepository.findById(pedidoDTO.getSedeRestaurante()).get();
        pedido.setSedeRestaurante(sedeRestaurante);
        final Usuario usuario = pedidoDTO.getUsuario() == null ? null : usuarioRepository.findById(pedidoDTO.getUsuario()).get();
        pedido.setUsuario(usuario);
        return pedido;
    }

}
