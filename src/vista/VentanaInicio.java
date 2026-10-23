
package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Ventana de bienvenida de la aplicación.
public class VentanaInicio extends JFrame {

    private final JButton botonIngresar;
    private final JButton botonRegistrarse;

    public VentanaInicio() {
        super("Clic y piensa");

        // Configura el tamaño, el cierre y la ubicación.
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crea el título y los botones.
        JLabel titulo = new JLabel(
            "Bienvenidos a Clic y piensa",
            SwingConstants.CENTER
        );

        botonIngresar = new JButton("Ingresar");
        botonRegistrarse = new JButton("Registrarse");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);
        panelBotones.add(botonRegistrarse);

        // Distribuye los componentes en la ventana.
        add(titulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Permiten conectar las acciones desde el controlador.
    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public JButton getBotonRegistrarse() {
        return botonRegistrarse;
    }
}