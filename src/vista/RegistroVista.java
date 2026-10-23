package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Formulario para registrar una cuenta.
public class RegistroVista extends JDialog {

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;
    private final JComboBox<String> selectorRol;
    private final JButton botonRegistrar;

    public RegistroVista(JFrame ventanaPadre) {
        // Mientras está abierto, bloquea su ventana principal.
        super(ventanaPadre, "Registrarse — Clic y piensa", true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Crea los campos y las opciones de rol.
        campoUsuario = new JTextField(20);
        campoContrasena = new JPasswordField(20);

        selectorRol = new JComboBox<>(
            new String[] {"Estudiante", "Docente"}
        );

        botonRegistrar = new JButton("Registrar");

        // Organiza los datos en tres filas y dos columnas.
        JPanel panelCampos = new JPanel(
            new GridLayout(3, 2, 10, 10)
        );

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(campoContrasena);
        panelCampos.add(new JLabel("Rol:"));
        panelCampos.add(selectorRol);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonRegistrar);

        // Prepara el contenedor con márgenes.
        JPanel panelPrincipal = new JPanel(
            new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(ventanaPadre);
    }

    // Permiten consultar los datos del formulario.
    public String getNombreUsuario() {
        return campoUsuario.getText().trim();
    }

    public char[] getContrasena() {
        return campoContrasena.getPassword();
    }

    public String getRol() {
        return (String) selectorRol.getSelectedItem();
    }

    // Conecta el botón Registrar con el controlador.
    public void agregarAccionRegistrar(ActionListener accion) {
        botonRegistrar.addActionListener(accion);
    }

    // Muestra mensajes y permite limpiar la contraseña.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void limpiarContrasena() {
        campoContrasena.setText("");
    }
}