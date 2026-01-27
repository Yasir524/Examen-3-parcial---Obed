package CONTROLLER;

import MODEL.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ControladorModelo {
    private ConsultasProducto consultasProducto;
    private ConsultasCliente consultasCliente;
    private ConsultasVenta consultasVenta;

    public ControladorModelo() {
        this.consultasProducto = new ConsultasProducto();
        this.consultasCliente = new ConsultasCliente();
        this.consultasVenta = new ConsultasVenta();
    }

    // Métodos delegados de Producto
    public DefaultTableModel leerProductos() { return consultasProducto.leer(); }
    public boolean crearProducto(String id, String nom, String des, double pre, int sto) {
        return consultasProducto.crear(id, nom, des, pre, sto);
    }
    
 // En CONTROLLER.ControladorModelo.java
    public boolean actualizarProducto(String id, String nom, String des, double pre, int sto) {
        return consultasProducto.actualizar(id, nom, des, pre, sto);
    }
    
    public double getPrecioProducto(String id) { return consultasProducto.getPrecio(id); }
    public int getStockProducto(String id) { return consultasProducto.getStock(id); }
    public List<String> obtenerListaProductos() { return consultasProducto.obtenerListaProductos(); }
    public boolean borrarProducto(String id) { return consultasProducto.borrar(id); }

    // Métodos delegados de Cliente
    public DefaultTableModel leerClientes() { return consultasCliente.leer(); }
    public boolean crearCliente(String id, String nom, String ape, String tel, String dir) {
        return consultasCliente.crear(id, nom, ape, tel, dir);
    }
    public List<String> obtenerListaClientes() { return consultasCliente.obtenerListaClientes(); }
    public boolean borrarCliente(String id) { return consultasCliente.borrar(id); }

    // Métodos delegados de Ventas
    public DefaultTableModel leerVentas() { return consultasVenta.leerVentas(); }
    public DefaultTableModel leerDetallesVenta(String id) { return consultasVenta.leerDetalles(id); }
    public String realizarVenta(String idCli, List<String> ids, List<Integer> cants) {
        return consultasVenta.realizarVenta(idCli, ids, cants);
    }
    public boolean eliminarVenta(String id) { return consultasVenta.eliminarVenta(id); }

	
}