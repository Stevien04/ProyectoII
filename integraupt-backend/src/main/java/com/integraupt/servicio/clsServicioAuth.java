package com.integraupt.servicio;

import com.integraupt.dto.clsDTOLoginRequest;
import com.integraupt.dto.clsDTOLoginResponse;
import com.integraupt.dto.clsDTOPerfilResponse;
import com.integraupt.entidad.clsEntidadPerfil;
import com.integraupt.repositorio.clsRepositorioPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Servicio para autenticación de usuarios
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Service
@Transactional
public class clsServicioAuth {

    @Autowired
    private clsRepositorioPerfil repositorioPerfil;

    /**
     * Autentica un usuario con código/email y contraseña
     * @param request solicitud de login
     * @return respuesta de login
     */
    public clsDTOLoginResponse login(clsDTOLoginRequest request) {
        try {
            // Buscar usuario por código o email
            Optional<clsEntidadPerfil> perfilOpt;
            
            if (request.getCodigoOEmail().contains("@")) {
                // Es un email
                perfilOpt = repositorioPerfil.findByEmail(request.getCodigoOEmail());
            } else {
                // Es un código
                perfilOpt = repositorioPerfil.findByCodigo(request.getCodigoOEmail());
            }

            if (!perfilOpt.isPresent()) {
                return new clsDTOLoginResponse(false, "Usuario no encontrado");
            }

            clsEntidadPerfil perfil = perfilOpt.get();

            // Verificar que el usuario esté activo
            if (!"Activo".equalsIgnoreCase(perfil.getEstado())) {
                return new clsDTOLoginResponse(false, "Usuario inactivo o bloqueado");
            }

            // Verificar tipo de login
            if (request.getTipoLogin() != null && 
                !request.getTipoLogin().equalsIgnoreCase(perfil.getTipoLogin())) {
                return new clsDTOLoginResponse(false, "Tipo de acceso incorrecto");
            }

            // Verificar contraseña
            // TODO: En producción, usar BCrypt.checkPassword()
            if (!perfil.getPassword().equals(request.getPassword())) {
                return new clsDTOLoginResponse(false, "Contraseña incorrecta");
            }

            // Login exitoso
            clsDTOPerfilResponse perfilResponse = new clsDTOPerfilResponse(perfil);
            clsDTOLoginResponse response = new clsDTOLoginResponse(
                true, 
                "Login exitoso", 
                perfilResponse
            );

            // TODO: Generar y agregar JWT token
            // response.setToken(jwtService.generateToken(perfil));

            return response;

        } catch (Exception e) {
            return new clsDTOLoginResponse(false, "Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Verifica las credenciales de un usuario
     * @param codigoOEmail código o email
     * @param password contraseña
     * @return true si las credenciales son correctas
     */
    public boolean verificarCredenciales(String codigoOEmail, String password) {
        Optional<clsEntidadPerfil> perfilOpt;
        
        if (codigoOEmail.contains("@")) {
            perfilOpt = repositorioPerfil.findByEmail(codigoOEmail);
        } else {
            perfilOpt = repositorioPerfil.findByCodigo(codigoOEmail);
        }

        if (!perfilOpt.isPresent()) {
            return false;
        }

        clsEntidadPerfil perfil = perfilOpt.get();
        
        // TODO: En producción, usar BCrypt
        return perfil.getPassword().equals(password) && 
               "Activo".equalsIgnoreCase(perfil.getEstado());
    }
}
