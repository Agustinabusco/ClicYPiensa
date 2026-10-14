package controlador;

import vista.VentanaInicio;
import vista.VentanaIngreso;

public class ControladorInicio {

    private VentanaInicio vista;

    public ControladorInicio(VentanaInicio vista) {
        this.vista = vista;

        // Acción del botón Ingresar
        this.vista.getBotonIngresar().addActionListener(evento -> {
            VentanaIngreso ingreso = new VentanaIngreso(this.vista);
            ingreso.setVisible(true);
        });

        // Acción del botón Registrarse
        this.vista.getBotonRegistrarse().addActionListener(evento -> {
            // Aquí agregaremos la apertura del formulario de registro.
        });
    }
}
