package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaPrincipal extends JFrame {
    public JTabbedPane pestañas;
    
    // Tablas
    public JTable tablaProductos, tablaClientes, tablaCarrito, tablaVentasHistorial, tablaDetallesVenta;
    
    // Botones Productos
    public JButton btnCrearProd, btnActProd, btnEliProd, btnRefProd;
    // Botones Clientes
    public JButton btnCrearCli, btnActCli, btnEliCli, btnRefCli;
    // Botones Ventas
    public JButton btnAgregarCarrito, btnVender, btnEliminarCarrito, btnRefrescarVentas, btnEliminarVenta;
    
    // Campos de Texto Productos (IMPORTANTE: Deben ser JTextField)
    public JTextField txtIdProd, txtNomProd, txtDescProd, txtPrecio, txtStock;
    
    // Campos de Texto Clientes
    public JTextField txtIdCli, txtNomCli, txtApeCli, txtTelCli, txtDirCli;
    
    // Componentes de Ventas
    public JComboBox<String> comboProductos, comboClientes;
    public JTextField txtPrecioCarrito, txtCantidadCarrito;
    public JLabel lblTotalCarrito;
    public DefaultTableModel modeloCarrito;

    public VistaPrincipal() {
        setTitle("Sistema de Punto de Venta - Proyecto BDII");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // 1. Inicializar objetos
        initComponentes();
        
        // 2. Construir Interfaz
        pestañas = new JTabbedPane();
        pestañas.addTab("Productos", crearPanelProductos());
        pestañas.addTab("Clientes", crearPanelClientes());
        pestañas.addTab("Ventas", crearPanelVentas());

        add(pestañas);
        setLocationRelativeTo(null);
    }

    private void initComponentes() {
        // Inicialización de Tablas
        tablaProductos = new JTable();
        tablaClientes = new JTable();
        tablaVentasHistorial = new JTable();
        tablaDetallesVenta = new JTable();
        
        modeloCarrito = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Cant", "Subtotal"}, 0);
        tablaCarrito = new JTable(modeloCarrito);

        // Inicialización de Campos Producto
        txtIdProd = new JTextField(10);
        txtNomProd = new JTextField(15);
        txtDescProd = new JTextField(20);
        txtPrecio = new JTextField(8);
        txtStock = new JTextField(5);

        // Inicialización de Campos Cliente
        txtIdCli = new JTextField(10);
        txtNomCli = new JTextField(15);
        txtApeCli = new JTextField(15);
        txtTelCli = new JTextField(10);
        txtDirCli = new JTextField(20);

        // Inicialización de Botones
        btnCrearProd = new JButton("Crear");
        btnActProd = new JButton("Actualizar");
        btnEliProd = new JButton("Eliminar");
        btnRefProd = new JButton("Refrescar");

        btnCrearCli = new JButton("Crear");
        btnActCli = new JButton("Actualizar");
        btnEliCli = new JButton("Eliminar");
        btnRefCli = new JButton("Refrescar");

        btnAgregarCarrito = new JButton("Añadir");
        btnVender = new JButton("Finalizar Venta");
        btnEliminarCarrito = new JButton("Quitar");
        btnRefrescarVentas = new JButton("Refrescar Historial");
        btnEliminarVenta = new JButton("Anular Venta");

        // Ventas
        comboProductos = new JComboBox<>();
        comboClientes = new JComboBox<>();
        txtPrecioCarrito = new JTextField(10);
        txtPrecioCarrito.setEditable(false); 
        txtCantidadCarrito = new JTextField("1", 5);
        lblTotalCarrito = new JLabel("TOTAL: $0.00");
        lblTotalCarrito.setFont(new Font("Arial", Font.BOLD, 18));
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(); 
        formulario.add(new JLabel("ID:")); formulario.add(txtIdProd);
        formulario.add(new JLabel("Nombre:")); formulario.add(txtNomProd);
        formulario.add(new JLabel("Descripción:")); formulario.add(txtDescProd); 
        formulario.add(new JLabel("Precio:")); formulario.add(txtPrecio);
        formulario.add(new JLabel("Stock:")); formulario.add(txtStock);

        JPanel botones = new JPanel();
        botones.add(btnCrearProd); botones.add(btnActProd); 
        botones.add(btnEliProd); botones.add(btnRefProd);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formulario = new JPanel(new GridLayout(2, 4)); 
        formulario.add(new JLabel("ID:")); formulario.add(txtIdCli);
        formulario.add(new JLabel("Nombre:")); formulario.add(txtNomCli);
        formulario.add(new JLabel("Apellido:")); formulario.add(txtApeCli); 
        formulario.add(new JLabel("Teléfono:")); formulario.add(txtTelCli); 
        formulario.add(new JLabel("Dirección:")); formulario.add(txtDirCli); 

        JPanel botones = new JPanel();
        botones.add(btnCrearCli); botones.add(btnEliCli); botones.add(btnRefCli);

        panel.add(formulario, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaClientes), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Panel de Selección (Arriba)
        JPanel seleccion = new JPanel();
        seleccion.add(new JLabel("Cliente:")); seleccion.add(comboClientes);
        seleccion.add(new JLabel("Producto:")); seleccion.add(comboProductos);
        seleccion.add(new JLabel("Precio:")); seleccion.add(txtPrecioCarrito);
        seleccion.add(new JLabel("Cant:")); seleccion.add(txtCantidadCarrito);
        seleccion.add(btnAgregarCarrito);

        // Panel Central (Carrito y Botones)
        JPanel centro = new JPanel(new GridLayout(1, 2));
        centro.add(new JScrollPane(tablaCarrito));
        
        // Panel Derecha (Historial)
        JPanel historial = new JPanel(new BorderLayout());
        historial.add(new JLabel("HISTORIAL DE VENTAS"), BorderLayout.NORTH);
        historial.add(new JScrollPane(tablaVentasHistorial), BorderLayout.CENTER);
        historial.add(new JScrollPane(tablaDetallesVenta), BorderLayout.SOUTH);
        
        centro.add(historial);

        // Panel Inferior (Acciones y Total)
        JPanel acciones = new JPanel();
        acciones.add(btnVender);
        acciones.add(btnEliminarCarrito);
        acciones.add(btnEliminarVenta);
        acciones.add(lblTotalCarrito);

        panel.add(seleccion, BorderLayout.NORTH);
        panel.add(centro, BorderLayout.CENTER);
        panel.add(acciones, BorderLayout.SOUTH);
        
        return panel;
    }
}