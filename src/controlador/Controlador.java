package controlador;

import modelo.*;
import vista.VistaConsola;


import java.util.*;

/**
 * Controlador principal del sistema.
 */
public class Controlador {

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.ATRIBUTOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    private Map<String, Producto> productos;
    private Map<String, Usuario> usuarios;
    private List<Pedido> historialPedidos;
    private Carrito carritoActual;
    private Usuario usuarioSesion;
    private VistaConsola vista;
    private Scanner scanner;

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.CONSTRUCTORES.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    /**
     * Constructor del controlador principal.
     */
    public Controlador() {
        this.productos = new HashMap<>();
        this.usuarios = new HashMap<>();
        this.historialPedidos = new ArrayList<>();
        this.vista = new VistaConsola();
        this.scanner = new Scanner(System.in);
        
        // Datos iniciales de prueba
        Usuario admin = new UsuarioAdmin("U1", "Admin", "admin@test.com", "admin123", true);
        Usuario user = new UsuarioNormal("U2", "User", "user@test.com", "user123", true);
        usuarios.put(admin.getId().toLowerCase(), admin);
        usuarios.put(user.getId().toLowerCase(), user);
        
        productos.put("p1", new ProductoFisico("P1", "Portatil", 1000.0, 10, 2.5, 10.0));
        productos.put("p2", new ProductoDigital("P2", "Antivirus", 50.0, 100, 150.0, "LIC-1234"));
    }

    //*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.METODOS.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*
    
