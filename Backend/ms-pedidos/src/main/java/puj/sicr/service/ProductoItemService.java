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
import puj.sicr.entidad.ProductoItem;
import puj.sicr.repository.ProductoItemRepository;
import puj.sicr.vo.RespuestaServicioVO;

@Service
public class ProductoItemService {

    Logger logger = LoggerFactory.getLogger(ProductoItemService.class);

    @Autowired
    private ProductoItemRepository repository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            ProductoItem productoItem = repository.findById(id).get();
            respuesta.setObjeto(productoItem);
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
            Iterable<ProductoItem> coldatos = repository.findAll();
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

            Iterable<ProductoItem> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(ProductoItem productoItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(productoItem);
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
    public RespuestaServicioVO crearTX(ProductoItem productoItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(productoItem);
        respuesta.setObjeto(productoItem);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(ProductoItem productoItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(productoItem);
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
    public RespuestaServicioVO actualizarTX(ProductoItem productoItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        ProductoItem aux = null;
        aux = repository.findById(productoItem.getId()).get();
        if (aux != null) {
            productoItem.setId(aux.getId());
            repository.save(productoItem);
            respuesta.setObjeto(productoItem);
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
}
