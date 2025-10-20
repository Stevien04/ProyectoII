package com.integraupt.dto;

/**
 * DTO para respuesta de login exitoso
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
public class clsDTOLoginResponse {

    private boolean success;
    private String message;
    private clsDTOPerfilResponse perfil;
    private String token; // Para futuras implementaciones JWT

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsDTOLoginResponse() {}

    public clsDTOLoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public clsDTOLoginResponse(boolean success, String message, clsDTOPerfilResponse perfil) {
        this.success = success;
        this.message = message;
        this.perfil = perfil;
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public clsDTOPerfilResponse getPerfil() {
        return perfil;
    }

    public void setPerfil(clsDTOPerfilResponse perfil) {
        this.perfil = perfil;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
