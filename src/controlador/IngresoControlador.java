package controlador;

import java.io.IOException;
import java.util.Arrays;
import modelo.Usuario;
import persistencia.UsuariosJson;
import vista.VentanaCuestionario;
import vista.VentanaDocente;
import vista.VentanaIngreso;

// Comprueba las credenciales y abre la pantalla según el rol.
public class IngresoControlador {

    private final VentanaIngreso vista;
    private final UsuariosJson persistencia;

    public IngresoControlador(VentanaIngreso vista) {
        this.vista = vista;
        this.persistencia = new UsuariosJson();

        // Conecta el botón Ingresar.
        vista.agregarAccionIngresar(evento -> ingresar());
    }

    private void ingresar() {
        // Recupera las credenciales escritas.
        String nombre = vista.getNombreUsuario();
        char[] contrasena = vista.getContrasena();

        try {
            if (nombre.isEmpty() || contrasena.length == 0) {
                vista.mostrarMensaje(
                    "Completá el usuario y la contraseña."
                );
                return;
            }

            // Busca una cuenta con nombre y contraseña coincidentes.
            for (Usuario usuario : persistencia.cargarUsuarios()) {
                boolean coincideNombre =
                    usuario.getNombreUsuario()
                        .equalsIgnoreCase(nombre);

                if (coincideNombre
                        && usuario.verificarContrasena(contrasena)) {

                    // Cierra el diálogo antes de abrir otra pantalla.
                    vista.dispose();
                    abrirPantalla(usuario);
                    return;
                }
            }

            vista.mostrarMensaje(
                "Usuario o contraseña incorrectos."
            );

        } catch (IOException | IllegalStateException error) {
            vista.mostrarMensaje(
                "No se pudo completar el ingreso: "
                + error.getMessage()
            );

        } catch (IllegalArgumentException error) {
            vista.mostrarMensaje(
                "La cuenta contiene datos de contraseña inválidos."
            );

        } finally {
            // Limpia la contraseña después de comprobarla.
            Arrays.fill(contrasena, '\0');
            vista.limpiarContrasena();
        }
    }

    private void abrirPantalla(Usuario usuario) {

        // El docente puede agregar preguntas.
        if ("Docente".equals(usuario.getRol())) {
            VentanaDocente docente = new VentanaDocente();
            new DocenteControlador(docente);
            docente.setVisible(true);

        } else {
            // El estudiante responde el cuestionario.
            VentanaCuestionario cuestionario =
                new VentanaCuestionario();

            new CuestionarioControlador(
                cuestionario,
                usuario.getNombreUsuario()
            );

            cuestionario.setVisible(true);
        }
    }
}