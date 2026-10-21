
package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Ventana de bienvenida que hereda de JFrame.
public class VentanaInicio extends JFrame {

    // Referencias a los botones que utilizará el controlador.
    private final JButton botonIngresar;
    private final JButton botonRegistrarse;

    public VentanaInicio() {
        // Llama al constructor de JFrame y establece el título.
        super("Clic y piensa");

        // Configura el tamaño, el cierre de la aplicación y la ubicación.
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crea la etiqueta con el texto centrado horizontalmente.
        JLabel titulo = new JLabel(
            "Bienvenidos a Clic y piensa",
            SwingConstants.CENTER
        );

        // Crea los botones y los agrupa en un panel.
        botonIngresar = new JButton("Ingresar");
        botonRegistrarse = new JButton("Registrarse");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);
        panelBotones.add(botonRegistrarse);

        // Ubica la etiqueta en el centro y el panel en la parte inferior.
        add(titulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Permiten al controlador acceder a los botones y conectar sus acciones.
    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public JButton getBotonRegistrarse() {
        return botonRegistrarse;
    }
}