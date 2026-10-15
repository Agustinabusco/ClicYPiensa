package modelo;

public class Usuario {

    private final String nombreUsuario;
    private final String rol;

    public Usuario(String nombreUsuario, String rol) {
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }
}