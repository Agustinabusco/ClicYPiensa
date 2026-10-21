package modelo;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// Representa una cuenta y permite comprobar su contraseña.
public class Usuario {

    private final String nombreUsuario;
    private final String rol;
    private final String sal;
    private final String hashContrasena;

    public Usuario(
            String nombreUsuario,
            String rol,
            char[] contrasena) {

        // Guarda el nombre y el rol recibidos.
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        // Genera 16 bytes aleatorios como sal para esta cuenta.
        byte[] salAleatoria = new byte[16];
        new SecureRandom().nextBytes(salAleatoria);

        // Representa la sal como texto para guardarla en JSON.
        this.sal = Base64.getEncoder()
            .encodeToString(salAleatoria);

        // Calcula el hash y lo representa como texto.
        this.hashContrasena = Base64.getEncoder()
            .encodeToString(calcularHash(contrasena, salAleatoria));
    }

    // Permiten consultar el nombre y el rol del usuario.
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    // Comprueba si la contraseña ingresada coincide con la registrada.
    public boolean verificarContrasena(char[] contrasena) {

        // Rechaza cuentas que no tienen sal o hash guardados.
        if (sal == null || hashContrasena == null) {
            return false;
        }

        // Recupera los bytes originales de los textos en Base64.
        byte[] salGuardada = Base64.getDecoder().decode(sal);
        byte[] hashGuardado = Base64.getDecoder()
            .decode(hashContrasena);

        // Calcula el hash de la contraseña ingresada usando la misma sal.
        byte[] hashIngresado = calcularHash(
            contrasena,
            salGuardada
        );

        // Devuelve true si ambos resultados coinciden; false si no.
        return MessageDigest.isEqual(
            hashGuardado,
            hashIngresado
        );
    }

    // Método auxiliar que calcula el hash al registrar y al ingresar.
    private static byte[] calcularHash(
            char[] contrasena,
            byte[] sal) {

        // Prepara el cálculo con 600000 iteraciones y una salida de 256 bits.
        PBEKeySpec especificacion = new PBEKeySpec(
            contrasena,
            sal,
            600000,
            256
        );

        try {
            // Selecciona el algoritmo para procesar la contraseña.
            SecretKeyFactory fabrica =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            // Realiza el cálculo y devuelve el resultado como bytes.
            return fabrica.generateSecret(especificacion)
                .getEncoded();

        } catch (GeneralSecurityException error) {
            // Comunica el problema conservando su causa original.
            throw new IllegalStateException(
                "No se pudo procesar la contraseña.",
                error
            );

        } finally {
            // Borra la copia de la contraseña guardada en la especificación.
            especificacion.clearPassword();
        }
    }
}