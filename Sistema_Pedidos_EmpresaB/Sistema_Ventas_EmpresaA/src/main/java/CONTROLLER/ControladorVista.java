package CONTROLLER;

import VIEW.VistaPrincipal;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ControladorVista {
    private VistaPrincipal vista;

    public ControladorVista(VistaPrincipal vista) {
        this.vista = vista;
    }

    public void aplicarPermisos(String rolUsuario) {
        if (rolUsuario != null && rolUsuario.equalsIgnoreCase("Vendedor")) {
            vista.pestañas.setEnabledAt(0, false);
            vista.pestañas.setEnabledAt(1, false);
            vista.pestañas.setSelectedIndex(2);
        }
    }

    public void actualizarCombos(List<String> clientes, List<String> productos) {
        vista.comboClientes.removeAllItems();
        clientes.forEach(vista.comboClientes::addItem);
        
        vista.comboProductos.removeAllItems();
        productos.forEach(vista.comboProductos::addItem);
    }

    public void recalcularTotalCarrito() {
        double total = 0;
        for (int i = 0; i < vista.modeloCarrito.getRowCount(); i++) {
            total += Double.parseDouble(vista.modeloCarrito.getValueAt(i, 4).toString());
        }
        vista.lblTotalCarrito.setText("TOTAL: $" + total);
    }
    
    public void limpiarTablasVenta() {
        vista.modeloCarrito.setRowCount(0);
        recalcularTotalCarrito();
    }
}