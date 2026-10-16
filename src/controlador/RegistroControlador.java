package controlador;

import java.util.Arrays;
import modelo.Usuario;
import vista.RegistroVista;

public class RegistroControlador {

    private final RegistroVista vista;

    public RegistroControlador(RegistroVista vista) {
        this.vista = vista;

        vista.agregarAccionRegistrar(evento -> registrar());
    }

    private void registrar() {
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();
        String rol = vista.getRol();

        try {
            if (nombre.isEmpty()) {
                vista.mostrarMensaje(
                    "Escribí un nombre de usuario."
                );
                return;
            }

            if (contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Escribí una contraseña."
                );
                return;
            }

            if (!"Estudiante".equals(rol)
                    && !"Docente".equals(rol)) {
                vista.mostrarMensaje(
                    "Seleccioná un rol válido."
                );
                return;
            }

            Usuario usuario = new Usuario(nombre, rol);

            vista.mostrarMensaje(
                "Datos válidos para "
                + usuario.getNombreUsuario()
                + " con rol "
                + usuario.getRol()
                + ". La cuenta todavía no se guardó."
            );

        } finally {
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}