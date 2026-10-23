package controlador;

import java.io.IOException;
import modelo.Pregunta;
import persistencia.PreguntasAdicionalesJson;
import vista.VentanaDocente;

// Coordina el agregado de preguntas.
public class DocenteControlador {

    private final VentanaDocente vista;
    private final PreguntasAdicionalesJson persistencia;

    public DocenteControlador(VentanaDocente vista) {
        this.vista = vista;
        this.persistencia = new PreguntasAdicionalesJson();

        // Conecta el botón Guardar.
        vista.agregarAccionGuardar(evento -> guardarPregunta());
    }

    private void guardarPregunta() {
        // Comprueba que el enunciado tenga texto.
        String enunciado = vista.getEnunciado();

        if (enunciado.isEmpty()) {
            vista.mostrarMensaje("Escribí un enunciado.");
            return;
        }

        // Crea una pregunta con la respuesta seleccionada.
        Pregunta pregunta = new Pregunta(
            enunciado,
            vista.getRespuestaCorrecta()
        );

        try {
            // Guarda la pregunta sin eliminar las anteriores.
            persistencia.guardarPregunta(pregunta);

            vista.mostrarMensaje("Pregunta guardada correctamente.");
            vista.limpiarCampos();

        } catch (IOException error) {
            // Si falla, conserva el enunciado para no perderlo.
            vista.mostrarMensaje(
                "No se pudo guardar la pregunta: "
                + error.getMessage()
            );
        }
    }
}