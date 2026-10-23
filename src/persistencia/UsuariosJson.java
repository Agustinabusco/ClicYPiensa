package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import modelo.Usuario;

// Lee y guarda las cuentas.
public class UsuariosJson {

    private final Path archivo = Path.of("datos", "usuarios.json");

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public List<Usuario> cargarUsuarios() throws IOException {

        // Sin archivo, todavía no hay cuentas registradas.
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        // Lee el JSON y cierra el lector automáticamente.
        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            Usuario[] usuarios = gson.fromJson(
                lector, Usuario[].class
            );

            if (usuarios == null) {
                throw new IOException(
                    "El archivo debe contener una lista de usuarios."
                );
            }

            // Evita cargar cuentas incompletas.
            for (Usuario usuario : usuarios) {
                if (usuario == null
                        || usuario.getNombreUsuario() == null
                        || usuario.getRol() == null) {

                    throw new IOException(
                        "El archivo contiene un usuario incompleto."
                    );
                }
            }

            return new ArrayList<>(Arrays.asList(usuarios));

        } catch (JsonParseException error) {
            throw new IOException(
                "El archivo de usuarios tiene un formato inválido.",
                error
            );
        }
    }

    public void guardarUsuario(Usuario nuevoUsuario)
            throws IOException {

        // Recupera las cuentas anteriores.
        List<Usuario> usuarios = cargarUsuarios();

        // Rechaza nombres repetidos.
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(
                    nuevoUsuario.getNombreUsuario())) {

                throw new IllegalArgumentException(
                    "Ese nombre de usuario ya está registrado."
                );
            }
        }

        usuarios.add(nuevoUsuario);
        Files.createDirectories(archivo.getParent());

        // Guarda la lista completa de cuentas.
        try (Writer escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8)) {

            gson.toJson(usuarios, escritor);
        }
    }
}