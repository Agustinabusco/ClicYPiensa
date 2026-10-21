package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Pregunta;
import modelo.Preguntas;
import modelo.Resultado;
import persistencia.ResultadosJson;
import vista.VentanaCuestionario;

// Coordina las preguntas y guarda el intento al finalizar.
public class CuestionarioControlador {

    private final VentanaCuestionario vista;
    private final List<Pregunta> preguntas;
    private final String nombreUsuario;
    private final List<Boolean> respuestas;
    private final ResultadosJson persistencia;

    private int indiceActual;
    private int aciertos;

    public CuestionarioControlador(
            VentanaCuestionario vista,
            String nombreUsuario) {

        // Guarda la ventana y el nombre del usuario que responde.
        this.vista = vista;
        this.nombreUsuario = nombreUsuario;

        // Recupera las preguntas preparadas en el modelo.
        Preguntas banco = new Preguntas();
        this.preguntas = banco.cargarPreguntas();

        // Prepara la lista de respuestas y la persistencia.
        this.respuestas = new ArrayList<>();
        this.persistencia = new ResultadosJson();

        // Inicia los contadores en cero.
        this.indiceActual = 0;
        this.aciertos = 0;

        // Cada botón envía la respuesta elegida.
        vista.agregarAccionVerdadero(evento -> responder(true));
        vista.agregarAccionFalso(evento -> responder(false));

        // Comprueba que haya preguntas antes de comenzar.
        if (preguntas.isEmpty()) {
            vista.mostrarMensaje("No hay preguntas disponibles.");
        } else {
            mostrarPreguntaActual();
        }
    }

    // Muestra el enunciado y el número de la pregunta actual.
    private void mostrarPreguntaActual() {
        Pregunta pregunta = preguntas.get(indiceActual);

        vista.mostrarPregunta(
            pregunta.getEnunciado(),
            indiceActual + 1,
            preguntas.size()
        );
    }

    private void responder(boolean respuestaElegida) {
        // Evita acceder a una pregunta inexistente.
        if (indiceActual >= preguntas.size()) {
            return;
        }

        // Conserva la respuesta elegida en el orden de las preguntas.
        respuestas.add(respuestaElegida);

        Pregunta pregunta = preguntas.get(indiceActual);

        // Suma un acierto cuando el modelo confirma la respuesta.
        if (pregunta.esCorrecta(respuestaElegida)) {
            aciertos++;
        }

        // Avanza de pregunta, independientemente de si acertó.
        indiceActual++;

        if (indiceActual < preguntas.size()) {
            mostrarPreguntaActual();
        } else {
            finalizarCuestionario();
        }
    }

    private void finalizarCuestionario() {
        // Reúne los datos del intento en un objeto Resultado.
        Resultado resultado = new Resultado(
            nombreUsuario,
            respuestas,
            aciertos,
            preguntas.size()
        );

        String mensajeGuardado;

        try {
            // Guarda el intento sin eliminar los anteriores.
            persistencia.guardarResultado(resultado);
            mensajeGuardado = "\nResultado guardado correctamente.";

        } catch (IOException error) {
            // Informa el fallo de guardado y conserva la calificación en pantalla.
            mensajeGuardado =
                "\nNo se pudo guardar el resultado: "
                + error.getMessage();
        }

        // Muestra los aciertos, el porcentaje y el estado del guardado.
        vista.mostrarMensaje(
            "Cuestionario finalizado."
            + "\nUsuario: " + resultado.getNombreUsuario()
            + "\nAciertos: " + resultado.getAciertos()
            + " de " + resultado.getTotalPreguntas()
            + "\nPorcentaje: " + resultado.getPorcentaje() + "%"
            + mensajeGuardado
        );

        // Cierra la ventana del cuestionario.
        vista.dispose();
    }
}