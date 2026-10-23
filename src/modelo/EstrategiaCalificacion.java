package modelo;

// Contrato común para las formas de calcular la calificación.
public interface EstrategiaCalificacion {

    // Recibe los aciertos y el total, y devuelve la calificación.
    String calcular(int aciertos, int totalPreguntas);
}