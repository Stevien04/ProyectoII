package com.integraupt.controlador;

import com.integraupt.dto.clsDTOLoginRequest;
import com.integraupt.dto.clsDTOLoginResponse;
import com.integraupt.servicio.clsServicioAuth;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para autenticación
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class clsControladorAuth {

    @Autowired
    private clsServicioAuth servicioAuth;

    /**
     * Endpoint para login de usuarios
     * POST /api/auth/login
     * @param request datos de login
     * @return respuesta con usuario autenticado o error
     */
    @PostMapping("/login")
    public ResponseEntity<clsDTOLoginResponse> login(@Valid @RequestBody clsDTOLoginRequest request) {
        try {
            clsDTOLoginResponse response = servicioAuth.login(request);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            clsDTOLoginResponse errorResponse = new clsDTOLoginResponse(
                false, 
                "Error en el servidor: " + e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Endpoint para verificar credenciales
     * POST /api/auth/verificar
     * @param request datos a verificar
     * @return true si las credenciales son correctas
     */
    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificar(@RequestBody clsDTOLoginRequest request) {
        try {
            boolean valido = servicioAuth.verificarCredenciales(
                request.getCodigoOEmail(), 
                request.getPassword()
            );
            return ResponseEntity.ok(valido);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    /**
     * Endpoint de prueba para verificar que el backend está funcionando
     * GET /api/auth/ping
     * @return mensaje de confirmación
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("IntegraUPT Backend está funcionando correctamente ✓");
    }
}
