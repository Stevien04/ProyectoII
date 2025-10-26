package main.java.com.integraupt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO utilizado para la creación y actualización de usuarios.
 */
public class clsDTOUsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellidos;

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private String email;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotNull(message = "El rol es obligatorio")
    private Integer rolId;

    @NotNull(message = "La facultad es obligatoria")
    private Integer facultadId;

    @NotNull(message = "La escuela es obligatoria")
    private Integer escuelaId;

    @Size(max = 11, message = "El celular no debe exceder 11 caracteres")
    private String celular;

    private Boolean genero;

    @Size(min = 4, max = 255, message = "La contraseña debe tener entre 4 y 255 caracteres")
    private String password;

    private Integer estado;

    private Boolean sesion;

    public clsDTOUsuarioRequest() {
        // Constructor por defecto requerido por Jackson
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Boolean getSesion() {
        return sesion;
    }

    public void setSesion(Boolean sesion) {
        this.sesion = sesion;
    }
}