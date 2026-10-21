// Clases necesarias para iniciar la interfaz y conectar su controlador.
import javax.swing.SwingUtilities;
import controlador.ControladorInicio;
import vista.VentanaInicio;

public class ClicYPiensa {

    // Punto de entrada de la aplicación.
    public static void main(String[] args) {

        // Crea y muestra la interfaz en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> {

            // Prepara la ventana de inicio.
            VentanaInicio ventana = new VentanaInicio();

            // Conecta las acciones de sus botones.
            new ControladorInicio(ventana);

            // Muestra la ventana.
            ventana.setVisible(true);
        });
    }
}