import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout; 

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

            JButton botonIngresar = new JButton("Ingresar");
            JButton botonRegistrarse = new JButton("Registrarse");

            JPanel panelBotones = new JPanel();
            panelBotones.add(botonIngresar);
            panelBotones.add(botonRegistrarse);
          
            ventana.add(titulo, BorderLayout.CENTER);
            ventana.add(panelBotones, BorderLayout.SOUTH);
            ventana.setVisible(true);
        });
    }
}