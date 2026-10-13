package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaIngreso extends JDialog {

    private final JTextField campoUsuario;
    private final JPasswordField campoContrasena;
    private final JButton botonIngresar;

    public VentanaIngreso(JFrame ventanaPadre) {
        super(ventanaPadre, "Ingresar — Clic y piensa", true);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        campoUsuario = new JTextField(20);
        campoContrasena = new JPasswordField(20);
        botonIngresar = new JButton("Ingresar");

        JPanel panelCampos = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );

        panelCampos.add(new JLabel("Usuario:"));
        panelCampos.add(campoUsuario);
        panelCampos.add(new JLabel("Contraseña:"));
        panelCampos.add(campoContrasena);

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonIngresar);

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
}
