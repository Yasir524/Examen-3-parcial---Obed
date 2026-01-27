 package Modelo; // Criterio 1: Estructura del proyecto bajo el patrón MVC (Capa Modelo)

     //Clase que representa la entidad Pedido en el sistema.
     // Sirve como el objeto de transferencia de datos entre la BD y la lógica del programa.

    public class Pedido {
        // Atributos privados para cumplir con el principio de Encapsulamiento
        private int idPedido;      // Identificador único del pedido (Primary Key en BD)
        private String cliente;    // Nombre del cliente que realiza el pedido
        private String producto;   // Nombre del artículo solicitado
        private int cantidad;      // Unidades del producto
        private String estado;     // Estado actual: PENDIENTE, EN PROCESO o PROCESADO
        private String hiloProceso;// Nombre del hilo que gestionó el pedido (Auditoría)

        /**
         * Constructor: Inicializa un nuevo objeto Pedido.
         * Se usa al recuperar datos de la BD o al crear uno nuevo desde el teclado.
         */
        public Pedido(int idPedido, String cliente, String producto, int cantidad, String estado) {
            this.idPedido = idPedido; // Asigna el ID
            this.cliente = cliente;   // Asigna el Cliente
            this.producto = producto; // Asigna el Producto
            this.cantidad = cantidad; // Asigna la Cantidad
            this.estado = estado;     // Asigna el Estado inicial
        }

        // --- MÉTODOS GETTERS ---
        // Criterio: Permiten que otras capas (como el DAO) accedan a la información privada del objeto.

        public int getIdPedido() { 
            return idPedido; // Retorna el ID para usarlo en las cláusulas WHERE de SQL
        }

        public String getProducto() { 
            return producto; // Retorna el producto para el reporte final
        }

        public int getCantidad() { 
            return cantidad; // Retorna la cantidad para los cálculos de suma (Criterio 6.3)
        }

        public String getCliente() {
            return cliente; // Retorna el nombre del cliente
        }
        
        public String getEstado() {
            return estado; // Retorna si el pedido está PENDIENTE o PROCESADO
        }

        // --- MÉTODOS SETTERS (Opcionales pero recomendados) ---
        public void setEstado(String estado) {
            this.estado = estado; // Permite actualizar el estado del objeto en memoria
        }

        public void setHiloProceso(String hiloProceso) {
            this.hiloProceso = hiloProceso; // Asigna el nombre del hilo al objeto
        }
}