package modelo;

/**
 * Clase que representa un producto digital que hereda de Producto.
 */
public class ProductoDigital extends Producto {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private double tamanoDescarga;
    private String licencia;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor de la clase ProductoDigital.
     * @param id Identificador unico del producto
     * @param nombre Nombre del producto
     * @param precio Precio base
     * @param stock Cantidad en stock
     * @param tamanoDescarga Tamano en MB
     * @param licencia Codigo de licencia
     */
    public ProductoDigital(String id, String nombre, double precio, int stock, double tamanoDescarga, String licencia) {
        super(id, nombre, precio, stock);
        this.tamanoDescarga = tamanoDescarga;
        this.licencia = licencia;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public double getTamanoDescarga() {
        return tamanoDescarga;
    }

    public void setTamanoDescarga(double tamanoDescarga) {
        this.tamanoDescarga = tamanoDescarga;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Devuelve los detalles propios de un producto digital.
     * @return Texto con el tamano de descarga y la licencia
     */
    @Override
    public String getDetalles() {
        return "Digital | Descarga: " + tamanoDescarga + " MB | Licencia: " + licencia;
    }
}
