package principal;

import controlador.Controlador;

/**
 * Clase principal que arranca la aplicacion.
 */
public class Main {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    
    /**
     * Metodo main de identificador.
     * @param args Argumentos de linea de comandos
     */
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciar();
    }
}
