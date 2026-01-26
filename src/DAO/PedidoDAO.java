package DAO; // Capa de Acceso a Datos (Data Access Object) del patrón MVC
import Conexion.ConexionBD; // Importa la lógica de conexión creada previamente
import Modelo.Pedido;     // Importa la entidad Pedido
import java.sql.*;        // Importa las interfaces JDBC (Connection, Statement, etc.)
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {	
    // Atributo estático: Compartido por todas las instancias de la clase (Criterio 4)
    private static int contadorProcesados = 0;

    /**     Criterio 4: Sincronización de recursos compartidos.
     * La palabra 'synchronized' asegura que solo un hilo a la vez pueda incrementar el contador.
     * Justificación: Evita condiciones de carrera (Race Conditions) en entornos multihilo.    */
    
    public static synchronized void incrementarContadorGlobal() {
        contadorProcesados++; // Incrementa de forma segura el total de pedidos finalizados
    }
    public static int getContadorGlobal() { // Método para consultar el valor del contador desde la vista (Main)
        return contadorProcesados;   }
    
    /**     Criterio 2.3.2: Operación CREATE (Insertar)
     * Registra un nuevo pedido en la base de datos con estado inicial PENDIENTE.  */
    public boolean insertarPedido(Pedido p) {
        // SQL con parámetros (?) para prevenir ataques de Inyección SQL
        String sql = "INSERT INTO Pedido (id_pedido, cliente, producto, cantidad, estado) VALUES (?, ?, ?, ?, 'PENDIENTE')";
        
        // Criterio 5: Try-with-resources asegura el cierre de la conexión y del statement automáticamente
        try (Connection conn = ConexionBD.obtenerConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Mapeo de datos del objeto Pedido a la sentencia SQL
            pstmt.setInt(1, p.getIdPedido());
            pstmt.setString(2, p.getCliente());
            pstmt.setString(3, p.getProducto());
            pstmt.setInt(4, p.getCantidad());           
            pstmt.executeUpdate(); // Ejecuta la inserción en MySQL
            
        } catch (SQLException e) {
            // Criterio 5: Manejo de errores de base de datos
            System.err.println("Error al insertar: " + e.getMessage()); }
		return false;
    }
    /** Criterio 2.3.3: Operación READ (Consultar Pendientes)
     * Este método es crítico para los hilos. Se usa synchronized para que dos hilos
     * no seleccionen el mismo pedido exactamente al mismo tiempo. */
    public synchronized Pedido obtenerSiguientePendiente() {
        // Busca el primer pedido que aún no ha sido procesado
        String sql = "SELECT * FROM Pedido WHERE estado = 'PENDIENTE' LIMIT 1";       
        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                // Crea el objeto Pedido con la información recuperada de la BD
                Pedido p = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getString("cliente"),
                    rs.getString("producto"),
                    rs.getInt("cantidad"),
                    rs.getString("estado")
                );
                // Criterio 3: Cambia el estado inmediatamente para "apartar" el pedido
                actualizarEstado(p.getIdPedido(), "EN PROCESO", "SISTEMA");
                return p;            }
            //Manejo de excepcion
        } catch (SQLException e) {
            e.printStackTrace();
        } return null; /* Retorna null si no hay más pedidos pendientes*/   }
    
    /**     Criterio 2.3.4: Operación UPDATE (Actualizar)
     * Actualiza el estado del pedido y registra el nombre del hilo que realizó la tarea.  */
    public void actualizarEstado(int id, String estado, String nombreHilo) {
        String sql = "UPDATE Pedido SET estado = ?, hilo_proceso = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, estado);      // Nuevo estado (PROCESADO / EN PROCESO)
            pstmt.setString(2, nombreHilo);  // Nombre del hilo (ej. Hilo-Alfa)
            pstmt.setInt(3, id);             // ID del pedido a modificar            
            pstmt.executeUpdate(); // Aplica los cambios en la BD
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
        }
    }
    /** - Criterio 6: Generación de Reportes Finales.
     - Utiliza funciones de agregación de SQL (COUNT y SUM).*/
    public void mostrarReporte() {
        // Consultas SQL para obtener estadísticas
        String sqlTotal = "SELECT COUNT(*) as total FROM Pedido";
        String sqlProcesados = "SELECT COUNT(*) as total FROM Pedido WHERE estado = 'PROCESADO'";

        try (Connection conn = ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement()) {
            
            // Ejecución de la primera consulta: Total registrados
            ResultSet rs1 = stmt.executeQuery(sqlTotal);
            if(rs1.next()) System.out.println("Total pedidos registrados: " + rs1.getInt("total"));
            
            // Ejecución de la segunda consulta: Total procesados
            ResultSet rs2 = stmt.executeQuery(sqlProcesados);
            if(rs2.next()) System.out.println("Total pedidos procesados: " + rs2.getInt("total"));
            
            // Criterio 6.3: Cálculo de la suma total de productos vendidos
            String sqlSuma = "SELECT SUM(cantidad) AS total_productos FROM Pedido WHERE estado = 'PROCESADO'";
            try (Statement stmt1 = conn.createStatement();
                 ResultSet rs = stmt1.executeQuery(sqlSuma)) {
                if (rs.next()) {
                    System.out.println("Cantidad total de productos vendidos: " + rs.getInt("total_productos"));
                }
            }           
            // Criterio 6.4: Reporte detallado de auditoría por hilos
            String sqlListado = "SELECT id_pedido, hilo_proceso FROM Pedido WHERE estado = 'PROCESADO'";
            try (ResultSet rs = stmt.executeQuery(sqlListado)) {
                System.out.println("Detalle por Hilo:");
                while (rs.next()) {
                    // Imprime qué hilo trabajó cada ID de pedido
                    System.out.println("- Pedido #" + rs.getInt("id_pedido") + " procesado por: " + rs.getString("hilo_proceso"));
                }            }    
        } catch (SQLException e) {
            // Manejo de excepciones durante el reporte
            e.printStackTrace();      }
    }
    /**
     * Criterio 6: Generación de reportes y visualización de datos.
     * Este método consulta la base de datos y formatea la salida en consola
     * para que parezca una tabla real de base de datos.
     */
    public void listarPedidos() {
        // Definimos la consulta SQL para obtener todos los registros de la tabla
        String sql = "SELECT * FROM Pedido";
        
        // Criterio 5: Gestión de excepciones y cierre automático de recursos (Try-with-resources)
        // Abrimos la conexión y el statement dentro del paréntesis para asegurar su cierre.
        try (Connection conn = Conexion.ConexionBD.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n--- CONTENIDO ACTUAL DE LA BASE DE DATOS ---");
            
            // Encabezado de la tabla con formato de columnas:
            // %-5s significa un String alineado a la izquierda con 5 espacios.
            System.out.printf("%-5s | %-15s | %-15s | %-8s | %-12s | %-15s\n", 
                              "ID", "CLIENTE", "PRODUCTO", "CANT", "ESTADO", "HILO PROCESO");
            System.out.println("------------------------------------------------------------------------------------");

            // Criterio 2.3.3: Lectura de registros (Read)
            // El bucle rs.next() recorre cada fila devuelta por MySQL
            while (rs.next()) {
                // Imprimimos cada fila formateada para que coincida con el encabezado
                System.out.printf("%-5d | %-15s | %-15s | %-8d | %-12s | %-15s\n",
                    rs.getInt("id_pedido"),    // Obtiene el ID (Entero)
                    rs.getString("cliente"),    // Obtiene el Cliente (String)
                    rs.getString("producto"),   // Obtiene el Producto (String)
                    rs.getInt("cantidad"),      // Obtiene la Cantidad (Entero)
                    rs.getString("estado"),      // Obtiene el Estado (PENDIENTE/PROCESADO)
                    rs.getString("hilo_proceso") // Obtiene el nombre del hilo que lo trabajó
                );
            }
            System.out.println("------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            // Criterio 5: Manejo de errores en caso de que la consulta falle
            System.err.println("Error al consultar la tabla: " + e.getMessage());
        }
    }
}