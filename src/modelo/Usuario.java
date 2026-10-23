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

        // Guarda los datos de identificación.
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        // Genera una sal aleatoria propia de esta cuenta.
        byte[] salAleatoria = new byte[16];
        new SecureRandom().nextBytes(salAleatoria);

        // Representa la sal y el hash como texto para el JSON.
        this.sal = Base64.getEncoder()
            .encodeToString(salAleatoria);

        this.hashContrasena = Base64.getEncoder()
            .encodeToString(calcularHash(contrasena, salAleatoria));
    }

    // Permiten consultar el nombre y el rol.
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public boolean verificarContrasena(char[] contrasena) {

        // Rechaza cuentas antiguas que no tienen contraseña guardada.
        if (sal == null || hashContrasena == null) {
            return false;
        }

        // Recupera los bytes de los datos almacenados.
        byte[] salGuardada = Base64.getDecoder().decode(sal);
        byte[] hashGuardado = Base64.getDecoder()
            .decode(hashContrasena);

        // Repite el cálculo utilizando la misma sal.
        byte[] hashIngresado = calcularHash(
            contrasena, salGuardada
        );

        // Comprueba que ambos resultados coincidan.
        return MessageDigest.isEqual(
            hashGuardado, hashIngresado
        );
    }

    private static byte[] calcularHash(
            char[] contrasena,
            byte[] sal) {

        // Configura 600000 iteraciones y una salida de 256 bits.
        PBEKeySpec especificacion = new PBEKeySpec(
            contrasena, sal, 600000, 256
        );

        try {
            // Calcula la representación protegida de la contraseña.
            SecretKeyFactory fabrica =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return fabrica.generateSecret(especificacion)
                .getEncoded();

        } catch (GeneralSecurityException error) {
            throw new IllegalStateException(
                "No se pudo procesar la contraseña.",
                error
            );

        } finally {
            // Limpia la copia de la contraseña usada en el cálculo.
            especificacion.clearPassword();
        }
    }
}