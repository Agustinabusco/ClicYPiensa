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

// Lee y guarda los intentos del cuestionario.
public class ResultadosJson {

    private final Path archivo = Path.of("datos", "resultados.json");

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public List<Resultado> cargarResultados() throws IOException {

        // Sin archivo, todavía no hay intentos guardados.
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        // Recupera los intentos del JSON.
        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            Resultado[] resultados = gson.fromJson(
                lector, Resultado[].class
            );

            if (resultados == null) {
                throw new IOException(
                    "El archivo debe contener una lista de resultados."
                );
            }

            // Rechaza elementos nulos.
            for (Resultado resultado : resultados) {
                if (resultado == null) {
                    throw new IOException(
                        "El archivo contiene un resultado inválido."
                    );
                }
            }

            return new ArrayList<>(Arrays.asList(resultados));

        } catch (JsonParseException error) {
            throw new IOException(
                "El archivo de resultados tiene un formato inválido.",
                error
            );
        }
    }

    public void guardarResultado(Resultado nuevoResultado)
            throws IOException {

        // Agrega el intento a los anteriores.
        List<Resultado> resultados = cargarResultados();
        resultados.add(nuevoResultado);

        Files.createDirectories(archivo.getParent());

        // Escribe la lista completa y cierra el archivo automáticamente.
        try (Writer escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8)) {

            gson.toJson(resultados, escritor);
        }
    }
}