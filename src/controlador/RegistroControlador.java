package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.RegistroVista;

// Coordina el registro entre la vista, el modelo y la persistencia.
public class RegistroControlador {

    private final RegistroVista vista;
    private final UsuariosJson persistencia;

    public RegistroControlador(RegistroVista vista) {
        // Guarda la vista recibida y prepara el acceso al archivo JSON.
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        // Ejecuta registrar() cuando se presiona el botón Registrar.
        vista.agregarAccionRegistrar(evento -> registrar());
    }

    private void registrar() {
        // Obtiene los datos ingresados en el formulario.
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();
        String rol = vista.getRol();

        try {
            // Comprueba que el nombre no esté vacío.
            // return detiene el registro si falta el dato.
            if (nombre.isEmpty()) {
                vista.mostrarMensaje(
                    "Escribí un nombre de usuario."
                );
                return;
            }

            // Comprueba que se haya escrito una contraseña.
            if (contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Escribí una contraseña."
                );
                return;
            }

            // Rechaza cualquier rol que no sea Estudiante ni Docente.
            if (!"Estudiante".equals(rol)
                    && !"Docente".equals(rol)) {
                vista.mostrarMensaje(
                    "Seleccioná un rol válido."
                );
                return;
            }

            // Crea el usuario; su constructor genera la sal y el hash.
            Usuario usuario = new Usuario(
                nombre,
                rol,
                contrasena
            );

            // Guarda la cuenta en JSON, comprobando que no esté repetida.
            persistencia.guardarUsuario(usuario);

            // Confirma el registro y cierra el formulario.
            vista.mostrarMensaje(
                "Cuenta registrada correctamente."
            );

            vista.dispose();

        } catch (IllegalArgumentException error) {
            // Muestra el motivo del rechazo, por ejemplo, un nombre repetido.
            vista.mostrarMensaje(error.getMessage());

        } catch (IOException | IllegalStateException error) {
            // Informa problemas al guardar o al procesar la contraseña.
            vista.mostrarMensaje(
                "No se pudo registrar la cuenta: "
                + error.getMessage()
            );

        } finally {
            // Limpia la contraseña del arreglo y del campo,
            // incluso si hubo un error o se ejecutó un return.
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }
}