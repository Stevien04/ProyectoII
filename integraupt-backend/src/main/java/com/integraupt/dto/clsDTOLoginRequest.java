package com.integraupt.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitud de login
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
public class clsDTOLoginRequest {

    @NotBlank(message = "El código o email es obligatorio")
    private String codigoOEmail;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private String tipoLogin; // "academico" o "administrativo"

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsDTOLoginRequest() {}

    public clsDTOLoginRequest(String codigoOEmail, String password, String tipoLogin) {
        this.codigoOEmail = codigoOEmail;
        this.password = password;
        this.tipoLogin = tipoLogin;
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================

    public String getCodigoOEmail() {
        return codigoOEmail;
    }

    public void setCodigoOEmail(String codigoOEmail) {
        this.codigoOEmail = codigoOEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }
}
