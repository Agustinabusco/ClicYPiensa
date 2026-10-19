package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.VentanaIngreso;

public class IngresoControlador {

    private final VentanaIngreso vista;
    private final UsuariosJson persistencia;

    public IngresoControlador(VentanaIngreso vista) {
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        vista.agregarAccionIngresar(evento -> ingresar());
    }

    private void ingresar() {
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();

        try {
            if (nombre.isEmpty() || contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Completá el usuario y la contraseña."
                );
                return;
            }

            for (Usuario usuario : persistencia.cargarUsuarios()) {

                boolean coincideNombre =
                    usuario.getNombreUsuario()
                        .equalsIgnoreCase(nombre);

                if (coincideNombre
                        && usuario.verificarContrasena(contrasena)) {

                    vista.mostrarMensaje(
                        "Bienvenido/a, "
                        + usuario.getNombreUsuario()
                        + ". Rol: "
                        + usuario.getRol()
                    );

                    vista.dispose();
                    return;
                }
            }

            vista.mostrarMensaje(
                "Usuario o contraseña incorrectos."
            );

        } catch (IOException | IllegalStateException error) {
            vista.mostrarMensaje(
                "No se pudo comprobar el ingreso: "
                + error.getMessage()
            );

        } catch (IllegalArgumentException error) {
            vista.mostrarMensaje(
                "La cuenta contiene datos de contraseña inválidos."
            );

        } finally {
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}