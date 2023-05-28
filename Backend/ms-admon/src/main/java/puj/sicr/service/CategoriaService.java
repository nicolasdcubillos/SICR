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
import puj.sicr.dto.CategoriaDto;
import puj.sicr.dto.ProductoDTO;
import puj.sicr.entidad.Categoria;
import puj.sicr.entidad.Menu;
import puj.sicr.repository.CategoriaRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    Logger logger = LoggerFactory.getLogger(CategoriaService.class);

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProductoService productoService;
    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Categoria categoria = repository.findById(id).get();
            respuesta.setObjeto(categoria);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("Transacción exitosa.");
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
            List<Categoria> coldatos = repository.findAll();
            List<CategoriaDto> respuestaObj = new ArrayList<>();
            for (Categoria categoria : coldatos) {
                CategoriaDto categoriaDto = new CategoriaDto();
                categoriaDto.setId(categoria.getId());
                categoriaDto.setNombre(categoria.getNombre());
                RespuestaServicioVO respuestaProductos = productoService.getByCategoriaId(categoria.getId());
                if (respuestaProductos.getExitosa()) {
                    List<ProductoDTO> productos = (List<ProductoDTO>) respuestaProductos.getObjeto();
                    categoriaDto.setProductos(productos);
                    respuesta.setObjeto(respuestaObj);
                    respuesta.setExitosa(true);
                    respuesta.setDescripcionRespuesta("La transacción fue exitosa.");
                } else {
                    return respuestaProductos;
                }
                respuestaObj.add(categoriaDto);
            }
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

            Iterable<Categoria> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(Categoria categoria) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(categoria);
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
    public RespuestaServicioVO crearTX(Categoria categoria) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(categoria);
        respuesta.setObjeto(categoria);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(Categoria categoria) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(categoria);
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
    public RespuestaServicioVO actualizarTX(Categoria categoria) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        Categoria aux = null;
        aux = repository.findById(categoria.getId()).get();
        if (aux != null) {
            categoria.setId(aux.getId());
            repository.save(categoria);
            respuesta.setObjeto(categoria);
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
