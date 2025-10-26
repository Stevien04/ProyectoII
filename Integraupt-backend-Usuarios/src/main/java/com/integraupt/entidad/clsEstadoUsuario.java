package main.java.com.integraupt.entidad;

import java.util.Arrays;
import java.util.Optional;

/**
 * Catálogo de estados válidos para la entidad usuario.
 */
public enum clsEstadoUsuario {

    ACTIVO(1, "Activo"),
    INACTIVO(0, "Inactivo");

    private final int id;
    private final String descripcion;

    clsEstadoUsuario(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Optional<clsEstadoUsuario> fromId(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return Arrays.stream(values())
                .filter(valor -> valor.id == id)
                .findFirst();
    }
}