package modelo;

/**
 * Clase base para representar un usuario en el sistema.
 */
public abstract class Usuario {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private String id;
    private String nombre;
    private String correo;
    private String contrasena;
    private boolean activo;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor de la clase Usuario.
     * @param id Identificador unico
     * @param nombre Nombre del usuario
     * @param correo Correo electronico
     * @param contrasena Contrasena del usuario
     * @param activo Estado del usuario
     */
    public Usuario(String id, String nombre, String correo, String contrasena, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.activo = activo;
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.GETTERS Y SETTERS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Metodo abstracto para determinar si es admin.
     * @return true si es admin, false en caso contrario
     */
    public abstract boolean esAdmin();
}
