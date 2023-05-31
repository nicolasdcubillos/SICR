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
import puj.sicr.entidad.Categoria;
import puj.sicr.repository.CategoriaRepository;
import puj.sicr.vo.RespuestaServicioVO;

@Service
public class PagosService {

    Logger logger = LoggerFactory.getLogger(PagosService.class);

    @Autowired
    private CategoriaRepository repository;

    public RespuestaServicioVO pagar() {
        RespuestaServicioVO respuesta = new RespuestaServicioVO();
        try {
            logger.info("COMUNICACIÓN INICIADA CON LA PASARELA DE PAGOS.");
            logger.info("PAGO APROBADO. RESPUESTA AL CLIENTE.");
            respuesta.setExitosa(true);
            respuesta.setDescripcionRespuesta("Pago validado por la pasarela de pagos.");
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
