package modelo;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Usuario {

    private final String nombreUsuario;
    private final String rol;
    private final String sal;
    private final String hashContrasena;

    public Usuario(
            String nombreUsuario,
            String rol,
            char[] contrasena) {

        this.nombreUsuario = nombreUsuario;
        this.rol = rol;

        byte[] salAleatoria = new byte[16];
        new SecureRandom().nextBytes(salAleatoria);

        this.sal = Base64.getEncoder()
            .encodeToString(salAleatoria);

        this.hashContrasena = Base64.getEncoder()
            .encodeToString(calcularHash(contrasena, salAleatoria));
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public boolean verificarContrasena(char[] contrasena) {
        if (sal == null || hashContrasena == null) {
            return false;
        }

        byte[] salGuardada = Base64.getDecoder().decode(sal);
        byte[] hashGuardado = Base64.getDecoder()
            .decode(hashContrasena);

        byte[] hashIngresado = calcularHash(
            contrasena,
            salGuardada
        );

        return MessageDigest.isEqual(
            hashGuardado,
            hashIngresado
        );
    }

    private static byte[] calcularHash(
            char[] contrasena,
            byte[] sal) {

        PBEKeySpec especificacion = new PBEKeySpec(
            contrasena,
            sal,
            600000,
            256
        );

        try {
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
            especificacion.clearPassword();
        }
    }
}