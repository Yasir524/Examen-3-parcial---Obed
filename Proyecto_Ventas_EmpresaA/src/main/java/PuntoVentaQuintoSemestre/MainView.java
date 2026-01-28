package PuntoVentaQuintoSemestre;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTabbedPane tabs;
    private JTable tablaProductos;
    private JTextField txtIdProd, txtNomProd, txtPrecio, txtStock;
    private JButton btnGuardar, btnEliminar, btnActualizar;

    public MainView() {
        setTitle("Sistema de Inventario - Versión Base");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // IMPORTANTE: Primero inicializamos los componentes
        initComponentes();

        tabs = new JTabbedPane();
        tabs.addTab("Productos", crearPanelProductos());
        tabs.addTab("Ventas", new JPanel()); 

        add(tabs);
    }

    private void initComponentes() {
        tablaProductos = new JTable();
        txtIdProd = new JTextField(10);
        txtNomProd = new JTextField(10);
        txtPrecio = new JTextField(10);
        txtStock = new JTextField(10);
        
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar");
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(5, 2));

        formulario.add(new JLabel("ID:"));
        formulario.add(txtIdProd); 
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNomProd); 

        panel.add(formulario, BorderLayout.WEST);
        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        
        return panel;
    }
}