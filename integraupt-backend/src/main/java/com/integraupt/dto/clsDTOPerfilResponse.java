package com.integraupt.dto;

import com.integraupt.entidad.clsEntidadPerfil;

/**
 * DTO para respuesta de perfil de usuario (sin password)
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
public class clsDTOPerfilResponse {

    private String id;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    private String celular;
    private String genero;
    private String facultad;
    private String escuela;
    private String rol;
    private String tipoLogin;
    private String avatarUrl;
    private String estado;

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsDTOPerfilResponse() {}

    /**
     * Constructor que convierte una entidad Perfil a DTO
     * @param perfil entidad a convertir
     */
    public clsDTOPerfilResponse(clsEntidadPerfil perfil) {
        this.id = perfil.getId();
        this.codigo = perfil.getCodigo();
        this.nombres = perfil.getNombres();
        this.apellidos = perfil.getApellidos();
        this.email = perfil.getEmail();
        this.tipoDocumento = perfil.getTipoDocumento();
        this.numeroDocumento = perfil.getNumeroDocumento();
        this.celular = perfil.getCelular();
        this.genero = perfil.getGenero();
        this.facultad = perfil.getFacultad();
        this.escuela = perfil.getEscuela();
        this.rol = perfil.getRol();
        this.tipoLogin = perfil.getTipoLogin();
        this.avatarUrl = perfil.getAvatarUrl();
        this.estado = perfil.getEstado();
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el nombre completo del usuario
     * @return nombres + apellidos
     */
    public String getNombreCompleto() {
        return this.nombres + " " + this.apellidos;
    }
}
