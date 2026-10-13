import javax.swing.SwingUtilities;
import vista.VentanaInicio;

public class ClicYPiensa {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventana = new VentanaInicio();
            ventana.setVisible(true);
        });
    }
}