package com.integraupt.dto;

/**
 * DTO para respuesta del login de usuario
 *
 * @author IntegraUPT
 * @version 1.0.0
 */
public class clsDTOLoginResponse {

    private boolean success;
    private String message;
    private Integer userId;
    private String email;
    private String token;

    public clsDTOLoginResponse() {
    }

    public clsDTOLoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public clsDTOLoginResponse(boolean success, String message, Integer userId, String email) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.email = email;
    }

    // Getters y Setters
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
