package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.RegistroVista;

public class RegistroControlador {

    private final RegistroVista vista;
    private final UsuariosJson persistencia;

    public RegistroControlador(RegistroVista vista) {
        this.vista = vista;
        this.persistencia = new UsuariosJson();

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

            Usuario usuario = new Usuario(
                nombre,
                rol,
                contrasena
            );

            persistencia.guardarUsuario(usuario);

            vista.mostrarMensaje(
                "Cuenta registrada correctamente."
            );

            vista.dispose();

        } catch (IllegalArgumentException error) {
            vista.mostrarMensaje(error.getMessage());

        } catch (IOException | IllegalStateException error) {
            vista.mostrarMensaje(
                "No se pudo registrar la cuenta: "
                + error.getMessage()
            );

        } finally {
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}