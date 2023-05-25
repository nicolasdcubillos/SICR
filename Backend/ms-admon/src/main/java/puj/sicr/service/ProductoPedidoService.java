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
import puj.sicr.dto.ProductoPedidoDTO;
import puj.sicr.entidad.Pedido;
import puj.sicr.entidad.Producto;
import puj.sicr.entidad.ProductoPedido;
import puj.sicr.repository.PedidoRepository;
import puj.sicr.repository.ProductoPedidoRepository;
import puj.sicr.repository.ProductoRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class ProductoPedidoService {

    Logger logger = LoggerFactory.getLogger(ProductoPedidoService.class);

    @Autowired
    private ProductoPedidoRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            ProductoPedido productoPedido = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(productoPedido));
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
            List<ProductoPedido> coldatos = repository.findAll();
            List<ProductoPedidoDTO> respuestaObj = coldatos.stream().map((productoPedido) -> mapToDTO(productoPedido)).toList();
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

            Iterable<ProductoPedido> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(ProductoPedidoDTO productoPedidoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(productoPedidoDTO));
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
    public RespuestaServicioVO crearTX(ProductoPedido productoPedido) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(productoPedido);
        respuesta.setObjeto(productoPedido);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(ProductoPedidoDTO productoPedidoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(productoPedidoDTO));
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
    public RespuestaServicioVO actualizarTX(ProductoPedido productoPedido) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        ProductoPedido aux = null;
        aux = repository.findById(productoPedido.getId()).get();
        if (aux != null) {
            productoPedido.setId(aux.getId());
            repository.save(productoPedido);
            respuesta.setObjeto(productoPedido);
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

    private ProductoPedidoDTO mapToDTO(final ProductoPedido productoPedido) {
        ProductoPedidoDTO productoPedidoDTO = new ProductoPedidoDTO();
        productoPedidoDTO.setId(productoPedido.getId());
        productoPedidoDTO.setCantidad(productoPedido.getCantidad());
        productoPedidoDTO.setProducto(productoPedido.getProducto() == null ? null : productoPedido.getProducto().getId());
        productoPedidoDTO.setPedido(productoPedido.getPedido() == null ? null : productoPedido.getPedido().getId());
        return productoPedidoDTO;
    }

    private ProductoPedido mapToEntity(final ProductoPedidoDTO productoPedidoDTO) {
        ProductoPedido productoPedido = new ProductoPedido();
        productoPedido.setCantidad(productoPedidoDTO.getCantidad());
        final Producto producto = productoPedidoDTO.getProducto() == null ? null : productoRepository.findById(productoPedidoDTO.getProducto()).get();
        productoPedido.setProducto(producto);
        final Pedido pedido = productoPedidoDTO.getPedido() == null ? null : pedidoRepository.findById(productoPedidoDTO.getPedido()).get();
        productoPedido.setPedido(pedido);
        return productoPedido;
    }
}
