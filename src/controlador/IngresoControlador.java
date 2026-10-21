package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.VentanaIngreso;
import vista.VentanaCuestionario;

// Comprueba las credenciales y abre el cuestionario.
public class IngresoControlador {

    private final VentanaIngreso vista;
    private final UsuariosJson persistencia;

    public IngresoControlador(VentanaIngreso vista) {
        // Guarda la vista y prepara el acceso a los usuarios.
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        // Ejecuta ingresar() al presionar el botón.
        vista.agregarAccionIngresar(evento -> ingresar());
    }

    private void ingresar() {
        // Obtiene los datos escritos en el formulario.
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();

        try {
            // Comprueba que ambos campos estén completos.
            if (nombre.isEmpty() || contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Completá el usuario y la contraseña."
                );
                return;
            }

            // Recupera las cuentas y busca una coincidencia.
            for (Usuario usuario : persistencia.cargarUsuarios()) {

                // Compara los nombres sin distinguir mayúsculas y minúsculas.
                boolean coincideNombre =
                    usuario.getNombreUsuario()
                        .equalsIgnoreCase(nombre);

                // Comprueba la contraseña si el nombre coincide.
                if (coincideNombre
                        && usuario.verificarContrasena(contrasena)) {

                    // Cierra el formulario de ingreso.
                    vista.dispose();

                    // Crea la ventana del cuestionario.
                    VentanaCuestionario cuestionario =
                        new VentanaCuestionario();

                    // Conecta el controlador y le pasa el usuario del intento.
                    new CuestionarioControlador(
                        cuestionario,
                        usuario.getNombreUsuario()
                    );

                    // Muestra el cuestionario y termina la búsqueda.
                    cuestionario.setVisible(true);
                    return;
                }
            }

            // Informa que no se encontró una cuenta con esos datos.
            vista.mostrarMensaje(
                "Usuario o contraseña incorrectos."
            );

        } catch (IOException | IllegalStateException error) {
            // Informa problemas de lectura o procesamiento de la contraseña.
            vista.mostrarMensaje(
                "No se pudo comprobar el ingreso: "
                + error.getMessage()
            );

        } catch (IllegalArgumentException error) {
            // Informa si los datos de la contraseña tienen un formato inválido.
            vista.mostrarMensaje(
                "La cuenta contiene datos de contraseña inválidos."
            );

        } finally {
            // Limpia la contraseña, incluso si hubo un error o un return.
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}