package com.integraupt.servicio;

import com.integraupt.dto.clsDTOLoginRequest;
import com.integraupt.dto.clsDTOLoginResponse;
import com.integraupt.entidad.clsEntidadUsuario;
import com.integraupt.repositorio.clsRepositorioAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class clsServicioAuth {

    @Autowired
    private clsRepositorioAuth repositorioAuth;

    /**
     * Autentica un usuario con código/email y contraseña
     */
    public clsDTOLoginResponse login(clsDTOLoginRequest request) {
        try {
            // Buscar usuario por código o correo
            Optional<clsEntidadUsuario> usuarioOpt = repositorioAuth.findByCodigoOEmail(request.getCodigoOEmail().trim());

            if (usuarioOpt.isEmpty()) {
                return new clsDTOLoginResponse(false, "Usuario no encontrado");
            }

            clsEntidadUsuario usuario = usuarioOpt.get();

            // Verificar estado (1 = activo)
            if (usuario.getEstado() != 1) {
                return new clsDTOLoginResponse(false, "Usuario inactivo o bloqueado");
            }

            // Verificar contraseña (sin encriptar por ahora)
            if (!usuario.getPassword().equals(request.getPassword())) {
                return new clsDTOLoginResponse(false, "Contraseña incorrecta");
            }

            // Construir respuesta
            clsDTOLoginResponse response = new clsDTOLoginResponse(true, "Login exitoso");
            response.setUserId(usuario.getIdUsuario());
            response.setEmail(usuario.getCorreoU());

            return response;

        } catch (Exception e) {
            System.out.println("\n===============================");
            System.out.println("❌ ERROR EN LOGIN DETECTADO");
            e.printStackTrace();
            System.out.println("===============================\n");
            return new clsDTOLoginResponse(false, "Error en el servidor: " + e.getMessage());
        }
    }

    /**
     * Verifica las credenciales de un usuario
     */
    public boolean verificarCredenciales(String codigoOEmail, String password) {
        Optional<clsEntidadUsuario> usuarioOpt = repositorioAuth.findByCodigoOEmail(codigoOEmail.trim());

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        clsEntidadUsuario usuario = usuarioOpt.get();

        return usuario.getPassword().equals(password) && usuario.getEstado() == 1;
    }
}
