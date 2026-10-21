package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

// Formulario de ingreso asociado a la ventana principal.
public class VentanaIngreso extends JDialog {

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;
    private final JButton botonIngresar;

    
    public VentanaIngreso(JFrame ventanaPadre) {
        // Establece propietario, título y modalidad:
        // bloquea la interacción con la ventana principal mientras está abierto.
        super(ventanaPadre, "Ingresar — Clic y piensa", true);

        // Cierra el diálogo sin terminar la aplicación.
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Crea los campos y el botón. El 20 indica columnas de ancho preferido.
        campoUsuario = new JTextField(20);
        campoContrasena = new JPasswordField(20);
        botonIngresar = new JButton("Ingresar");

        // Organiza etiquetas y campos en dos filas y dos columnas.
        JPanel panelCampos = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(campoContrasena);

        // Agrupa el botón en su propio panel.
        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);

        // Prepara el contenedor principal con separaciones y márgenes.
        JPanel panelPrincipal = new JPanel(
            new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panelPrincipal.add(panelCampos, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Establece el contenido, ajusta el tamaño y centra el diálogo.
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

    // Permite al controlador conectar la acción del botón.
    public void agregarAccionIngresar(ActionListener accion) {
        botonIngresar.addActionListener(accion);
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