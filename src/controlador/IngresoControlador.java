package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.VentanaIngreso;

// Coordina la comprobación de las credenciales ingresadas.
public class IngresoControlador {

    private final VentanaIngreso vista;
    private final UsuariosJson persistencia;

    public IngresoControlador(VentanaIngreso vista) {
        // Guarda la vista y prepara el acceso al archivo JSON.
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        // Ejecuta ingresar() al presionar el botón Ingresar.
        vista.agregarAccionIngresar(evento -> ingresar());
    }

    private void ingresar() {
        // Obtiene el nombre y la contraseña del formulario.
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();

        try {
            // Detiene el ingreso si alguno de los campos está vacío.
            if (nombre.isEmpty() || contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Completá el usuario y la contraseña."
                );
                return;
            }

            // Recupera las cuentas del JSON y las recorre una por una.
            for (Usuario usuario : persistencia.cargarUsuarios()) {

                // Compara los nombres sin distinguir mayúsculas y minúsculas.
                boolean coincideNombre =
                    usuario.getNombreUsuario()
                        .equalsIgnoreCase(nombre);

                // Si el nombre coincide, comprueba también la contraseña.
                if (coincideNombre
                        && usuario.verificarContrasena(contrasena)) {

                    // Muestra la bienvenida con el nombre y el rol.
                    vista.mostrarMensaje(
                        "Bienvenido/a, "
                        + usuario.getNombreUsuario()
                        + ". Rol: "
                        + usuario.getRol()
                    );

                    // Cierra el formulario y termina el método.
                    vista.dispose();
                    return;
                }
            }

            // Se muestra si ninguna cuenta coincide con las credenciales.
            vista.mostrarMensaje(
                "Usuario o contraseña incorrectos."
            );

        } catch (IOException | IllegalStateException error) {
            // Informa problemas de lectura o de procesamiento de la contraseña.
            vista.mostrarMensaje(
                "No se pudo comprobar el ingreso: "
                + error.getMessage()
            );

        } catch (IllegalArgumentException error) {
            // Informa si la sal o el hash guardados tienen un formato inválido.
            vista.mostrarMensaje(
                "La cuenta contiene datos de contraseña inválidos."
            );

        } finally {
            // Limpia la contraseña del arreglo y del campo,
            // incluso si hubo un error o se ejecutó un return.
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}