package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.RegistroVista;

// Coordina la creación de cuentas.
public class RegistroControlador {

    private final RegistroVista vista;
    private final UsuariosJson persistencia;

    public RegistroControlador(RegistroVista vista) {
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        // Conecta el botón Registrar.
        vista.agregarAccionRegistrar(evento -> registrar());
    }

    private void registrar() {
        // Obtiene los datos del formulario.
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();
        String rol = vista.getRol();

        try {
            // Comprueba los campos obligatorios.
            if (nombre.isEmpty()) {
                vista.mostrarMensaje("Escribí un nombre de usuario.");
                return;
            }

            if (contrasena.length == 0) {
                vista.mostrarMensaje("Escribí una contraseña.");
                return;
            }

            // Comprueba que el rol sea uno de los disponibles.
            if (!"Estudiante".equals(rol)
                    && !"Docente".equals(rol)) {

                vista.mostrarMensaje("Seleccioná un rol válido.");
                return;
            }

            // Crea la cuenta y guarda su contraseña protegida.
            Usuario usuario = new Usuario(nombre, rol, contrasena);
            persistencia.guardarUsuario(usuario);

            vista.mostrarMensaje("Cuenta registrada correctamente.");
            vista.dispose();

        } catch (IllegalArgumentException error) {
            // Informa, por ejemplo, que el nombre ya existe.
            vista.mostrarMensaje(error.getMessage());

        } catch (IOException | IllegalStateException error) {
            vista.mostrarMensaje(
                "No se pudo registrar la cuenta: "
                + error.getMessage()
            );

        } finally {
            // Limpia la contraseña incluso si ocurre un error.
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}