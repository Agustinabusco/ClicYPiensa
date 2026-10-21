package modelo;

import java.util.ArrayList;
import java.util.List;

// Representa un intento del cuestionario y sus respuestas.
public class Resultado {

    // Datos del estudiante y del intento realizado.
    private final String nombreUsuario;
    private final List<Boolean> respuestas;
    private final int aciertos;
    private final int totalPreguntas;

    // Recibe los datos necesarios para crear un resultado.
    public Resultado(
            String nombreUsuario,
            List<Boolean> respuestas,
            int aciertos,
            int totalPreguntas) {

        // Guarda el nombre, los aciertos y el total de preguntas.
        this.nombreUsuario = nombreUsuario;
        this.aciertos = aciertos;
        this.totalPreguntas = totalPreguntas;

        // Conserva una copia de las respuestas de este intento.
        this.respuestas = new ArrayList<>(respuestas);
    }

    // Devuelve el nombre del usuario que respondió.
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Devuelve la cantidad de respuestas correctas.
    public int getAciertos() {
        return aciertos;
    }

    // Devuelve la cantidad total de preguntas del intento.
    public int getTotalPreguntas() {
        return totalPreguntas;
    }

    // Calcula el porcentaje de respuestas correctas.
    public double getPorcentaje() {

        // Evita dividir entre cero si no hay preguntas.
        if (totalPreguntas == 0) {
            return 0.0;
        }

        return aciertos * 100.0 / totalPreguntas;
    }
}