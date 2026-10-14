
package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaInicio extends JFrame {

    private JButton botonIngresar;
    private JButton botonRegistrarse;

    public VentanaInicio() {
        super("Clic y piensa");

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel(
            "Bienvenidos a Clic y piensa",
            SwingConstants.CENTER
        );

        botonIngresar = new JButton("Ingresar");
        botonRegistrarse = new JButton("Registrarse");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);
        panelBotones.add(botonRegistrarse);

        add(titulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public JButton getBotonRegistrarse() {
        return botonRegistrarse;
    }
}
