package modelo;

import java.util.ArrayList;
import java.util.List;

// Prepara las preguntas que utilizará el cuestionario.
public class Preguntas {

    public List<Pregunta> cargarPreguntas() {

        // Crea una lista vacía para guardar objetos Pregunta.
        List<Pregunta> preguntas = new ArrayList<>();

        // Agrega cada enunciado junto con su respuesta correcta.
        preguntas.add(new Pregunta(
            "Python es un lenguaje de programación.",
            true
        ));

        preguntas.add(new Pregunta(
            "El software es la parte física y tangible de la computadora",
            false
        ));

        preguntas.add(new Pregunta(
            "El teclado es un dispositivo de entrada de datos.",
            true
        ));

        preguntas.add(new Pregunta(
            "La memoria RAM conserva toda su información al apagar la computadora.",
            false
        ));

        // Devuelve la lista con las cuatro preguntas.
        return preguntas;
    }
}