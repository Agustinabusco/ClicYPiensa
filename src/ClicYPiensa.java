import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ClicYPiensa {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Clic y piensa");

            ventana.setSize(500, 300);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setLocationRelativeTo(null);

            JLabel titulo = new JLabel(
                "Bienvenidos a Clic y piensa",
                SwingConstants.CENTER
            );

            ventana.add(titulo);
            ventana.setVisible(true);
        });
    }
}