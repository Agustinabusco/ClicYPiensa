package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

// Formulario para agregar preguntas al cuestionario.
public class VentanaDocente extends JFrame {

    private final JTextArea campoEnunciado;
    private final JComboBox<String> selectorRespuesta;
    private final JButton botonGuardar;

    public VentanaDocente() {
        super("Agregar preguntas — Clic y piensa");

        // Configura la ventana sin cerrar toda la aplicación.
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Prepara el enunciado con ajuste automático de líneas.
        campoEnunciado = new JTextArea(5, 30);
        campoEnunciado.setLineWrap(true);
        campoEnunciado.setWrapStyleWord(true);

        selectorRespuesta = new JComboBox<>(
            new String[] {"Verdadero", "Falso"}
        );

        botonGuardar = new JButton("Guardar pregunta");

        // Agrupa la respuesta correcta y el botón.
        JPanel panelInferior = new JPanel();
        panelInferior.add(new JLabel("Respuesta correcta:"));
        panelInferior.add(selectorRespuesta);
        panelInferior.add(botonGuardar);

        // Distribuye los componentes y agrega márgenes.
        JPanel panelPrincipal = new JPanel(
            new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panelPrincipal.add(
            new JLabel("Escribí el enunciado:"),
            BorderLayout.NORTH
        );

        panelPrincipal.add(
            new JScrollPane(campoEnunciado),
            BorderLayout.CENTER
        );

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        setContentPane(panelPrincipal);
    }

    // Devuelve el enunciado escrito.
    public String getEnunciado() {
        return campoEnunciado.getText().trim();
    }

    // La primera opción corresponde a verdadero.
    public boolean getRespuestaCorrecta() {
        return selectorRespuesta.getSelectedIndex() == 0;
    }

    // Conecta la acción de guardar.
    public void agregarAccionGuardar(ActionListener accion) {
        botonGuardar.addActionListener(accion);
    }

    // Prepara el formulario para otra pregunta.
    public void limpiarCampos() {
        campoEnunciado.setText("");
        selectorRespuesta.setSelectedIndex(0);
        campoEnunciado.requestFocusInWindow();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}