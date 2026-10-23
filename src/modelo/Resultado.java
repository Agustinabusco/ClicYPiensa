package modelo;

import java.util.ArrayList;
import java.util.List;

// Representa los datos de un intento que se guardará en JSON.
public class Resultado {

    private final String nombreUsuario;
    private final List<Boolean> respuestas;
    private final int aciertos;
    private final int totalPreguntas;

    public Resultado(
            String nombreUsuario,
            List<Boolean> respuestas,
            int aciertos,
            int totalPreguntas) {

        // Guarda el usuario y la cantidad de aciertos y preguntas.
        this.nombreUsuario = nombreUsuario;
        this.aciertos = aciertos;
        this.totalPreguntas = totalPreguntas;

        // Conserva una copia de las respuestas del intento.
        this.respuestas = new ArrayList<>(respuestas);
    }
}