    /**
     * Inicia la aplicacion.
     */
    public void iniciar() {
        boolean apagarSistema = false;
        
        while (!apagarSistema) {
            boolean logueado = seleccionarUsuario();
            if (!logueado) {
                apagarSistema = true;
                continue;
            }
            
            boolean cerrarSesion = false;
            while (!cerrarSesion) {
                vista.mostrarMenu(usuarioSesion.esAdmin());
                try {
                    String opcionStr = scanner.nextLine().trim();
                    int opcion = Integer.parseInt(opcionStr);
                    
                    switch (opcion) {
                        case 1:
                            if (usuarioSesion.esAdmin()) gestionarProductos();
                            else gestionarCarrito();
                            break;
                        case 2:
                            if (usuarioSesion.esAdmin()) gestionarUsuarios();
                            else cerrarPedido();
                            break;
                        case 3:
                            if (usuarioSesion.esAdmin()) gestionarCarrito();
                            else consultarHistorial();
                            break;
                        case 4:
                            if (usuarioSesion.esAdmin()) cerrarPedido();
                            else {
                                vista.mostrarMensaje("Cerrando sesión...");
                                cerrarSesion = true;
                            }
                            break;
                        case 5:
                            if (usuarioSesion.esAdmin()) consultarHistorial();
                            else vista.mostrarError("Opción no válida.");
                            break;
                        case 6:
                            if (usuarioSesion.esAdmin()) {
                                vista.mostrarMensaje("Cerrando sesión...");
                                cerrarSesion = true;
                            } else {
                                vista.mostrarError("Opción no válida.");
                            }
                            break;
                        default:
                            vista.mostrarError("Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    vista.mostrarError("Debe introducir un numero válido.");
                }
            }
        }
    }

    /**
     * Muestra el menu de login y pide credenciales hasta que el usuario entra o decide salir.
     * @return true si se ha iniciado sesion, false si el usuario quiere salir del programa
     */
    private boolean seleccionarUsuario() {
        boolean loginValido = false;
        while (!loginValido) {
            vista.menuLogin();
            String opcion = scanner.nextLine().trim();
            
            if (opcion.equals("3")) {
                return false;
            }
            
            if (opcion.equals("1") || opcion.equals("2")) {
                boolean esLoginAdmin = opcion.equals("1");
                
                vista.mostrarMensaje("Introduce tu ID o Email:");
                String identificador = scanner.nextLine().trim();
                vista.pedirPassword();
                String contrasena = scanner.nextLine().trim();
                
                Usuario encontrado = null;
                for (Usuario u : usuarios.values()) {
                    if (u.esAdmin() == esLoginAdmin 
                        && (u.getId().equalsIgnoreCase(identificador) || u.getCorreo().equalsIgnoreCase(identificador)) 
                        && u.getContrasena().equals(contrasena)) {
                        encontrado = u;
                    }
                }
                
                if (encontrado == null) {
                    vista.mostrarError("Credenciales incorrectas o el tipo de perfil elegido no coincide.\n");
                } else if (!encontrado.isActivo()) {
                    vista.mostrarError("Este usuario esta dado de baja y no puede iniciar sesión.\n");
                } else {
                    this.usuarioSesion = encontrado;
                    this.carritoActual = new Carrito();
                    vista.mostrarExito("Sesión iniciada como " + usuarioSesion.getNombre() + (esLoginAdmin ? " (Admin)" : " (Usuario Normal)"));
                    loginValido = true;
                }
            } else {
                vista.mostrarError("Opción no válida. Por favor, selecciona 1, 2 o 3.\n");
            }
        }
        return true;
    }

    /**
     * Submenu de gestion de productos (solo Admin): alta, baja, listado y busqueda por precio.
     */
    private void gestionarProductos() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMensaje("\n--- GESTIÓN DE PRODUCTOS ---");
            vista.mostrarMensaje("1. Alta de producto");
            vista.mostrarMensaje("2. Baja de producto");
            vista.mostrarMensaje("3. Listado completo");
            vista.mostrarMensaje("4. Busqueda por precio (menor o igual a)");
            vista.mostrarMensaje("5. Volver");
            vista.mostrarMensaje("Seleccione una opción:");
            
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    vista.mostrarMensaje("Introduce el ID del producto:");
                    String idProd = scanner.nextLine().trim();
                    if (idProd.isEmpty()) {
                        vista.mostrarError("El ID no puede estar vacío.");
                        break;
                    }
                    if (productos.containsKey(idProd.toLowerCase())) {
                        vista.mostrarError("Ya existe un producto con ese ID.");
                        break;
                    }
                    vista.mostrarMensaje("Introduce el nombre del producto:");
                    String nomProd = scanner.nextLine().trim();
                    if (nomProd.isEmpty()) {
                        vista.mostrarError("El nombre no puede estar vacío.");
                        break;
                    }
                    try {
                        vista.mostrarMensaje("Introduce el precio:");
                        double precio = Double.parseDouble(scanner.nextLine().trim());
                        vista.mostrarMensaje("Introduce el stock inicial:");
                        int stock = Integer.parseInt(scanner.nextLine().trim());
                        if (precio <= 0 || stock < 0) {
                            throw new IllegalArgumentException("El precio debe ser mayor que 0 y el stock no puede ser negativo.");
                        }
                        
                        vista.mostrarMensaje("¿Es un producto Físico (1) o Digital (2)?");
                        String tipoProd = scanner.nextLine().trim();
                        if (tipoProd.equals("1")) {
                            vista.mostrarMensaje("Introduce el peso (kg):");
                            double peso = Double.parseDouble(scanner.nextLine().trim());
                            vista.mostrarMensaje("Introduce gastos de envío:");
                            double envio = Double.parseDouble(scanner.nextLine().trim());
                            if (peso < 0 || envio < 0) {
                                throw new IllegalArgumentException("El peso y los gastos de envío no pueden ser negativos.");
                            }
                            productos.put(idProd.toLowerCase(), new ProductoFisico(idProd, nomProd, precio, stock, peso, envio));
                            vista.mostrarExito("Producto Físico añadido.");
                        } else if (tipoProd.equals("2")) {
                            vista.mostrarMensaje("Introduce tamaño de descarga (MB):");
                            double tam = Double.parseDouble(scanner.nextLine().trim());
                            if (tam < 0) {
                                throw new IllegalArgumentException("El tamaño de descarga no puede ser negativo.");
                            }
                            vista.mostrarMensaje("Introduce contraseña de licencia:");
                            String lic = scanner.nextLine().trim();
                            productos.put(idProd.toLowerCase(), new ProductoDigital(idProd, nomProd, precio, stock, tam, lic));
                            vista.mostrarExito("Producto Digital añadido.");
                        } else {
                            vista.mostrarError("Tipo de producto no válido. Cancelando...");
                        }
                    } catch (NumberFormatException e) {
                        vista.mostrarError("Error: Se esperaba un valor numerico válido.");
                    } catch (IllegalArgumentException e) {
                        vista.mostrarError(e.getMessage());
                    }
                    break;
                case "2":
                    vista.mostrarMensaje("Introduce el ID del producto a dar de baja:");
                    String idBaja = scanner.nextLine().trim();
                    if (productos.remove(idBaja.toLowerCase()) != null) {
                        vista.mostrarExito("Producto eliminado.");
                    } else {
                        vista.mostrarError("Producto no encontrado.");
                    }
                    break;
                case "3":
                    vista.mostrarMensaje("Listado de productos:");
                    for (Producto p : productos.values()) {
                        vista.mostrarMensaje(" - ID: " + p.getId() + " | " + p.getNombre() + " | Precio: " + p.getPrecio() + " EUR | Stock: " + p.getStock() + " | " + p.getDetalles());
                    }
                    break;
                case "4":
                    vista.mostrarMensaje("Introduce el precio maximo:");
                    try {
                        double max = Double.parseDouble(scanner.nextLine().trim());
                        vista.mostrarMensaje("Productos con precio <= " + max + ":");
                        boolean hayProductos = false;
                        for (Producto p : productos.values()) {
                            if (p.getPrecio() <= max) {
                                vista.mostrarMensaje(" - ID: " + p.getId() + " | " + p.getNombre() + " | Precio: " + p.getPrecio() + " EUR");
                                hayProductos = true;
                            }
                        }
                        if (!hayProductos) {
                            vista.mostrarMensaje("No se encontraron productos en ese rango de precio.");
                        }
                    } catch (NumberFormatException e) {
                        vista.mostrarError("Precio invalido.");
                    }
                    break;
                case "5":
                    volver = true;
                    break;
                default:
                    vista.mostrarError("Opción no válida.");
            }
        }
    }

    /**
     * Submenu de gestion de usuarios (solo Admin): alta, baja logica y listado de activos.
     */
    private void gestionarUsuarios() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMensaje("\n--- GESTIÓN DE USUARIOS ---");
            vista.mostrarMensaje("1. Alta de usuario");
            vista.mostrarMensaje("2. Baja/Desactivar usuario");
            vista.mostrarMensaje("3. Listado de usuarios activos");
            vista.mostrarMensaje("4. Volver");
            vista.mostrarMensaje("Seleccione una opción:");
            
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    vista.mostrarMensaje("Introduce el ID del usuario (ej:U3) :");
                    String id = scanner.nextLine().trim();
                    if (id.isEmpty()) {
                        vista.mostrarError("El ID no puede estar vacío.");
                        break;
                    }
                    if (usuarios.containsKey(id.toLowerCase())) {
                        vista.mostrarError("Ya existe un usuario con ese ID.");
                        break;
                    }
                    vista.mostrarMensaje("Introduce el nombre:");
                    String nombre = scanner.nextLine().trim();
                    vista.mostrarMensaje("Introduce el correo:");
                    String correo = scanner.nextLine().trim();
                    if (nombre.isEmpty() || !correo.contains("@")) {
                        vista.mostrarError("El nombre no puede estar vacío y el correo debe ser válido.");
                        break;
                    }
                    boolean emailRepetido = false;
                    for (Usuario u : usuarios.values()) {
                        if (u.getCorreo().equalsIgnoreCase(correo)) {
                            emailRepetido = true;
                        }
                    }
                    if (emailRepetido) {
                        vista.mostrarError("Ya existe un usuario con ese correo.");
                        break;
                    }
                    vista.mostrarMensaje("Introduce la contraseña:");
                    String contrasena = scanner.nextLine().trim();
                    if (contrasena.isEmpty()) {
                        vista.mostrarError("La contraseña no puede estar vacía.");
                        break;
                    }
                    vista.mostrarMensaje("¿Tipo de perfil? (1 para Admin, 2 para Normal):");
                    String tipo = scanner.nextLine().trim();
                    
                    if (tipo.equals("1")) {
                        usuarios.put(id.toLowerCase(), new UsuarioAdmin(id, nombre, correo, contrasena, true));
                        vista.mostrarExito("Administrador añadido correctamente.");
                    } else if (tipo.equals("2")) {
                        usuarios.put(id.toLowerCase(), new UsuarioNormal(id, nombre, correo, contrasena, true));
                        vista.mostrarExito("Usuario Normal añadido correctamente.");
                    } else {
                        vista.mostrarError("Tipo de perfil no válido. Cancelando...");
                    }
                    break;
                case "2":
                    vista.mostrarMensaje("Introduce el ID del usuario a dar de baja:");
                    String idBaja = scanner.nextLine().trim();
                    Usuario uBaja = usuarios.get(idBaja.toLowerCase());
                    if (uBaja == null) {
                        vista.mostrarError("Usuario no encontrado.");
                    } else if (uBaja == usuarioSesion) {
                        vista.mostrarError("No puedes darte de baja a ti mismo mientras tienes la sesión iniciada.");
                    } else if (!uBaja.isActivo()) {
                        vista.mostrarError("El usuario " + uBaja.getNombre() + " ya estaba dado de baja.");
                    } else {
                        uBaja.setActivo(false); // Baja logica
                        vista.mostrarExito("Usuario " + uBaja.getNombre() + " dado de baja (desactivado).");
                    }
                    break;
                case "3":
                    vista.mostrarMensaje("Usuarios registrados y activos:");
                    for (Usuario u : usuarios.values()) {
                        if (u.isActivo()) {
                            vista.mostrarMensaje(" - ID: " + u.getId() + " | Nombre: " + u.getNombre() + " | Email: " + u.getCorreo() + " | Perfil: " + (u.esAdmin() ? "Admin" : "Normal"));
                        }
                    }
                    break;
                case "4":
                    volver = true;
                    break;
                default:
                    vista.mostrarError("Opción no válida.");
            }
        }
    }

    /**
     * Submenu del carrito: anadir producto, quitar producto y ver el total.
     */
    private void gestionarCarrito() {
        boolean volver = false;
        while (!volver) {
            vista.mostrarMensaje("\n--- GESTIÓN DE CARRITO ---");
            vista.mostrarMensaje("1. Añadir producto");
            vista.mostrarMensaje("2. Quitar producto");
            vista.mostrarMensaje("3. Ver carrito / Total");
            vista.mostrarMensaje("4. Volver");
            vista.mostrarMensaje("Seleccione una opción:");
            
            String opcion = scanner.nextLine().trim();
            switch (opcion) {
                case "1":
                    vista.mostrarMensaje("Productos disponibles en tienda:");
                    for (Producto p : productos.values()) {
                        if (p.getStock() > 0) {
                            vista.mostrarMensaje(" - ID: " + p.getId() + " | " + p.getNombre() + " | Precio: " + p.getPrecio() + " EUR | Stock: " + p.getStock());
                        }
                    }
                    vista.mostrarMensaje("Introduce el ID o el Nombre del producto:");
                    String identificador = scanner.nextLine().trim();
                    Producto productoElegido = null;
                    for (Producto p : productos.values()) {
                        if (p.getId().equalsIgnoreCase(identificador) || p.getNombre().equalsIgnoreCase(identificador)) {
                            productoElegido = p;
                            break;
                        }
                    }
                    if (productoElegido != null) {
                        vista.mostrarMensaje("Cantidad:");
                        try {
                            int cantidad = Integer.parseInt(scanner.nextLine().trim());
                            if (cantidad <= 0) {
                                vista.mostrarError("La cantidad debe ser mayor que 0.");
                            } else {
                                carritoActual.anadirProducto(productoElegido, cantidad);
                                vista.mostrarExito("Añadido al carrito.");
                            }
                        } catch (NumberFormatException e) {
                            vista.mostrarError("Cantidad inválida.");
                        } catch (IllegalArgumentException e) {
                            vista.mostrarError(e.getMessage());
                        }
                    } else {
                        vista.mostrarError("Producto no encontrado.");
                    }
                    break;
                case "2":
                    if (carritoActual.getProductos().isEmpty()) {
                        vista.mostrarError("El carrito esta vacío.");
                        break;
                    }
                    vista.mostrarMensaje("Productos en tu carrito:");
                    for (Map.Entry<Producto, Integer> elemento : carritoActual.getProductos().entrySet()) {
                        vista.mostrarMensaje(" - ID: " + elemento.getKey().getId() + " | " + elemento.getKey().getNombre() + " (Cantidad: " + elemento.getValue() + ")");
                    }
                    vista.mostrarMensaje("Introduce el ID o el Nombre del producto a quitar:");
                    String identificadorQuitar = scanner.nextLine().trim();
                    Producto productoAQuitar = null;
                    for (Producto p : carritoActual.getProductos().keySet()) {
                        if (p.getId().equalsIgnoreCase(identificadorQuitar) || p.getNombre().equalsIgnoreCase(identificadorQuitar)) {
                            productoAQuitar = p;
                            break;
                        }
                    }
                    if (productoAQuitar != null) {
                        carritoActual.quitarProducto(productoAQuitar);
                        vista.mostrarExito("Producto eliminado del carrito.");
                    } else {
                        vista.mostrarError("Ese producto no esta en tu carrito.");
                    }
                    break;
                case "3":
                    if (carritoActual.getProductos().isEmpty()) {
                        vista.mostrarMensaje("El carrito esta vacío.");
                        break;
                    }
                    double total = 0;
                    vista.mostrarMensaje("--- CONTENIDO DEL CARRITO ---");
                    for (Map.Entry<Producto, Integer> elemento : carritoActual.getProductos().entrySet()) {
                        double subtotal = elemento.getKey().getPrecio() * elemento.getValue();
                        vista.mostrarMensaje(" - " + elemento.getKey().getNombre() + " x" + elemento.getValue() + " = " + subtotal + " EUR");
                        total += subtotal;
                    }
                    vista.mostrarMensaje("-----------------------------");
                    vista.mostrarMensaje("Total carrito: " + total + " EUR");
                    break;
                case "4":
                    volver = true;
                    break;
                default:
                    vista.mostrarError("Opción no válida.");
            }
        }
    }

    /**
     * Comprueba el carrito, descuenta el stock, crea el Pedido, lo guarda en el historial y muestra la factura.
     */
    private void cerrarPedido() {
        if (carritoActual.getProductos().isEmpty()) {
            vista.mostrarError("El carrito esta vacío.");
            return;
        }
        
        // Comprobar stock y existencia en el catalogo antes de cerrar
        for (Map.Entry<Producto, Integer> elemento : carritoActual.getProductos().entrySet()) {
            Producto prodCarrito = elemento.getKey();
            
            // 1. Comprobar si el administrador ha borrado el producto
            if (!productos.containsKey(prodCarrito.getId().toLowerCase())) {
                vista.mostrarError("El producto '" + prodCarrito.getNombre() + "' ha sido descatalogado por un administrador. Por favor, quitalo de tu carrito para poder continuar.");
                return;
            }
            
            // 2. Comprobar si sigue habiendo stock suficiente
            if (elemento.getValue() > prodCarrito.getStock()) {
                vista.mostrarError("El producto '" + prodCarrito.getNombre() + "' ya no tiene suficiente stock. Solo quedan " + prodCarrito.getStock() + ". Ajuste su carrito e intentelo de nuevo.");
                return;
            }
        }
        
        double total = 0;
        for (Map.Entry<Producto, Integer> elemento : carritoActual.getProductos().entrySet()) {
            Producto p = elemento.getKey();
            int cantidad = elemento.getValue();
            p.setStock(p.getStock() - cantidad); // Descontar stock
            total += p.getPrecio() * cantidad;
        }
        
        Pedido pedido = new Pedido(usuarioSesion, carritoActual.getProductos(), total);
        historialPedidos.add(pedido);
        carritoActual.vaciar();
        
        vista.mostrarExito("Pedido cerrado con éxito.");
        vista.mostrarFactura(pedido);
    }

    /**
     * Muestra el historial de pedidos: el Admin ve todos y el usuario normal solo los suyos.
     */
    private void consultarHistorial() {
        List<Pedido> pedidosVista = new ArrayList<>();
        if (usuarioSesion.esAdmin()) {
            pedidosVista.addAll(historialPedidos);
        } else {
            for (Pedido p : historialPedidos) {
                if (p.getUsuario().getId().equals(usuarioSesion.getId())) {
                    pedidosVista.add(p);
                }
            }
        }
        vista.mostrarHistorial(pedidosVista);
    }
}
