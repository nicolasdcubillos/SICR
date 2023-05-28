package puj.sicr.service;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puj.sicr.dto.MenuProductoDTO;
import puj.sicr.entidad.*;
import puj.sicr.repository.MenuProductoRepository;
import puj.sicr.repository.MenuRepository;
import puj.sicr.repository.ProductoRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuProductoService {

    Logger logger = LoggerFactory.getLogger(MenuProductoService.class);

    @Autowired
    private MenuProductoRepository repository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ProductoRepository productoRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            MenuProducto menuProducto = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(menuProducto));
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
            List<MenuProducto> coldatos = repository.findAll();
            List<MenuProductoDTO> respuestaObj = coldatos.stream().map((menuProducto) -> mapToDTO(menuProducto)).toList();
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

            Iterable<MenuProducto> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(MenuProductoDTO menuProductoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(menuProductoDTO));
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
    public RespuestaServicioVO crearTX(MenuProducto menuProducto) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(menuProducto);
        respuesta.setObjeto(menuProducto);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(MenuProductoDTO menuProductoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(menuProductoDTO));
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
    public RespuestaServicioVO actualizarTX(MenuProducto menuProducto) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        MenuProducto aux = null;
        aux = repository.findById(menuProducto.getId()).get();
        if (aux != null) {
            menuProducto.setId(aux.getId());
            repository.save(menuProducto);
            respuesta.setObjeto(menuProducto);
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

    private MenuProductoDTO mapToDTO(final MenuProducto menuProducto) {
        MenuProductoDTO menuProductoDTO = new MenuProductoDTO();
        menuProductoDTO.setId(menuProducto.getId());
        menuProductoDTO.setProducto(menuProducto.getProducto() == null ? null : menuProducto.getProducto().getId());
        menuProductoDTO.setMenu(menuProducto.getMenu() == null ? null : menuProducto.getMenu().getId());
        return menuProductoDTO;
    }

    private MenuProducto mapToEntity(final MenuProductoDTO menuProductoDTO) {
        MenuProducto menuProducto = new MenuProducto();
        menuProducto.setId(menuProductoDTO.getId());
        final Producto producto = menuProductoDTO.getProducto() == null ? null : productoRepository.findById(menuProductoDTO.getProducto()).get();
        menuProducto.setProducto(producto);
        final Menu menu = menuProductoDTO.getMenu() == null ? null : menuRepository.findById(menuProductoDTO.getMenu()).get();
        menuProducto.setMenu(menu);
        return menuProducto;
    }

    public RespuestaServicioVO getBySedeRestauranteId(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            List<MenuProducto> coldatos = repository.findBySedeRestauranteId(id);
            List<MenuProductoDTO> respuestaObj = coldatos.stream().map((menuProducto) -> mapToDTO(menuProducto)).toList();
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

    public RespuestaServicioVO getByMenuId(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            Menu menu = menuRepository.getById(id);
            Hibernate.initialize(menu.getMenuMenuProductos());

            List<MenuProducto> menuProducto = menu.getMenuMenuProductos();
            List<Producto> productoMenu = new ArrayList();
            for (MenuProducto menuP:menuProducto) {
                Producto producto = menuP.getProducto();
                if (producto instanceof HibernateProxy) {
                    producto = (Producto) ((HibernateProxy) producto).getHibernateLazyInitializer().getImplementation();
                }
                productoMenu.add(producto);
            }
            respuesta.setObjeto(productoMenu);
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
}
