package VIEW;

import javax.swing.*;
import java.awt.*;

public class VistaLogin extends JDialog {

    public JTextField txtUsuario;
    public JPasswordField txtPassword;
    public JButton btnEntrar;
    private boolean autenticado = false;
    private String rolUsuario = "";

    public VistaLogin() {
        setTitle("Acceso al Sistema");
        setSize(420, 300);
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(139, 0, 0));
        pnlHeader.setPreferredSize(new Dimension(400, 60));

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        pnlHeader.add(lblTitulo);

        // PANEL CENTRAL
        JPanel pnlCentral = new JPanel(new GridBagLayout());
        pnlCentral.setBackground(Color.WHITE);
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        pnlCentral.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        JLabel lblRol = new JLabel("Admin");
        lblRol.setFont(new Font("Arial", Font.BOLD, 14));
        pnlCentral.add(lblRol, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        pnlCentral.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        pnlCentral.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        pnlCentral.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        pnlCentral.add(txtPassword, gbc);

        btnEntrar = new JButton("Ingresar");
        Estilos.aplicarEstiloBoton(btnEntrar);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 0, 0);
        pnlCentral.add(btnEntrar, gbc);

        add(pnlHeader, BorderLayout.NORTH);
        add(pnlCentral, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> validar());
        txtPassword.addActionListener(e -> validar());
    }

    private void validar() {
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        if (user.equals("admin") && pass.equals("1234")) {
            autenticado = true;
            rolUsuario = "Administrador";
            dispose();
        } else if (user.equals("vendedor") && pass.equals("5678")) {
            autenticado = true;
            rolUsuario = "Vendedor";
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean esAutenticado() { return autenticado; }
    public String getRol() { return rolUsuario; }
}
