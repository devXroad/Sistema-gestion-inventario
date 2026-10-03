package modelo;

/**
 * Clase para representar un usuario normal.
 */
public class UsuarioNormal extends Usuario {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor de la clase UsuarioNormal.
     * @param id Identificador unico
     * @param nombre Nombre del usuario
     * @param correo Correo electronico
     * @param contrasena Contrasena
     * @param activo Estado del usuario
     */
    public UsuarioNormal(String id, String nombre, String correo, String contrasena, boolean activo) {
        super(id, nombre, correo, contrasena, activo);
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    @Override
    public boolean esAdmin() {
        return false;
    }
}
