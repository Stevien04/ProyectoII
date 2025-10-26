package main.java.com.integraupt.dto;


/**
 * DTO con la información del catálogo de estados disponibles para los usuarios.
 */
public class clsDTOEstadoResponse {

    private Integer id;
    private String descripcion;

    public clsDTOEstadoResponse() {
        // Constructor por defecto
    }

    public clsDTOEstadoResponse(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}