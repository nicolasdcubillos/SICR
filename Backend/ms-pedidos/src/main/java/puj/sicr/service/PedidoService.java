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
import puj.sicr.dto.PedidoProductoDTO;
import puj.sicr.dto.RealizarPedidoDTO;
import puj.sicr.entidad.*;
import puj.sicr.repository.*;
import puj.sicr.vo.RespuestaServicioVO;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EstadoPedidoService estadoPedidoService;

    @Autowired
    private SedeRestauranteService sedeRestauranteService;

    @Autowired
    private ProductoPedidoService productoPedidoService;

    @Autowired
    private ItemSedeRestauranteService itemSedeRestauranteService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MiembroService miembroService;

    @Autowired
    private ProductoService productoService;

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

    public RespuestaServicioVO actualizarEstadoPedido(Integer id) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        RespuestaServicioVO respuestaPedido = this.getById(id);
        if (respuestaPedido.getExitosa()) {
            Pedido pedido = (Pedido) respuestaPedido.getObjeto();
            if (pedido.getEstadoPedido().getId() < 3) {//Preparando, Enviado y Entregado son los estados
                Integer nuevoEstadoId = pedido.getEstadoPedido().getId() + 1;
                RespuestaServicioVO respuestaNuevoEstado = estadoPedidoService.getById(nuevoEstadoId);
                if (respuestaNuevoEstado.getExitosa()){
                    EstadoPedido nuevoEstado = (EstadoPedido) respuestaNuevoEstado.getObjeto();
                    pedido.setEstadoPedido(nuevoEstado);
                    RespuestaServicioVO respuestaActualizar =this.actualizarTX(pedido);
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
                    respuesta.setDescripcionRespuesta("El estado al que se quiere actualizar no existe");
                }
            } else{
                respuesta.setObjeto(null);
                respuesta.setExitosa(false);
                respuesta.setDescripcionRespuesta("El estado en el que se encuentra el pedido (" + pedido.getEstadoPedido().getNombre() + ") no se puede actualizar.");
            }
        } else {
            respuesta.setObjeto(null);
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("No existe un pedido por el id ingresado.");
        }
        return respuesta;
    }

    public RespuestaServicioVO realizarPedido(RealizarPedidoDTO pedidoDTO) {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        respuesta.setExitosa(false);
        RespuestaServicioVO respuestaSedeResaturante = sedeRestauranteService.getById(pedidoDTO.getSedeRestaurante());
        if (respuestaSedeResaturante.getExitosa()){
            SedeRestaurante sedeRestaurante = (SedeRestaurante) respuestaSedeResaturante.getObjeto();
            List<Producto> productoList = new ArrayList();
            for (PedidoProductoDTO pedidoProductoDTO : pedidoDTO.getPedidoProductos()){
                RespuestaServicioVO respuestaProducto = productoService.getById(pedidoProductoDTO.getPedidoProductoId());
                if (respuestaProducto.getExitosa()){
                    productoList.add((Producto) respuestaProducto.getObjeto());
                }
            }
            if (productoList.size() > 0){
                List <Item> itemSede = new ArrayList();
                Map<Item, Integer> disponibilidadItems = new HashMap<>();
                List <ItemSedeRestaurante> itemSedeRestauranteList = sedeRestaurante.getSedeRestauranteItemSedeRestaurantes();
                for (ItemSedeRestaurante item : itemSedeRestauranteList){
                    Item itemS = item.getItem();
                    itemSede.add(itemS);//Tengo todos los items de la sede
                    Integer cantidadDisponible = item.getCantidad();
                    disponibilidadItems.put(itemS, cantidadDisponible);
                }
                Integer j=0;
                outerLoop:
                for (Producto producto : productoList){
                    if (producto.getProductoProductoItems().size() > 0) {
                        List<ProductoItem> itemList = producto.getProductoProductoItems();
                        for (int i = 0; i < itemList.size(); i++){
                            Item item = itemRepository.getItemByProductoItemId(itemList.get(i).getId());
                            Integer totalItems = itemList.get(i).getCantidad() * pedidoDTO.getPedidoProductos().get(j).getPedidoProductoCantidad(); //cantidad de items total con base en la canntidad de productos
                            if (itemSede.contains(item) && disponibilidadItems.getOrDefault(item, 0) >= totalItems){
                                ItemSedeRestaurante itemSedeRestauranteActualizado = itemSedeRestauranteList.get(i);
                                int cantidadActual = itemSedeRestauranteActualizado.getCantidad();
                                int nuevaCantidad = cantidadActual - totalItems;
                                itemSedeRestauranteActualizado.setCantidad(nuevaCantidad);
                                RespuestaServicioVO respuestaItemSedeRestaurante = itemSedeRestauranteService.actualizarTX(itemSedeRestauranteActualizado);
                                if (respuestaItemSedeRestaurante.getExitosa()){
                                    respuesta.setObjeto(respuestaItemSedeRestaurante.getObjeto());
                                    respuesta.setExitosa(true);
                                    respuesta.setDescripcionRespuesta("Transacción exitosa.");
                                } else {
                                    respuesta.setObjeto(null);
                                    respuesta.setExitosa(false);
                                    respuesta.setDescripcionRespuesta("Error interno al actualizar el inventario del item");
                                    break outerLoop;
                                }
                            } else {
                                respuesta.setObjeto(null);
                                respuesta.setExitosa(false);
                                respuesta.setDescripcionRespuesta("El item " + item + " no está presente en la lista de items de la sede o no tiene disponibilidad suficiente.");
                                break outerLoop;
                            }
                            j++;
                        }
                    } else {
                        respuesta.setObjeto(null);
                        respuesta.setExitosa(false);
                        respuesta.setDescripcionRespuesta("El producto no cuenta con items asociados.");
                        break;
                    }
                }
                if (respuesta.getExitosa()){
                    Pedido pedido = new Pedido();
                    Usuario usuario = new Usuario();
                    RespuestaServicioVO respuestaNuevoEstado = estadoPedidoService.getById(1);
                    if (respuestaNuevoEstado.getExitosa()) {
                        RespuestaServicioVO respuestaUsuario = usuarioService.getById(pedidoDTO.getUsuario());//cambiar por el que viene del DTO
                        if (respuestaUsuario.getExitosa()) {
                            if (pedidoDTO.getMiembro() != null && pedidoDTO.getMiembro() != 0){
                                RespuestaServicioVO respuestaMiembro = miembroService.getById(pedidoDTO.getMiembro());
                                pedido.setMiembro((Miembro) respuestaMiembro.getObjeto());
                            }
                            EstadoPedido nuevoEstado = (EstadoPedido) respuestaNuevoEstado.getObjeto();
                            Usuario usuarioPedido = (Usuario) respuestaUsuario.getObjeto();
                            OffsetDateTime fechaHoraActual = OffsetDateTime.now(ZoneOffset.UTC);
                            pedido.setEstadoPedido(nuevoEstado);
                            pedido.setUsuario(usuarioPedido);
                            pedido.setFecha(fechaHoraActual);
                            pedido.setTotal(pedidoDTO.getTotal());
                            pedido.setSubtotal(pedidoDTO.getSubtotal());
                            pedido.setSedeRestaurante(sedeRestaurante);
                            RespuestaServicioVO respuestaPedido = this.crearTX(pedido);
                            if (respuestaPedido.getExitosa()){
                                Pedido pedidoRetornado = (Pedido) respuestaPedido.getObjeto();
                                List<ProductoPedido> productosPedido = new ArrayList();
                                j=0;
                                for (Producto producto : productoList){
                                    ProductoPedido productoPedido = new ProductoPedido();
                                    productoPedido.setProducto(producto);
                                    productoPedido.setCantidad(pedidoDTO.getPedidoProductos().get(j).getPedidoProductoCantidad());
                                    productoPedido.setPedido(pedidoRetornado);
                                    productosPedido.add(productoPedido);
                                    j++;
                                }
                                j=0;
                                for (ProductoPedido productoPedido : productosPedido){
                                    RespuestaServicioVO respuestaProductoPedido = productoPedidoService.crear(productoPedido);
                                    if (!respuestaProductoPedido.getExitosa()){
                                        j++;
                                    }
                                }
                                if (j > 0){
                                    respuesta.setObjeto(null);
                                    respuesta.setExitosa(false);
                                    respuesta.setDescripcionRespuesta("No se pudieron agregar " + j + " productos al pedido");
                                } else {
                                    respuesta.setObjeto(pedido);
                                    respuesta.setExitosa(true);
                                    respuesta.setDescripcionRespuesta("Transacción exitosa.");
                                }
                            }
                        } else {
                            respuesta.setObjeto(null);
                            respuesta.setExitosa(false);
                            respuesta.setDescripcionRespuesta("El usuario asociado al pedido no existe");
                        }
                    } else {
                        respuesta.setObjeto(null);
                        respuesta.setExitosa(false);
                        respuesta.setDescripcionRespuesta("El estado inicial del pedido no existe");
                    }
                }
            }
        } else {
            respuesta.setObjeto(null);
            respuesta.setExitosa(false);
            respuesta.setDescripcionRespuesta("No existe una sede restaurante con el ID ingresado.");
        }
        return respuesta;
    }
}
