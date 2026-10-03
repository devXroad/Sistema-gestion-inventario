package modelo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un pedido realizado.
 */
public class Pedido {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private LocalDateTime fecha;
    private Usuario usuario;
    private Map<Producto, Integer> lineas;
    private double total;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor del pedido.
     * @param usuario Usuario que hace el pedido
     * @param lineas Lineas de productos con sus cantidades
     * @param total Coste total del pedido
     */
    public Pedido(Usuario usuario, Map<Producto, Integer> lineas, double total) {
        this.fecha = LocalDateTime.now();
        this.usuario = usuario;
        this.lineas = new HashMap<>(lineas);
        this.total = total;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public LocalDateTime getFecha() {
        return fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Map<Producto, Integer> getLineas() {
        return lineas;
    }

    public double getTotal() {
        return total;
    }
}
