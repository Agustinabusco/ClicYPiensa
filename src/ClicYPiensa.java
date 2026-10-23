import javax.swing.SwingUtilities;
import controlador.ControladorInicio;
import vista.VentanaInicio;

// Inicia la aplicación.
public class ClicYPiensa {

    public static void main(String[] args) {

        // Prepara la interfaz en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventana = new VentanaInicio();
            new ControladorInicio(ventana);
            ventana.setVisible(true);
        });
    }
}