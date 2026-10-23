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

// Muestra las preguntas y permite responder verdadero o falso.
public class VentanaCuestionario extends JFrame {

    private final JLabel etiquetaProgreso;
    private final JLabel etiquetaPregunta;
    private final JButton botonVerdadero;
    private final JButton botonFalso;

    public VentanaCuestionario() {
        super("Cuestionario — Clic y piensa");

        // Configura la ventana.
        setSize(650, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Prepara el progreso y el enunciado.
        etiquetaProgreso = new JLabel(
            "Pregunta", SwingConstants.CENTER
        );

        etiquetaPregunta = new JLabel(
            "", SwingConstants.CENTER
        );

        // Crea las opciones de respuesta.
        botonVerdadero = new JButton("Verdadero");
        botonFalso = new JButton("Falso");

        JPanel panelBotones = new JPanel();
        panelBotones.add(botonVerdadero);
        panelBotones.add(botonFalso);

        // Distribuye los componentes con márgenes.
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

    // Actualiza el enunciado y la cantidad de preguntas.
    public void mostrarPregunta(
            String enunciado,
            int numero,
            int total) {

        etiquetaProgreso.setText(
            "Pregunta " + numero + " de " + total
        );

        // Escapa símbolos para mostrarlos como texto dentro del HTML.
        String texto = enunciado
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;");

        // HTML permite mostrar el texto en varias líneas.
        etiquetaPregunta.setText(
            "<html><div style='text-align: center;'>"
            + texto
            + "</div></html>"
        );
    }

    // Conectan los botones con las acciones del controlador.
    public void agregarAccionVerdadero(ActionListener accion) {
        botonVerdadero.addActionListener(accion);
    }

    public void agregarAccionFalso(ActionListener accion) {
        botonFalso.addActionListener(accion);
    }

    // Permite elegir el formato de la calificación.
    public boolean elegirNotaSobreDiez() {
        String[] opciones = {"Porcentaje", "Nota sobre 10"};

        int seleccion = JOptionPane.showOptionDialog(
            this,
            "¿Cómo querés ver la calificación?",
            "Formato de calificación",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        // Si cierra el diálogo, se utiliza porcentaje.
        return seleccion == 1;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}