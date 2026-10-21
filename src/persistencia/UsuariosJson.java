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

// Lee y guarda usuarios en un archivo JSON.
public class UsuariosJson {

    // Ruta relativa al directorio donde se ejecuta el programa.
    private final Path archivo = Path.of("datos", "usuarios.json");

    // Configura Gson para escribir JSON con sangrías y saltos de línea.
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    // Recupera los usuarios. Informa problemas de lectura mediante IOException.
    public List<Usuario> cargarUsuarios() throws IOException {

        // Si el archivo no existe, devuelve una lista vacía.
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        // Abre el archivo con UTF-8 y cierra el lector automáticamente.
        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            // Convierte el contenido JSON en un arreglo de objetos Usuario.
            Usuario[] usuarios = gson.fromJson(
                lector,
                Usuario[].class
            );

            // Rechaza un archivo vacío o un contenido JSON igual a null.
            if (usuarios == null) {
                throw new IOException(
                    "El archivo de usuarios debe contener un arreglo JSON."
                );
            }

            // Comprueba que cada usuario tenga nombre y rol.
            for (Usuario usuario : usuarios) {
                if (usuario == null
                        || usuario.getNombreUsuario() == null
                        || usuario.getRol() == null) {

                    throw new IOException(
                        "El archivo contiene un usuario incompleto."
                    );
                }
            }

            // Convierte el arreglo en una lista que permite agregar elementos.
            return new ArrayList<>(Arrays.asList(usuarios));

        } catch (JsonParseException error) {
            // Comunica un problema de formato conservando su causa original.
            throw new IOException(
                "El archivo de usuarios tiene un formato inválido.",
                error
            );
        }
    }

    // Agrega un usuario y guarda la lista actualizada.
    public void guardarUsuario(Usuario nuevoUsuario)
            throws IOException {

        // Recupera los usuarios existentes para conservarlos.
        List<Usuario> usuarios = cargarUsuarios();

        // Rechaza nombres repetidos, sin distinguir mayúsculas y minúsculas.
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(
                    nuevoUsuario.getNombreUsuario())) {

                throw new IllegalArgumentException(
                    "Ese nombre de usuario ya está registrado."
                );
            }
        }

        // Agrega el nuevo usuario a la lista en memoria.
        usuarios.add(nuevoUsuario);

        // Crea la carpeta de datos si todavía no existe.
        Files.createDirectories(archivo.getParent());

        // Abre el archivo para reemplazar su contenido y cierra el escritor
        // automáticamente al terminar.
        try (Writer escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8)) {

            // Convierte la lista completa a JSON y la escribe en el archivo.
            gson.toJson(usuarios, escritor);
        }
    }
}