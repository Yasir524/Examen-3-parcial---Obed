package Conexion; // Define el paquete para organizar el código según el modelo MVC

// Importaciones necesarias para la conectividad con bases de datos relacionales
import java.sql.Connection;    // Interfaz para la sesión con la base de datos
import java.sql.DriverManager; // Clase para gestionar el conjunto de controladores JDBC
import java.sql.SQLException;  // Clase para manejar errores específicos de SQL

/**
 - Clase encargada de gestionar la conectividad con MySQL.
 - Cumple con el Criterio 2.2 del examen: Implementar clase ConexionBD.
 */
public class ConexionBD {
    
    // Constantes de configuración (Encapsulamiento de datos de conexión)
    // URL: Especifica el protocolo (jdbc), el motor (mysql), el servidor (localhost/puerto) y la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/SistemaPedidos";
    
    // USER: Nombre de usuario por defecto del servidor MySQL
    private static final String USER = "root";
    
    // PASS: Credencial de acceso para el driver 
    private static final String PASS = "root"; 

    /**
     * Método estático para obtener la conexión. 
     * @return Objeto Connection activo.
     * @throws SQLException Si ocurre un error de acceso o las credenciales son incorrectas.
     */
    public static Connection obtenerConexion() throws SQLException {
        try {
            // Criterio 2.1: Carga dinámica del Driver de MySQL en memoria.
            // Esto permite que el DriverManager sepa qué motor de BD estamos usando.
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Criterio 2.2: Intenta establecer la conexión y la retorna.
            // Se pasan los parámetros de URL, usuario y contraseña definidos arriba.
            return DriverManager.getConnection(URL, USER, PASS);
            
        } catch (ClassNotFoundException e) {
            // Manejo de excepciones (Criterio 5): 
            // Si el archivo .jar del conector no está en el Classpath, lanzamos un error descriptivo.
            throw new SQLException("Driver JDBC no encontrado en las librerías: " + e.getMessage());
        }
    }
}