package hilos;

import DAO.PedidoDAO;
import Modelo.Pedido;

// Criterio 3.1: Implementa Runnable para permitir ejecución concurrente
public class ProcesadorPedido implements Runnable {
    
    // Instancia del DAO para interactuar con la base de datos
    private PedidoDAO pedidoDAO = new PedidoDAO();

    @Override
    public void run() {
        // El hilo intentará procesar pedidos mientras existan en la BD
        while (true) {
            // 1. Obtener un pedido (Este método en el DAO debe ser SYNCHRONIZED)
            // Criterio 3.2.1: Obtener un pedido pendiente
            Pedido pedido = pedidoDAO.obtenerSiguientePendiente();

            // Si no hay más pedidos pendientes, el hilo termina su ejecución
            if (pedido == null) {
                System.out.println(Thread.currentThread().getName() + ": No hay más pedidos por procesar.");
                break;
            }

            try {
                // 2. Simular el procesamiento
                // Criterio 3.2.2: Utilizar sleep para simular tiempo de trabajo
                System.out.println(">>> " + Thread.currentThread().getName() + " procesando pedido #" + pedido.getIdPedido());
                Thread.sleep(2000); // Pausa de 2 segundos

                // 3. Actualizar la base de datos con el resultado
                // Criterio 3.2.3: Se actualiza a PROCESADO y se registra el nombre del hilo
                // Thread.currentThread().getName() obtiene el nombre asignado al hilo
                pedidoDAO.actualizarEstado(pedido.getIdPedido(), "PROCESADO", Thread.currentThread().getName());
                
                // Criterio 4: Incrementar el contador global sincronizado
                // Justificación: Se requiere sincronización para evitar que dos hilos 
                // modifiquen la variable estática al mismo tiempo (Condición de Carrera).
                PedidoDAO.incrementarContadorGlobal();

            } catch (InterruptedException e) {
                // Manejo de excepción de concurrencia
                System.err.println("Hilo interrumpido: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}