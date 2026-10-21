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

// Formulario para introducir los datos de una nueva cuenta.
public class RegistroVista extends JDialog {

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;
    private final JComboBox<String> selectorRol;
    private final JButton botonRegistrar;

    public RegistroVista(JFrame ventanaPadre) {
        // Establece propietario, título y modalidad del diálogo.
        // Mientras ese diálogo esté abierto, no se puede interactuar con su ventana principal.
        super(ventanaPadre, "Registrarse — Clic y piensa", true);

        // Cierra el formulario sin terminar la aplicación.
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Crea los campos de usuario y contraseña.
        campoUsuario = new JTextField(20);
        campoContrasena = new JPasswordField(20);

        // Crea la lista desplegable con los roles disponibles.
        selectorRol = new JComboBox<>(
            new String[] {"Estudiante", "Docente"}
        );

        botonRegistrar = new JButton("Registrar");

        // Distribuye las etiquetas y los campos en tres filas y dos columnas.
        JPanel panelCampos = new JPanel(
            new GridLayout(3, 2, 10, 10)
        );

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(campoContrasena);
        panelCampos.add(new JLabel("Rol:"));
        panelCampos.add(selectorRol);

        // Agrupa el botón en un panel.
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonRegistrar);

        // Prepara el contenedor principal con separaciones y márgenes.
        JPanel panelPrincipal = new JPanel(
            new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Establece el contenido, ajusta el tamaño y centra el formulario.
        setContentPane(panelPrincipal);
        pack();
        setLocationRelativeTo(ventanaPadre);
    }

    // Devuelve el usuario sin espacios en los extremos.
    public String getNombreUsuario() {
        return campoUsuario.getText().trim();
    }

    // Devuelve la contraseña como arreglo de caracteres.
    public char[] getContrasena() {
        return campoContrasena.getPassword();
    }

    // Obtiene la opción seleccionada y la convierte al tipo String.
    public String getRol() {
        return (String) selectorRol.getSelectedItem();
    }

    // Permite al controlador conectar la acción del botón Registrar.
    public void agregarAccionRegistrar(ActionListener accion) {
        botonRegistrar.addActionListener(accion);
    }

    // Muestra los mensajes solicitados por el controlador.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Vacía el campo de contraseña.
    public void limpiarContrasena() {
        campoContrasena.setText("");
    }
}