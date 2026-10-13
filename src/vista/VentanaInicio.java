package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaInicio extends JFrame {

    public VentanaInicio() {
        super("Clic y piensa");

        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel(
            "Bienvenidos a Clic y piensa",
            SwingConstants.CENTER
        );

        JButton botonIngresar = new JButton("Ingresar");
        JButton botonRegistrarse = new JButton("Registrarse");

        // Al pulsar Ingresar, se crea y muestra el formulario.
        botonIngresar.addActionListener(evento -> {
            VentanaIngreso ingreso = new VentanaIngreso(this);
            ingreso.setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);
        panelBotones.add(botonRegistrarse);

        add(titulo, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }
}
