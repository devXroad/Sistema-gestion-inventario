package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa el carrito de la compra.
 */
public class Carrito {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private Map<Producto, Integer> productos;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor del carrito.
     */
    public Carrito() {
        this.productos = new HashMap<>();
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Anade un producto al carrito.
     * @param producto Producto a anadir
     * @param cantidad Cantidad a anadir
     * @throws IllegalArgumentException si la cantidad total supera el stock disponible
     */
    public void anadirProducto(Producto producto, int cantidad) throws IllegalArgumentException {
        int yaEnCarrito = this.productos.getOrDefault(producto, 0);
        if (yaEnCarrito + cantidad > producto.getStock()) {
            throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre()
                    + ". Disponible: " + producto.getStock() + ", ya en tu carrito: " + yaEnCarrito);
        }
        this.productos.put(producto, yaEnCarrito + cantidad);
    }

    /**
     * Quita un producto del carrito.
     * @param producto Producto a quitar
     */
    public void quitarProducto(Producto producto) {
        this.productos.remove(producto);
    }

    /**
     * Vacia el carrito.
     */
    public void vaciar() {
        this.productos.clear();
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public Map<Producto, Integer> getProductos() {
        return productos;
    }
}
