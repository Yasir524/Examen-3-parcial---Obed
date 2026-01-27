package CONTROLLER;

import VIEW.VistaPrincipal;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ControladorPrincipal implements ActionListener {
    private VistaPrincipal vista;
    private ControladorModelo cModelo;
    private ControladorVista cVista;
    private String rolUsuario;

    public ControladorPrincipal(VistaPrincipal vista, String rol) {
        this.vista = vista;
        this.rolUsuario = rol;
        this.cModelo = new ControladorModelo();
        this.cVista = new ControladorVista(vista);

        inicializarEventos();
        actualizarTodo();
        cVista.aplicarPermisos(rolUsuario);
    }

    private void inicializarEventos() {
        // Eventos de Productos
        vista.btnCrearProd.addActionListener(this);
        vista.btnActProd.addActionListener(this);
        vista.btnEliProd.addActionListener(this);
        vista.btnRefProd.addActionListener(e -> actualizarTodo());

        // Eventos de Clientes
        vista.btnCrearCli.addActionListener(this);
        vista.btnActCli.addActionListener(this);
        vista.btnEliCli.addActionListener(this);
        vista.btnRefCli.addActionListener(e -> actualizarTodo());

        // Eventos de Ventas (Carrito y Finalización)
        vista.btnAgregarCarrito.addActionListener(this);
        vista.btnVender.addActionListener(this);
        vista.btnEliminarCarrito.addActionListener(this);
        vista.btnRefrescarVentas.addActionListener(e -> actualizarTodo());
        vista.btnEliminarVenta.addActionListener(this);
        
        // Evento al seleccionar producto en el Combo de Ventas
        vista.comboProductos.addActionListener(e -> {
            String item = (String) vista.comboProductos.getSelectedItem();
            if (item != null) {
                String id = item.split(" - ")[0];
                vista.txtPrecioCarrito.setText(String.valueOf(cModelo.getPrecioProducto(id)));
            }
        });

        // Evento para ver detalles al hacer clic en una venta del historial
        vista.tablaVentasHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = vista.tablaVentasHistorial.getSelectedRow();
                if (fila >= 0) {
                    String idVenta = vista.tablaVentasHistorial.getValueAt(fila, 0).toString();
                    vista.tablaDetallesVenta.setModel(cModelo.leerDetallesVenta(idVenta));
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Lógica de ruteo de botones
        if (source == vista.btnCrearProd) crearProducto();
        else if (source == vista.btnActProd) actualizarProducto();
        else if (source == vista.btnEliProd) eliminarProducto();
        else if (source == vista.btnCrearCli) crearCliente();
        else if (source == vista.btnEliCli) eliminarCliente();
        else if (source == vista.btnAgregarCarrito) agregarAlCarrito();
        else if (source == vista.btnVender) finalizarVenta();
        else if (source == vista.btnEliminarVenta) eliminarVenta();
        else if (source == vista.btnEliminarCarrito) quitarDelCarrito();
    }

    // --- MÉTODOS DE PRODUCTOS ---
    private void crearProducto() {
        try {
            String id = vista.txtIdProd.getText();
            String nom = vista.txtNomProd.getText();
            String desc = vista.txtDescProd.getText();
            double precio = Double.parseDouble(vista.txtPrecio.getText());
            int stock = Integer.parseInt(vista.txtStock.getText());

            if (cModelo.crearProducto(id, nom, desc, precio, stock)) {
                JOptionPane.showMessageDialog(vista, "✅ Producto Guardado");
                actualizarTodo();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "❌ Error: Datos numéricos inválidos");
        }
    }
    private void actualizarProducto() {
        try {
            String id = vista.txtIdProd.getText();
            String nom = vista.txtNomProd.getText();
            String desc = vista.txtDescProd.getText();
            double precio = Double.parseDouble(vista.txtPrecio.getText());
            int stock = Integer.parseInt(vista.txtStock.getText());

            if (cModelo.actualizarProducto(id, nom, desc, precio, stock)) {
                JOptionPane.showMessageDialog(vista, "✅ Producto actualizado correctamente");
                actualizarTodo(); // Para refrescar la tabla y ver el cambio
            } else {
                JOptionPane.showMessageDialog(vista, "❌ No se pudo actualizar. Verifica el ID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "❌ Error: Revisa que precio y stock sean números.");
        }
    }
    private void eliminarProducto() {
        int fila = vista.tablaProductos.getSelectedRow();
        if (fila >= 0) {
            String id = vista.tablaProductos.getValueAt(fila, 0).toString();
            if (cModelo.borrarProducto(id)) {
                actualizarTodo();
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un producto de la tabla");
        }
    }

    // --- MÉTODOS DE CLIENTES ---
    private void crearCliente() {
        String id = vista.txtIdCli.getText();
        String nom = vista.txtNomCli.getText();
        String ape = vista.txtApeCli.getText();
        String tel = vista.txtTelCli.getText();
        String dir = vista.txtDirCli.getText();

        if (cModelo.crearCliente(id, nom, ape, tel, dir)) {
            JOptionPane.showMessageDialog(vista, "✅ Cliente Registrado");
            actualizarTodo();
        }
    }

    private void eliminarCliente() {
        int fila = vista.tablaClientes.getSelectedRow();
        if (fila >= 0) {
            String id = vista.tablaClientes.getValueAt(fila, 0).toString();
            if (cModelo.borrarCliente(id)) {
                actualizarTodo();
            }
        }
    }

    // --- LÓGICA DE VENTAS ---
    private void agregarAlCarrito() {
        try {
            String item = (String) vista.comboProductos.getSelectedItem();
            if (item == null) return;

            String id = item.split(" - ")[0];
            String nombre = item.split(" - ")[1];
            double precio = Double.parseDouble(vista.txtPrecioCarrito.getText());
            int cant = Integer.parseInt(vista.txtCantidadCarrito.getText());
            double subtotal = precio * cant;

            vista.modeloCarrito.addRow(new Object[]{id, nombre, precio, cant, subtotal});
            actualizarTotalPagar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "❌ Cantidad inválida");
        }
    }

    private void quitarDelCarrito() {
        int fila = vista.tablaCarrito.getSelectedRow();
        if (fila >= 0) {
            vista.modeloCarrito.removeRow(fila);
            actualizarTotalPagar();
        }
    }

    private void actualizarTotalPagar() {
        double total = 0;
        for (int i = 0; i < vista.modeloCarrito.getRowCount(); i++) {
            total += (double) vista.modeloCarrito.getValueAt(i, 4);
        }
        vista.lblTotalCarrito.setText("TOTAL: $" + total);
    }

    private void finalizarVenta() {
        if (vista.modeloCarrito.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío");
            return;
        }

        String idCli = ((String) vista.comboClientes.getSelectedItem()).split(" - ")[0];
        List<String> ids = new ArrayList<>();
        List<Integer> cants = new ArrayList<>();

        for (int i = 0; i < vista.modeloCarrito.getRowCount(); i++) {
            ids.add(vista.modeloCarrito.getValueAt(i, 0).toString());
            cants.add(Integer.parseInt(vista.modeloCarrito.getValueAt(i, 3).toString()));
        }

        String resultado = cModelo.realizarVenta(idCli, ids, cants);
        if (resultado.startsWith("Venta Exitosa")) {
            JOptionPane.showMessageDialog(vista, "🎉 " + resultado);
            cVista.limpiarTablasVenta();
            actualizarTodo();
            vista.lblTotalCarrito.setText("TOTAL: $0.00");
        } else {
            JOptionPane.showMessageDialog(vista, "❌ " + resultado);
        }
    }

    private void eliminarVenta() {
        int fila = vista.tablaVentasHistorial.getSelectedRow();
        if (fila >= 0) {
            String idVenta = vista.tablaVentasHistorial.getValueAt(fila, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(vista, "¿Anular venta " + idVenta + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (cModelo.eliminarVenta(idVenta)) {
                    actualizarTodo();
                }
            }
        }
    }

    private void actualizarTodo() {
        vista.tablaProductos.setModel(cModelo.leerProductos());
        vista.tablaClientes.setModel(cModelo.leerClientes());
        vista.tablaVentasHistorial.setModel(cModelo.leerVentas());
        cVista.actualizarCombos(cModelo.obtenerListaClientes(), cModelo.obtenerListaProductos());
    }

}
