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
import puj.sicr.dto.SolicitarInventarioDto;
import puj.sicr.dto.SolicitarInventarioRespuestaDto;
import puj.sicr.entidad.*;
import puj.sicr.repository.TransferenciaItemRepository;
import puj.sicr.vo.RespuestaServicioVO;

import java.util.List;

@Service
public class TransferenciaItemService {

    Logger logger = LoggerFactory.getLogger(TransferenciaItemService.class);

    @Autowired
    private TransferenciaItemRepository repository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private SedeRestauranteService sedeRestauranteService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemSedeRestauranteService itemSedeRestauranteService;

    @Autowired
    private EstadoTransferenciaItemService estadoTransferenciaItemService;

    public RespuestaServicioVO getById(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            TransferenciaItem transferenciaItem = repository.findById(id).get();
            respuesta.setObjeto(transferenciaItem);
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
            Iterable<TransferenciaItem> coldatos = repository.findAll();
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

            Iterable<TransferenciaItem> page = repository.getByPage(pageable);
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

    public RespuestaServicioVO crear(TransferenciaItem transferenciaItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = crearTX(transferenciaItem);
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
    public RespuestaServicioVO crearTX(TransferenciaItem transferenciaItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        repository.save(transferenciaItem);
        respuesta.setObjeto(transferenciaItem);
        respuesta.setExitosa(true);
        return respuesta;
    }

    public RespuestaServicioVO actualizar(TransferenciaItem transferenciaItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            respuesta = actualizarTX(transferenciaItem);
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
    public RespuestaServicioVO actualizarTX(TransferenciaItem transferenciaItem) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        TransferenciaItem aux = null;
        aux = repository.findById(transferenciaItem.getId()).get();
        if (aux != null) {
            transferenciaItem.setId(aux.getId());
            repository.save(transferenciaItem);
            respuesta.setObjeto(transferenciaItem);
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

    public RespuestaServicioVO solicitarInventario (SolicitarInventarioDto solicitarInventarioDto) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        RespuestaServicioVO respuestaRestaurante = restauranteService.getById(solicitarInventarioDto.getRestauranteId());

        if (respuestaRestaurante.getExitosa()) {
            Restaurante restaurante = (Restaurante) respuestaRestaurante.getObjeto();
            RespuestaServicioVO respuestaSedeRestaurante = sedeRestauranteService.getById(solicitarInventarioDto.getSedeOrigenId());
            if (respuestaSedeRestaurante.getExitosa()) {
                SedeRestaurante sedeRestauranteSolicitante = (SedeRestaurante) respuestaSedeRestaurante.getObjeto();
                RespuestaServicioVO respuestaItem = itemService.getById(solicitarInventarioDto.getItemId());
                if (respuestaItem.getExitosa()) {
                    Item item = (Item) respuestaItem.getObjeto();
                    if (solicitarInventarioDto.getCantidad() > 0) {
                        RespuestaServicioVO respuestaItemsSedesDisponibles
                                = itemSedeRestauranteService.findByItemIdDisponiblesAndRestauranteId
                                (item.getId(), solicitarInventarioDto.getRestauranteId(), solicitarInventarioDto.getCantidad());
                        if (respuestaItemsSedesDisponibles.getExitosa()) {
                            List<ItemSedeRestaurante> itemSedeRestaurantesDisponibles = (List<ItemSedeRestaurante>) respuestaItemsSedesDisponibles.getObjeto();
                            if (!itemSedeRestaurantesDisponibles.isEmpty()) {
                                SedeRestaurante sedeRestaurante = itemSedeRestaurantesDisponibles.get(0).getSedeRestaurante();

                                SolicitarInventarioRespuestaDto respuestaDto = new SolicitarInventarioRespuestaDto();
                                respuestaDto.setNombreSedeRestauranteSolicitante(sedeRestauranteSolicitante.getNombre());
                                respuestaDto.setNombreSedeRestaurante(sedeRestaurante.getNombre());
                                respuestaDto.setDireccion(sedeRestaurante.getDireccion());
                                respuestaDto.setCantidad(solicitarInventarioDto.getCantidad());
                                respuestaDto.setNombreItem(item.getNombre());

                                // Creando el registro en la tabla de TransferenciaItem con estado 1 - (Por enviar)

                                RespuestaServicioVO respuestaEstadoTransferenciaItem = estadoTransferenciaItemService.getById(1);
                                EstadoTransferenciaItem estadoTransferenciaItem = (EstadoTransferenciaItem) respuestaEstadoTransferenciaItem.getObjeto();

                                TransferenciaItem transferenciaItem = new TransferenciaItem();
                                transferenciaItem.setCantidad(solicitarInventarioDto.getCantidad());
                                transferenciaItem.setItem(item);
                                transferenciaItem.setEstadoTransferenciaItem(estadoTransferenciaItem);
                                transferenciaItem.setSedeRestauranteOrigen(sedeRestaurante);
                                transferenciaItem.setSedeRestauranteDestino(sedeRestauranteSolicitante);

                                RespuestaServicioVO respuestaCrearTransferenciaItem = this.crear(transferenciaItem);
                                if (respuestaCrearTransferenciaItem.getExitosa()) {
                                    respuesta.setObjeto(respuestaDto);
                                    respuesta.setExitosa(true);
                                    respuesta.setDescripcionRespuesta("Transacción exitosa.");
                                } else {
                                    return respuestaCrearTransferenciaItem;
                                }
                            } else {
                                respuesta.setObjeto(null);
                                respuesta.setExitosa(false);
                                respuesta.setDescripcionRespuesta("No hay restaurantes que contengan este item disponible para transferir en su inventario.");
                            }
                        } else {
                            return respuestaItemsSedesDisponibles;
                        }
                    } else {
                        respuesta.setObjeto(null);
                        respuesta.setExitosa(false);
                        respuesta.setDescripcionRespuesta("La cantidad a solicitar debe ser mayor a cero (0).");
                    }
                } else {
                    respuesta.setObjeto(null);
                    respuesta.setExitosa(true);
                    respuesta.setDescripcionRespuesta("No existe un item con el ID ingresado.");
                }
            } else {
                respuesta.setObjeto(null);
                respuesta.setExitosa(true);
                respuesta.setDescripcionRespuesta("No existe una sede restaurante con el ID ingresado.");
            }
        } else {
            respuesta.setObjeto(null);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("No existe un restaurante con el ID ingresado.");
        }
        return respuesta;
    }

    public RespuestaServicioVO actualizarEstadoTransferencia(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        RespuestaServicioVO respuestaTransferenciaItem = this.getById(id);
        if (respuestaTransferenciaItem.getExitosa()) {
            TransferenciaItem transferenciaItem = (TransferenciaItem) respuestaTransferenciaItem.getObjeto();
            if (transferenciaItem.getEstadoTransferenciaItem().getId() < 3) {
                Integer nuevoEstadoId = transferenciaItem.getEstadoTransferenciaItem().getId() + 1;

                if (nuevoEstadoId == 2) { // Enviado de la sede origen.
                    RespuestaServicioVO respuestaItemSedeRestaurante =
                            itemSedeRestauranteService.getByItemIdAndSedeRestauranteId(transferenciaItem.getItem().getId(), transferenciaItem.getSedeRestauranteOrigen().getId());
                    ItemSedeRestaurante itemSedeRestaurante = (ItemSedeRestaurante) respuestaItemSedeRestaurante.getObjeto();
                    itemSedeRestaurante.setCantidad(itemSedeRestaurante.getCantidad() - transferenciaItem.getCantidad());
                    itemSedeRestauranteService.actualizar(itemSedeRestaurante);
                } else if (nuevoEstadoId == 3) { // Recibido por la sede destino.
                    RespuestaServicioVO respuestaItemSedeRestaurante =
                            itemSedeRestauranteService.getByItemIdAndSedeRestauranteId(transferenciaItem.getItem().getId(), transferenciaItem.getSedeRestauranteDestino().getId());
                    ItemSedeRestaurante itemSedeRestaurante = (ItemSedeRestaurante) respuestaItemSedeRestaurante.getObjeto();
                    itemSedeRestaurante.setCantidad(itemSedeRestaurante.getCantidad() + transferenciaItem.getCantidad());
                    itemSedeRestauranteService.actualizar(itemSedeRestaurante);
                }

                RespuestaServicioVO respuestaNuevoEstado = estadoTransferenciaItemService.getById(nuevoEstadoId);
                EstadoTransferenciaItem nuevoEstado = (EstadoTransferenciaItem) respuestaNuevoEstado.getObjeto();
                transferenciaItem.setEstadoTransferenciaItem(nuevoEstado);
                RespuestaServicioVO respuestaActualizar = this.actualizar(transferenciaItem);
                if (respuestaActualizar.getExitosa()) {
                    respuesta.setObjeto(nuevoEstado.getNombre());
                    respuesta.setExitosa(true);
                    respuesta.setDescripcionRespuesta("Transacción exitosa.");
                } else {
                    return respuestaActualizar;
                }
            } else {
                respuesta.setObjeto(null);
                respuesta.setExitosa(false);
                respuesta.setDescripcionRespuesta("El estado en el que se encuentra la transferencia (" + transferenciaItem.getEstadoTransferenciaItem().getNombre() + ") no se puede actualizar.");
            }
        } else {
            respuesta.setObjeto(null);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("No existe una transferencia por el id ingresado.");
        }
        return respuesta;
    }
}
