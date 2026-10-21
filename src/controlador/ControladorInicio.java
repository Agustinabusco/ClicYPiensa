package controlador;

import vista.RegistroVista;
import vista.VentanaInicio;
import vista.VentanaIngreso;

// Conecta los botones de inicio con los formularios correspondientes.
public class ControladorInicio {

    private final VentanaInicio vista;

    public ControladorInicio(VentanaInicio vista) {
        // Guarda la referencia a la ventana de inicio.
        this.vista = vista;

        // Al presionar Ingresar, crea el formulario, conecta su controlador
        // y lo muestra.
        this.vista.getBotonIngresar().addActionListener(evento -> {
            VentanaIngreso ingreso = new VentanaIngreso(this.vista);
            new IngresoControlador(ingreso);
            ingreso.setVisible(true);
        });

        // Al presionar Registrarse, crea el formulario, conecta su controlador
        // y lo muestra.
        this.vista.getBotonRegistrarse().addActionListener(evento -> {
            RegistroVista registro = new RegistroVista(this.vista);
            new RegistroControlador(registro);
            registro.setVisible(true);
        });
    }
}