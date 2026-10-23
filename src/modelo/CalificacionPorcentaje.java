package modelo;

// Calcula la calificación como porcentaje.
public class CalificacionPorcentaje
        implements EstrategiaCalificacion {

    @Override
    public String calcular(int aciertos, int totalPreguntas) {

        // Evita dividir entre cero.
        if (totalPreguntas == 0) {
            return "0.0%";
        }

        // Convierte los aciertos a una escala de cien.
        double porcentaje = aciertos * 100.0 / totalPreguntas;

        return String.format("%.1f%%", porcentaje);
    }
}