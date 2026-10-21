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
import modelo.Resultado;

// Lee y guarda los intentos del cuestionario en JSON.
public class ResultadosJson {

    // Ubicación del archivo donde se conservarán los resultados.
    private final Path archivo = Path.of("datos", "resultados.json");

    // Configura Gson para escribir el archivo con sangrías.
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    // Recupera los resultados guardados.
    public List<Resultado> cargarResultados() throws IOException {

        // Si todavía no existe el archivo, devuelve una lista vacía.
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        // Abre el archivo y cierra el lector automáticamente al terminar.
        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            // Convierte el JSON en un arreglo de objetos Resultado.
            Resultado[] resultados = gson.fromJson(
                lector,
                Resultado[].class
            );

            // Rechaza un archivo vacío o con contenido null.
            if (resultados == null) {
                throw new IOException(
                    "El archivo de resultados debe contener un arreglo JSON."
                );
            }

            // Comprueba que no haya resultados nulos en el arreglo.
            for (Resultado resultado : resultados) {
                if (resultado == null) {
                    throw new IOException(
                        "El archivo contiene un resultado inválido."
                    );
                }
            }

            // Convierte el arreglo en una lista que permite agregar elementos.
            return new ArrayList<>(Arrays.asList(resultados));

        } catch (JsonParseException error) {
            // Informa un problema de formato sin reemplazar el archivo.
            throw new IOException(
                "El archivo de resultados tiene un formato inválido.",
                error
            );
        }
    }

    // Agrega un intento y guarda la lista completa.
    public void guardarResultado(Resultado nuevoResultado)
            throws IOException {

        // Recupera los intentos anteriores para conservarlos.
        List<Resultado> resultados = cargarResultados();

        // Agrega el nuevo intento a la lista.
        resultados.add(nuevoResultado);

        // Crea la carpeta datos si todavía no existe.
        Files.createDirectories(archivo.getParent());

        // Abre el archivo y cierra el escritor automáticamente al terminar.
        try (Writer escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8)) {

            // Escribe todos los intentos, incluyendo el nuevo.
            gson.toJson(resultados, escritor);
        }
    }
}