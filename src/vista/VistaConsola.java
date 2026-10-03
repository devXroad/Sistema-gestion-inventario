package vista;

import modelo.Pedido;
import modelo.Producto;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.List;

/**
 * Clase que gestiona la salida por consola separando la vista de la logica.
 */
public class VistaConsola {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS DE IMPRESION.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*

    /**
     * Imprime un mensaje informativo.
     * @param mensaje Mensaje a imprimir
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println(Estilos.PASTEL_BLUE + mensaje + Estilos.ANSI_RESET);
    }

    /**
     * Imprime el banner de bienvenida del sistema.
     */
    public void mostrarBienvenida() {
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "╔════════════════════════════════════════╗" + Estilos.ANSI_RESET);
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "║ " + Estilos.PASTEL_BLUE + "    SISTEMA DE GESTIÓN INVENTARIO     " + Estilos.PASTEL_PURPLE + Estilos.BOLD + " ║" + Estilos.ANSI_RESET);
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "╚════════════════════════════════════════╝" + Estilos.ANSI_RESET);
    }

    /**
     * Muestra el menu de inicio de sesion.
     */
    public void menuLogin() {
        mostrarBienvenida();
        System.out.println(Estilos.PASTEL_BLUE + "Selecciona con qué perfil deseas entrar:");
        System.out.println("1. Entrar como Administrador");
        System.out.println("2. Entrar como Usuario Normal");
        System.out.println("3. Salir del programa" + Estilos.ANSI_RESET);
        System.out.print(Estilos.PASTEL_GREEN + "Elige una opción: " + Estilos.ANSI_RESET);
    }

    /**
     * Pide la contrasena al usuario.
     */
    public void pedirPassword() {
        System.out.print(Estilos.PASTEL_BLUE + "Introduce tu contraseña: " + Estilos.ANSI_RESET);
    }

    /**
     * Imprime un mensaje de exito.
     * @param mensaje Mensaje de exito
     */
    public void mostrarExito(String mensaje) {
        System.out.println(Estilos.PASTEL_GREEN + Estilos.BOLD + "[ÉXITO] " + mensaje + Estilos.ANSI_RESET);
    }

    /**
     * Imprime un mensaje de error.
     * @param mensaje Mensaje de error
     */
    public void mostrarError(String mensaje) {
        System.out.println(Estilos.ANSI_RED + Estilos.BOLD + "[ERROR] " + mensaje + Estilos.ANSI_RESET);
    }

    /**
     * Muestra el menu principal dependiendo de si el usuario es admin.
     * @param esAdmin true si es admin
     */
    public void mostrarMenu(boolean esAdmin) {
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "\n✦ MENÚ PRINCIPAL ✦" + Estilos.ANSI_RESET);
        System.out.print(Estilos.PASTEL_BLUE);
        if (esAdmin) {
            System.out.println("1. Gestionar productos (alta, baja, listado, búsqueda por precio)");
            System.out.println("2. Gestionar usuarios (alta, baja, listado activos)");
            System.out.println("3. Gestionar carrito (añadir, quitar, ver total)");
            System.out.println("4. Cerrar pedido / generar factura");
            System.out.println("5. Consultar historial de pedidos");
            System.out.println("6. Cerrar sesión");
        } else {
            System.out.println("1. Gestionar carrito (añadir, quitar, ver total)");
            System.out.println("2. Cerrar pedido / generar factura");
            System.out.println("3. Consultar historial de pedidos");
            System.out.println("4. Cerrar sesión");
        }
        System.out.print(Estilos.ANSI_RESET);
        System.out.print(Estilos.PASTEL_GREEN + "Seleccione una opción: " + Estilos.ANSI_RESET);
    }

    /**
     * Genera la factura de un pedido.
     * @param pedido Pedido a facturar
     */
    public void mostrarFactura(Pedido pedido) {
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "\n✦ FACTURA ✦" + Estilos.ANSI_RESET);
        System.out.print(Estilos.PASTEL_BLUE);
        System.out.println("Fecha: " + pedido.getFecha().format(FORMATO_FECHA));
        System.out.println("Cliente: " + pedido.getUsuario().getNombre());
        System.out.println("Productos:");
        for (Map.Entry<Producto, Integer> linea : pedido.getLineas().entrySet()) {
            Producto p = linea.getKey();
            int cantidad = linea.getValue();
            System.out.println(" - " + p.getNombre() + " x" + cantidad + " : " + String.format("%.2f", p.getPrecio() * cantidad) + " EUR");
        }
        System.out.println("Total: " + Estilos.PASTEL_YELLOW + Estilos.BOLD + String.format("%.2f", pedido.getTotal()) + " EUR" + Estilos.ANSI_RESET);
        System.out.println(Estilos.PASTEL_PURPLE + "───────────" + Estilos.ANSI_RESET);
    }

    /**
     * Muestra una lista de pedidos.
     * @param pedidos Lista de pedidos
     */
    public void mostrarHistorial(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println(Estilos.ANSI_RED + "No hay pedidos en el historial." + Estilos.ANSI_RESET);
            return;
        }
        System.out.println(Estilos.PASTEL_PURPLE + Estilos.BOLD + "\n✦ HISTORIAL DE PEDIDOS ✦" + Estilos.ANSI_RESET);
        for (Pedido pedido : pedidos) {
            System.out.println(Estilos.PASTEL_BLUE + "Pedido de " + pedido.getUsuario().getNombre() + " | Fecha: " + pedido.getFecha().format(FORMATO_FECHA)
                    + " | Total: " + Estilos.PASTEL_YELLOW + Estilos.BOLD + String.format("%.2f", pedido.getTotal()) + " EUR" + Estilos.ANSI_RESET);
        }
    }
}
