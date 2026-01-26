package Vista; // Capa de Vista: Encargada de la interacción con el usuario

import java.util.Scanner; // Clase para capturar datos desde la consola
import DAO.PedidoDAO;    // Clase para la persistencia de datos (CRUD)
import Modelo.Pedido;    // Clase que representa nuestra entidad Pedido
import hilos.ProcesadorPedido; // Clase que contiene la lógica de los hilos

public class Main {
    public static void main(String[] args) {
        // Inicialización del lector de teclado (Entrada del sistema)
        Scanner teclado = new Scanner(System.in);
        // Instancia de la lógica de base de datos
        PedidoDAO pedidoDAO = new PedidoDAO();
        // Variable para controlar la navegación en el menú
        int opcion = 0; 

        // El ciclo 'do-while' permite que el menú se repita hasta que el usuario decida salir
        do {
            // Criterio 6: Interfaz de usuario por consola con opciones claras
            System.out.println("\n--- MENU DE SISTEMA DE PEDIDOS (CRUD) ---");
            System.out.println("1. Registrar un nuevo pedido (CREATE)");
            System.out.println("2. Procesar pedidos pendientes (MULTIHILO)");
            System.out.println("3. Generar reporte de base de datos (READ)");
            System.out.println("4. Salir del sistema");
            System.out.print("Seleccione una opcion: ");
            
            // Captura la opción numérica ingresada por el usuario
            opcion = teclado.nextInt(); 

            switch (opcion) {
                case 1:
                    // OPERACIÓN: CREATE
                    // Ejemplo de entrada para el profesor: 
                    // ID: 10, Cliente: 'YASSIR', Producto: 'Monitor', Cantidad: 2
                    System.out.println("\n--- REGISTRO DE NUEVO PEDIDO ---");
                    System.out.print("Ingrese ID del pedido: "); 
                    int id = teclado.nextInt(); // Lee el ID (entero)
                    System.out.print("Ingrese nombre del Cliente: "); 
                    String cliente = teclado.next(); // Lee el nombre (cadena)
                    System.out.print("Ingrese nombre del Producto: "); 
                    String prod = teclado.next(); // Lee el producto (cadena)
                    System.out.print("Ingrese cantidad: "); 
                    int cant = teclado.nextInt(); // Lee la cantidad (entero)

                    // Criterio 2.3.2: Se crea el objeto Pedido con estado inicial 'PENDIENTE'
                    Pedido nuevo = new Pedido(id, cliente, prod, cant, "PENDIENTE");
                 // Guardamos y verificamos el resultado al mismo tiempo
                    if (pedidoDAO.insertarPedido(nuevo)) {
                        System.out.println("✅ Pedido guardado exitosamente.");
                    } else {
                        System.out.println("⚠️ No se pudo registrar el pedido por ID duplicado.");
                    }
                    break;
                case 2:
                    // OPERACIÓN: MULTIHILO (UPDATE CONCURRENTE)
                    System.out.println("\n--- INICIANDO PROCESAMIENTO CONCURRENTE ---");
                    // Instancia de la lógica que ejecutarán los hilos
                    ProcesadorPedido logica = new ProcesadorPedido();
                    
                    // Criterio 3.4: Creación de hilos con nombres personalizados
                    Thread h1 = new Thread(logica, "Hilo-Alfa");
                    Thread h2 = new Thread(logica, "Hilo-Beta");
                    
                    // Criterio 3.3: Configuración de prioridades para los hilos
                    h1.setPriority(Thread.MAX_PRIORITY); // Máxima prioridad (10)
                    h2.setPriority(Thread.MIN_PRIORITY); // Mínima prioridad (1)

                    // Criterio 3.2: Disparo de la ejecución paralela
                    h1.start();
                    h2.start();

                    try {
                        // El método join() asegura que el menú no reaparezca 
                        // hasta que los hilos hayan terminado de procesar.
                        h1.join();
                        h2.join();
                    } catch (InterruptedException e) {
                        System.err.println("Error en la concurrencia: " + e.getMessage());
                    } break;
                case 3:
                    // OPERACIÓN: READ (REPORTE)
                    // Criterio 6: Muestra el estado actual de la tabla en la BD
                    System.out.println("\n--- REPORTE ACTUAL DE PEDIDOS ---");
                    pedidoDAO.mostrarReporte();
                    break;
                case 4:
                    // CIERRE DEL SISTEMA
                    System.out.println("Cerrando sistema y liberando recursos...");
                    break;

                default:
                    // Manejo de errores de entrada del usuario
                    System.out.println("⚠️ Opcion no valida, intente nuevamente."); }
        // El ciclo continúa mientras la opción sea diferente de 4
        } while (opcion != 4); 
        // Criterio 5: Cierre del recurso Scanner para evitar fugas de memoria
        teclado.close(); }
}