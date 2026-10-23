package controlador;

import vista.RegistroVista;
import vista.VentanaInicio;
import vista.VentanaIngreso;

// Conecta los botones de la ventana inicial.
public class ControladorInicio {

    public ControladorInicio(VentanaInicio vista) {

        // Crea y muestra el formulario de ingreso.
        vista.getBotonIngresar().addActionListener(evento -> {
            VentanaIngreso ingreso = new VentanaIngreso(vista);
            new IngresoControlador(ingreso);
            ingreso.setVisible(true);
        });

        // Crea y muestra el formulario de registro.
        vista.getBotonRegistrarse().addActionListener(evento -> {
            RegistroVista registro = new RegistroVista(vista);
            new RegistroControlador(registro);
            registro.setVisible(true);
        });
    }
}