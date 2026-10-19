package controlador;

import vista.RegistroVista;
import vista.VentanaInicio;
import vista.VentanaIngreso;

public class ControladorInicio {

    private final VentanaInicio vista;

    public ControladorInicio(VentanaInicio vista) {
        this.vista = vista;

        // Acción del botón Ingresar.
        this.vista.getBotonIngresar().addActionListener(evento -> {
            VentanaIngreso ingreso = new VentanaIngreso(this.vista);
            new IngresoControlador(ingreso);
            ingreso.setVisible(true);
        });

        // Acción del botón Registrarse.
        this.vista.getBotonRegistrarse().addActionListener(evento -> {
            RegistroVista registro = new RegistroVista(this.vista);
            new RegistroControlador(registro);
            registro.setVisible(true);
        });
    }
}