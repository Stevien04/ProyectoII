package main.java.com.integraupt.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO para las peticiones de actualización del estado de un usuario.
 */
public class clsDTOActualizarEstadoRequest {

    @NotNull(message = "El estado es obligatorio")
    private Integer estado;

    public clsDTOActualizarEstadoRequest() {
        // Constructor por defecto
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}