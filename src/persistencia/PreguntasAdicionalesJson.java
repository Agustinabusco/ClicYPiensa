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
import modelo.Pregunta;

// Conserva las preguntas agregadas desde la pantalla docente.
public class PreguntasAdicionalesJson {

    private final Path archivo =
        Path.of("datos", "preguntas_adicionales.json");

    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public List<Pregunta> cargarPreguntas() throws IOException {

        // Devuelve una lista vacía si aún no hay preguntas adicionales.
        if (!Files.exists(archivo)) {
            return new ArrayList<>();
        }

        // Lee las preguntas y cierra el lector automáticamente.
        try (Reader lector = Files.newBufferedReader(
                archivo, StandardCharsets.UTF_8)) {

            Pregunta[] preguntas = gson.fromJson(
                lector, Pregunta[].class
            );

            if (preguntas == null) {
                throw new IOException(
                    "El archivo de preguntas está vacío."
                );
            }

            // Comprueba que cada pregunta tenga un enunciado.
            for (Pregunta pregunta : preguntas) {
                if (pregunta == null
                        || pregunta.getEnunciado() == null
                        || pregunta.getEnunciado().trim().isEmpty()) {

                    throw new IOException(
                        "Hay una pregunta sin enunciado."
                    );
                }
            }

            return new ArrayList<>(Arrays.asList(preguntas));

        } catch (JsonParseException error) {
            throw new IOException(
                "El archivo de preguntas tiene un formato inválido.",
                error
            );
        }
    }

    public void guardarPregunta(Pregunta nuevaPregunta)
            throws IOException {

        // Conserva las anteriores y agrega la nueva.
        List<Pregunta> preguntas = cargarPreguntas();
        preguntas.add(nuevaPregunta);

        Files.createDirectories(archivo.getParent());

        // Guarda todas las preguntas adicionales.
        try (Writer escritor = Files.newBufferedWriter(
                archivo, StandardCharsets.UTF_8)) {

            gson.toJson(preguntas, escritor);
        }
    }
}