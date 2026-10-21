package modelo;

// Representa una pregunta de verdadero o falso.
public class Pregunta {

    private final String enunciado;
    private final boolean respuestaCorrecta;

    // Recibe el enunciado y la respuesta correcta.
    public Pregunta(String enunciado, boolean respuestaCorrecta) {
        this.enunciado = enunciado;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    // Permite consultar el enunciado.
    public String getEnunciado() {
        return enunciado;
    }

    // Permite consultar la respuesta correcta.
    public boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    // Compara la respuesta elegida con la correcta.
    public boolean esCorrecta(boolean respuestaElegida) {
        return respuestaElegida == respuestaCorrecta;
    }
}