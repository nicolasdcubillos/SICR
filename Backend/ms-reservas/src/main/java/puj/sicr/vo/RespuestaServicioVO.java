package puj.sicr.vo;
import lombok.Data;

import java.io.Serializable;

@Data
public class RespuestaServicioVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Objeto de datos generado por cada metodo implementado por los Microservicios
     */
    private Object objeto;

    /**
     * Define el exito o no de un llamda a un Metodo del Microservicio
     */
    private Boolean exitosa;

    /**
     * Codigo asignado a la respuesta si es exitoso siempre llevara 1 en caso
     * contrario el definido
     */
    private String codigoRespuesta;

    /**
     * Descripción asociada a cada respuesta
     */
    private String descripcionRespuesta;

    /**
     * Descripción generada por la excepcion
     */
    private String descripcionExcepcion;

    /**
     * Variable utilizada para referrirnos a la cantidad de registros entregados
     * es util en el momento de una consulta de datos.
     */
    private Long cantidadTotal;

    /*
     * Puerto que responde, sirve para revisar si hace balanceo
     * cuando hay muchos servicios
     */
    private int puerto;

    /*
     * Cantidad de instancias activas y que hacen balanceo
     */
    private int cantidadInstancias;

    /*
     * Puerto que responde, sirve para revisar si hace balanceo
     * cuando hay muchos servicios
     */
    private String idInstancia;


}