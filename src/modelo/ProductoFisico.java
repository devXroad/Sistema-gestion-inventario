package modelo;

/**
 * Clase que representa un producto fisico que hereda de Producto.
 */
public class ProductoFisico extends Producto {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private double peso;
    private double gastosEnvio;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor de la clase ProductoFisico.
     * @param id Identificador unico del producto
     * @param nombre Nombre del producto
     * @param precio Precio base
     * @param stock Cantidad en stock
     * @param peso Peso del producto
     * @param gastosEnvio Costo de envio
     */
    public ProductoFisico(String id, String nombre, double precio, int stock, double peso, double gastosEnvio) {
        super(id, nombre, precio, stock);
        this.peso = peso;
        this.gastosEnvio = gastosEnvio;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Devuelve los detalles propios de un producto fisico.
     * @return Texto con el peso y los gastos de envio
     */
    @Override
    public String getDetalles() {
        return "Físico | Peso: " + peso + " kg | Envio: " + gastosEnvio + " EUR";
    }
}
