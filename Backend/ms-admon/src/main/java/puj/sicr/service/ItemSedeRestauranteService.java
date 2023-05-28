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
import puj.sicr.dto.ItemSedeDTO;
import puj.sicr.dto.ItemSedeRestauranteDTO;
import puj.sicr.dto.SolicitarInventarioDto;
import puj.sicr.entidad.Item;
import puj.sicr.entidad.ItemSedeRestaurante;
import puj.sicr.entidad.Restaurante;
import puj.sicr.entidad.SedeRestaurante;
import puj.sicr.repository.ItemRepository;
import puj.sicr.repository.ItemSedeRestauranteRepository;
import puj.sicr.repository.SedeRestauranteRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemSedeRestauranteService {

    Logger logger = LoggerFactory.getLogger(ItemSedeRestauranteService.class);

    @Autowired
    private ItemSedeRestauranteRepository repository;

    @Autowired
    private SedeRestauranteRepository sedeRestauranteRepository;

    @Autowired
    private ItemRepository itemRepository;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            ItemSedeRestaurante itemSedeRestaurante = repository.findById(id).get();
            respuesta.setObjeto(mapToDTO(itemSedeRestaurante));
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
            List<ItemSedeRestaurante> coldatos = repository.findAll();
            List<ItemSedeRestauranteDTO> respuestaObj = coldatos.stream().map((itemSedeRestaurante) -> mapToDTO(itemSedeRestaurante)).toList();
            respuesta.setObjeto(respuestaObj);
            respuesta.setExitosa(true);
        } catch (Exception e) {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionExcepcion(e.getMessage());
            logger.error(e.getMessage());
        }
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

            Iterable<ItemSedeRestaurante> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(ItemSedeRestauranteDTO itemSedeRestauranteDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(mapToEntity(itemSedeRestauranteDTO));
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
    public RespuestaServicioVO crearTX(ItemSedeRestaurante itemSedeRestaurante) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(itemSedeRestaurante);
        respuesta.setObjeto(itemSedeRestaurante);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(ItemSedeRestauranteDTO itemSedeRestauranteDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(mapToEntity(itemSedeRestauranteDTO));
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
    public RespuestaServicioVO actualizarTX(ItemSedeRestaurante itemSedeRestaurante) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        ItemSedeRestaurante aux = null;
        aux = repository.findById(itemSedeRestaurante.getId()).get();
        if (aux != null) {
            itemSedeRestaurante.setId(aux.getId());
            repository.save(itemSedeRestaurante);
            respuesta.setObjeto(itemSedeRestaurante);
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

    public RespuestaServicioVO findByItemIdDisponiblesAndRestauranteId(Integer id, Integer restauranteId, Integer cantidad) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            List <ItemSedeRestaurante> itemSedeRestaurante = repository.findByItemIdDisponiblesAndRestauranteId(id, restauranteId, cantidad);
            respuesta.setObjeto(itemSedeRestaurante);
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

    public RespuestaServicioVO getByItemIdAndSedeRestauranteId(Integer itemId, Integer sedeRestauranteId) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            ItemSedeRestaurante itemSedeRestaurante = repository.getByItemIdAndSedeRestauranteId(itemId, sedeRestauranteId);
            respuesta.setObjeto(itemSedeRestaurante);
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

    private ItemSedeRestauranteDTO mapToDTO(final ItemSedeRestaurante itemSedeRestaurante) {
        ItemSedeRestauranteDTO itemSedeRestauranteDTO = new ItemSedeRestauranteDTO();
        itemSedeRestauranteDTO.setId(itemSedeRestaurante.getId());
        itemSedeRestauranteDTO.setCantidad(itemSedeRestaurante.getCantidad());
        itemSedeRestauranteDTO.setItem(itemSedeRestaurante.getItem() == null ? null : itemSedeRestaurante.getItem().getId());
        itemSedeRestauranteDTO.setSedeRestaurante(itemSedeRestaurante.getSedeRestaurante() == null ? null : itemSedeRestaurante.getSedeRestaurante().getId());
        return itemSedeRestauranteDTO;
    }

    private ItemSedeRestaurante mapToEntity(final ItemSedeRestauranteDTO itemSedeRestauranteDTO) {
        ItemSedeRestaurante itemSedeRestaurante = new ItemSedeRestaurante();
        itemSedeRestaurante.setId(itemSedeRestauranteDTO.getId());
        itemSedeRestaurante.setCantidad(itemSedeRestauranteDTO.getCantidad());
        final Item item = itemSedeRestauranteDTO.getItem() == null ? null : itemRepository.findById(itemSedeRestauranteDTO.getItem()).get();
        itemSedeRestaurante.setItem(item);
        final SedeRestaurante sedeRestaurante = itemSedeRestauranteDTO.getSedeRestaurante() == null ? null : sedeRestauranteRepository.findById(itemSedeRestauranteDTO.getSedeRestaurante()).get();
        itemSedeRestaurante.setSedeRestaurante(sedeRestaurante);
        return itemSedeRestaurante;
    }

    public RespuestaServicioVO getBySedeId(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            List<ItemSedeRestaurante> coldatos = repository.getBySedeId(id);
            List<ItemSedeDTO> respuestaObj = new ArrayList();
            for (ItemSedeRestaurante ItemSede: coldatos) {
                ItemSedeDTO itemSedeDTO = new ItemSedeDTO();
                itemSedeDTO.setId(ItemSede.getId());
                itemSedeDTO.setCantidad(ItemSede.getCantidad());
                itemSedeDTO.setItem(ItemSede.getItem().getId());
                itemSedeDTO.setSedeRestaurante(ItemSede.getSedeRestaurante().getId());
                itemSedeDTO.setNombre(ItemSede.getItem().getNombre());
                itemSedeDTO.setCostoUnitario(ItemSede.getItem().getCostoUnitario());
                respuestaObj.add(itemSedeDTO);
            }
            respuesta.setObjeto(respuestaObj);
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
