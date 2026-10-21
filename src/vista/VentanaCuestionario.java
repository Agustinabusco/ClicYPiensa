package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

// Muestra las preguntas y las opciones de respuesta.
public class VentanaCuestionario extends JFrame {

    private final JLabel etiquetaProgreso;
    private final JLabel etiquetaPregunta;
    private final JButton botonVerdadero;
    private final JButton botonFalso;

    public VentanaCuestionario() {
        super("Cuestionario — Clic y piensa");

        // Configura el tamaño, el cierre y la ubicación.
        setSize(650, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Prepara las etiquetas que actualizará el controlador.
        etiquetaProgreso = new JLabel(
            "Pregunta",
            SwingConstants.CENTER
        );

        etiquetaPregunta = new JLabel(
            "",
            SwingConstants.CENTER
        );

        // Crea los botones y los agrupa en un panel.
        botonVerdadero = new JButton("Verdadero");
        botonFalso = new JButton("Falso");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonVerdadero);
        panelBotones.add(botonFalso);

        // Organiza el contenido y agrega márgenes.
        JPanel panelPrincipal = new JPanel(
            new BorderLayout(10, 10)
        );

        panelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panelPrincipal.add(etiquetaProgreso, BorderLayout.NORTH);
        panelPrincipal.add(etiquetaPregunta, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    // Actualiza el enunciado y el número de pregunta.
    public void mostrarPregunta(
            String enunciado,
            int numero,
            int total) {

        etiquetaProgreso.setText(
            "Pregunta " + numero + " de " + total
        );

        // HTML permite distribuir un enunciado largo en varias líneas.
        etiquetaPregunta.setText(
            "<html><div style='text-align: center;'>"
            + enunciado
            + "</div></html>"
        );
    }

    // Permiten al controlador conectar las acciones de los botones.
    public void agregarAccionVerdadero(ActionListener accion) {
        botonVerdadero.addActionListener(accion);
    }

    public void agregarAccionFalso(ActionListener accion) {
        botonFalso.addActionListener(accion);
    }

    // Muestra el resultado enviado por el controlador.
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}