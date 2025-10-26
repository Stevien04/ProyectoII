package main.java.com.integraupt.dto;

/**
 * DTO que representa la información expuesta de un usuario.
 */
public class clsDTOUsuarioResponse {

    private Integer id;
    private String nombres;
    private String apellidos;
    private String codigo;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    private Integer rolId;
    private Integer facultadId;
    private Integer escuelaId;
    private String celular;
    private Boolean genero;
    private Integer estado;
    private String estadoDescripcion;
    private Boolean sesion;

    public clsDTOUsuarioResponse() {
        // Constructor por defecto
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getRolId() {
        return rolId;
    }

    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }

    public Integer getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(Integer facultadId) {
        this.facultadId = facultadId;
    }

    public Integer getEscuelaId() {
        return escuelaId;
    }

    public void setEscuelaId(Integer escuelaId) {
        this.escuelaId = escuelaId;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getEstadoDescripcion() {
        return estadoDescripcion;
    }

    public void setEstadoDescripcion(String estadoDescripcion) {
        this.estadoDescripcion = estadoDescripcion;
    }

    public Boolean getSesion() {
        return sesion;
    }

    public void setSesion(Boolean sesion) {
        this.sesion = sesion;
    }
}