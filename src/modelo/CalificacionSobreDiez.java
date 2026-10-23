package modelo;

// Calcula la calificación sobre diez puntos.
public class CalificacionSobreDiez
        implements EstrategiaCalificacion {

    @Override
    public String calcular(int aciertos, int totalPreguntas) {

        // Evita dividir entre cero.
        if (totalPreguntas == 0) {
            return "0.0 sobre 10";
        }

        // Convierte los aciertos a una escala de diez.
        double nota = aciertos * 10.0 / totalPreguntas;

        return String.format("%.1f sobre 10", nota);
    }
}