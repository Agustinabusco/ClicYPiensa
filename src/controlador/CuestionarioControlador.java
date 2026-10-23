package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.CalificacionPorcentaje;
import modelo.CalificacionSobreDiez;
import modelo.EstrategiaCalificacion;
import modelo.Pregunta;
import modelo.Preguntas;
import modelo.Resultado;
import persistencia.PreguntasAdicionalesJson;
import persistencia.ResultadosJson;
import vista.VentanaCuestionario;

// Coordina las preguntas, la calificación y el guardado del intento.
public class CuestionarioControlador {

    private final VentanaCuestionario vista;
    private final String nombreUsuario;
    private final List<Pregunta> preguntas;
    private final List<Boolean> respuestas;
    private final ResultadosJson persistencia;
    private final EstrategiaCalificacion estrategia;

    private int indiceActual;
    private int aciertos;

    public CuestionarioControlador(
            VentanaCuestionario vista,
            String nombreUsuario) {

        this.vista = vista;
        this.nombreUsuario = nombreUsuario;

        // Carga las cuatro preguntas originales, sin modificarlas.
        Preguntas banco = new Preguntas();
        this.preguntas = banco.cargarPreguntas();

        try {
            // Suma las preguntas guardadas desde la pantalla docente.
            PreguntasAdicionalesJson adicionales =
                new PreguntasAdicionalesJson();

            preguntas.addAll(adicionales.cargarPreguntas());

        } catch (IOException error) {
            // Mantiene disponibles las cuatro preguntas originales.
            vista.mostrarMensaje(
                "Se usarán solamente las preguntas iniciales."
                + "\nNo se pudieron cargar las adicionales: "
                + error.getMessage()
            );
        }

        // Prepara la lista de respuestas y el guardado.
        this.respuestas = new ArrayList<>();
        this.persistencia = new ResultadosJson();

        // Selecciona la estrategia según la elección del usuario.
        if (vista.elegirNotaSobreDiez()) {
            this.estrategia = new CalificacionSobreDiez();
        } else {
            this.estrategia = new CalificacionPorcentaje();
        }

        // Inicia el recorrido y los aciertos en cero.
        this.indiceActual = 0;
        this.aciertos = 0;

        // Conecta las respuestas de los botones.
        vista.agregarAccionVerdadero(evento -> responder(true));
        vista.agregarAccionFalso(evento -> responder(false));

        // Siempre están disponibles las cuatro preguntas del modelo.
        mostrarPreguntaActual();
    }

    // Envía a la vista el enunciado y el progreso.
    private void mostrarPreguntaActual() {
        Pregunta pregunta = preguntas.get(indiceActual);

        vista.mostrarPregunta(
            pregunta.getEnunciado(),
            indiceActual + 1,
            preguntas.size()
        );
    }

    private void responder(boolean respuestaElegida) {

        // Evita procesar respuestas después de terminar.
        if (indiceActual >= preguntas.size()) {
            return;
        }

        // Conserva la respuesta en el orden de las preguntas.
        respuestas.add(respuestaElegida);

        // Cuenta el acierto si la respuesta coincide.
        Pregunta pregunta = preguntas.get(indiceActual);

        if (pregunta.esCorrecta(respuestaElegida)) {
            aciertos++;
        }

        // Avanza a la siguiente pregunta o finaliza.
        indiceActual++;

        if (indiceActual < preguntas.size()) {
            mostrarPreguntaActual();
        } else {
            finalizarCuestionario();
        }
    }

    private void finalizarCuestionario() {

        // Reúne los datos del intento.
        Resultado resultado = new Resultado(
            nombreUsuario,
            respuestas,
            aciertos,
            preguntas.size()
        );

        String mensajeGuardado;

        try {
            // Guarda el intento conservando los anteriores.
            persistencia.guardarResultado(resultado);
            mensajeGuardado = "\nResultado guardado correctamente.";

        } catch (IOException error) {
            mensajeGuardado =
                "\nNo se pudo guardar el resultado: "
                + error.getMessage();
        }

        // Ejecuta el cálculo de la estrategia elegida.
        String calificacion = estrategia.calcular(
            aciertos,
            preguntas.size()
        );

        // Muestra la calificación y el estado del guardado.
        vista.mostrarMensaje(
            "Cuestionario finalizado."
            + "\nUsuario: " + nombreUsuario
            + "\nAciertos: " + aciertos + " de " + preguntas.size()
            + "\nCalificación: " + calificacion
            + mensajeGuardado
        );

        vista.dispose();
    }
}