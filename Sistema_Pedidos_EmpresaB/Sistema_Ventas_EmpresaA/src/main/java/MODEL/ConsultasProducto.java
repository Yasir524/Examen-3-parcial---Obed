package MODEL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ConsultasProducto {
    private Connection con;

    public ConsultasProducto() {
        this.con = Conexion.getConexion();
    }

    // Método que causaba el error de columna
    public double getPrecio(String id) {
        double precio = 0;
        // Usamos la constante centralizada
        String sql = ConsultasSQL.PROD_PRECIO; 
        
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // CAMBIO CLAVE: El nombre debe ser "PRECIO" (como en tu SQL)
                    // y no "precioproducto"
                    precio = rs.getDouble("PRECIO"); 
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en getPrecio: " + e.getMessage());
        }
        return precio;
    }

    public DefaultTableModel leer() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");

        String sql = ConsultasSQL.PROD_SELECT_ALL;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("IDPRODUCTO"),
                    rs.getString("NOMBREPRODUCTO"),
                    rs.getString("DESCRIPCION"),
                    rs.getDouble("PRECIO"),
                    rs.getInt("STOCK")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al leer productos: " + e.getMessage());
        }
        return modelo;
    }

    public List<String> obtenerListaProductos() {
        List<String> lista = new ArrayList<>();
        String sql = ConsultasSQL.PROD_ID_NOMBRE;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                // Formato para el ComboBox: "ID - Nombre"
                lista.add(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en lista productos: " + e.getMessage());
        }
        return lista;
    }
 // Dentro de ConsultasProducto.java
    public boolean crear(String id, String nom, String des, double pre, int sto) {
        try (PreparedStatement ps = con.prepareStatement(ConsultasSQL.PROD_INSERT)) {
            ps.setString(1, id); ps.setString(2, nom); ps.setString(3, des);
            ps.setDouble(4, pre); ps.setInt(5, sto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
    public boolean actualizar(String id, String nom, String desc, double pre, int stock) {
        try (PreparedStatement ps = con.prepareStatement(ConsultasSQL.PROD_UPDATE)) {
            ps.setString(1, nom);
            ps.setString(2, desc);
            ps.setDouble(3, pre);
            ps.setInt(4, stock);
            ps.setString(5, id); 
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean borrar(String id) {
        try (PreparedStatement ps = con.prepareStatement(ConsultasSQL.PROD_DELETE)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public int getStock(String id) {
        try (PreparedStatement ps = con.prepareStatement(ConsultasSQL.PROD_STOCK)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("STOCK");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}