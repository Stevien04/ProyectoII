package com.integraupt.servicio;

import com.integraupt.dto.clsDTOLoginRequest;
import com.integraupt.dto.clsDTOLoginResponse;
import com.integraupt.dto.clsDTOLoginResponse.PerfilDTO;
import com.integraupt.entidad.clsEntidadUsuario;
import com.integraupt.repositorio.clsRepositorioAuth;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Servicio encargado de manejar la lógica de autenticación de usuarios.
 */
@Service
public class clsServicioAuth {

    private static final Logger LOGGER = LoggerFactory.getLogger(clsServicioAuth.class);

    private final clsRepositorioAuth repositorioAuth;

    public clsServicioAuth(clsRepositorioAuth repositorioAuth) {
        this.repositorioAuth = repositorioAuth;
    }

    /**
     * Autentica a un usuario con las credenciales proporcionadas.
     *
     * @param request datos del login
     * @return respuesta con el resultado del proceso de autenticación
     */
    @Transactional(readOnly = true)
    public clsDTOLoginResponse autenticarUsuario(clsDTOLoginRequest request) {
        if (request == null) {
            return clsDTOLoginResponse.error("La solicitud es inválida");
        }

        String identificador = normalizar(request.getCodigoOEmail());
        String password = normalizar(request.getPassword());
        String tipoLogin = normalizar(request.getTipoLogin());

        if (!StringUtils.hasText(identificador) || !StringUtils.hasText(password)) {
            return clsDTOLoginResponse.error("Debe proporcionar usuario y contraseña");
        }

        Optional<clsEntidadUsuario> usuarioOpt = buscarUsuarioPorIdentificador(identificador, tipoLogin);

        if (usuarioOpt.isEmpty()) {
            LOGGER.warn("Intento de acceso con identificador no encontrado: {}", identificador);
            return clsDTOLoginResponse.error("Credenciales inválidas");
        }

        clsEntidadUsuario usuario = usuarioOpt.get();

        if (!validarPassword(password, usuario.getPassword())) {
            LOGGER.warn("Contraseña incorrecta para el usuario: {}", identificador);
            return clsDTOLoginResponse.error("Credenciales inválidas");
        }

        PerfilDTO perfilDTO = construirPerfil(usuario);
        String token = generarTokenBasico(usuario);

        return clsDTOLoginResponse.success("Inicio de sesión exitoso", perfilDTO, token);
    }

    private Optional<clsEntidadUsuario> buscarUsuarioPorIdentificador(String identificador, String tipoLogin) {
        if (StringUtils.hasText(tipoLogin)) {
            Optional<clsEntidadUsuario> porTipo = repositorioAuth
                    .findFirstByTipoLoginIgnoreCaseAndCodigoIgnoreCase(tipoLogin, identificador)
                    .or(() -> repositorioAuth.findFirstByTipoLoginIgnoreCaseAndEmailIgnoreCase(tipoLogin, identificador));

            if (porTipo.isPresent()) {
                return porTipo;
            }
        }

        return repositorioAuth.findFirstByCodigoIgnoreCase(identificador)
                .or(() -> repositorioAuth.findFirstByEmailIgnoreCase(identificador));
    }

    private String normalizar(String valor) {
        return valor != null ? valor.trim() : null;
    }

    private boolean validarPassword(String passwordIngresada, String passwordAlmacenada) {
        if (!StringUtils.hasText(passwordAlmacenada)) {
            return false;
        }

        if (passwordAlmacenada.startsWith("$2a$") || passwordAlmacenada.startsWith("$2b$")
                || passwordAlmacenada.startsWith("$2y$")) {
            try {
                org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder encoder =
                        new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
                return encoder.matches(passwordIngresada, passwordAlmacenada);
            } catch (Exception ex) {
                LOGGER.error("Error validando contraseña encriptada", ex);
                return false;
            }
        }

        return passwordAlmacenada.equals(passwordIngresada);
    }

    private PerfilDTO construirPerfil(clsEntidadUsuario usuario) {
        return new PerfilDTO(
                usuario.getId(),
                usuario.getCodigo(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getTipoLogin(),
                usuario.getAvatarUrl(),
                usuario.getEstado(),
                usuario.getCelular(),
                usuario.getEscuela(),
                usuario.getFacultad(),
                usuario.getGenero(),
                usuario.getNumeroDocumento()
        );
    }

    private String generarTokenBasico(clsEntidadUsuario usuario) {
        String payload = usuario.getId() + ":" + Instant.now();
        return Base64.getEncoder().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
    }
}