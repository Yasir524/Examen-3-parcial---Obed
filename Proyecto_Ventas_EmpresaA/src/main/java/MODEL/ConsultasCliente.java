package MODEL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ConsultasCliente {
    private Connection con;

    public ConsultasCliente() {
        this.con = Conexion.getConexion();
    }

    public DefaultTableModel leer() {
        String[] col = {"ID", "Nombre", "Apellido", "Teléfono", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, col);
        String sql = ConsultasSQL.CLI_SELECT_ALL;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("IDCLIENTE"),
                    rs.getString("NOMBRECLIENTE"),
                    rs.getString("APELLIDOCLIENTE"),
                    rs.getString("TELEFONO"),
                    rs.getString("DIRECCION")
                });
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al leer clientes: " + e.getMessage());
        }
        return modelo;
    }

    public List<String> obtenerListaClientes() {
        List<String> lista = new ArrayList<>();
        String sql = ConsultasSQL.CLI_ID_NOMBRE;

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                // Formato para el ComboBox: "ID - Nombre"
                lista.add(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error en lista clientes: " + e.getMessage());
        }
        return lista;
    }

    // Método para insertar (CRUD)
    public boolean crear(String id, String nombre, String apellido, String tel, String dir) {
        String sql = ConsultasSQL.CLI_INSERT;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, tel);
            ps.setString(5, dir);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar cliente: " + e.getMessage());
            return false;
        }
    }
    public boolean borrar(String id) {
        try (PreparedStatement ps = con.prepareStatement(ConsultasSQL.CLI_DELETE)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}