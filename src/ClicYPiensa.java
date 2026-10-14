import javax.swing.SwingUtilities;
import controlador.ControladorInicio;
import vista.VentanaInicio;

public class ClicYPiensa {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventana = new VentanaInicio();
            new ControladorInicio(ventana);
            ventana.setVisible(true);
        });
    }
}