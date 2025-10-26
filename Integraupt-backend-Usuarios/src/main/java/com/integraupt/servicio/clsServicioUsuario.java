package main.java.com.integraupt.servicio;

import main.java.com.integraupt.dto.clsDTOEstadoResponse;
import main.java.com.integraupt.dto.clsDTOUsuarioRequest;
import main.java.com.integraupt.dto.clsDTOUsuarioResponse;
import main.java.com.integraupt.entidad.clsEntidadUsuario;
import main.java.com.integraupt.entidad.clsEstadoUsuario;
import com.integraupt.repositorio.clsRepositorioUsuario;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 * Servicio que encapsula la lógica de negocio del módulo de usuarios.
 */
@Service
public class clsServicioUsuario {

    private final clsRepositorioUsuario repositorioUsuario;

    public clsServicioUsuario(clsRepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Transactional(readOnly = true)
    public List<clsDTOUsuarioResponse> listarUsuarios() {
        return repositorioUsuario.findAll().stream()
                .map(this::mapearARespuesta)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public clsDTOUsuarioResponse obtenerPorId(Integer id) {
        clsEntidadUsuario entidad = repositorioUsuario.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El usuario con id " + id + " no existe"));
        return mapearARespuesta(entidad);
    }

    @Transactional
    public clsDTOUsuarioResponse crearUsuario(clsDTOUsuarioRequest request) {
        validarDatosObligatoriosParaCreacion(request);
        validarRestriccionesUnicas(request, null);

        clsEntidadUsuario entidad = new clsEntidadUsuario();
        actualizarEntidadConRequest(entidad, request, true);

        clsEntidadUsuario guardado = repositorioUsuario.save(entidad);
        return mapearARespuesta(guardado);
    }

    @Transactional
    public clsDTOUsuarioResponse actualizarUsuario(Integer id, clsDTOUsuarioRequest request) {
        clsEntidadUsuario entidad = repositorioUsuario.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El usuario con id " + id + " no existe"));

        validarRestriccionesUnicas(request, id);
        actualizarEntidadConRequest(entidad, request, false);

        clsEntidadUsuario actualizado = repositorioUsuario.save(entidad);
        return mapearARespuesta(actualizado);
    }

    @Transactional
    public void actualizarEstado(Integer id, Integer estado) {
        clsEntidadUsuario entidad = repositorioUsuario.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "El usuario con id " + id + " no existe"));

        clsEstadoUsuario estadoUsuario = clsEstadoUsuario.fromId(estado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El estado proporcionado no es válido"));

        entidad.setEstado(estadoUsuario.getId());
        repositorioUsuario.save(entidad);
    }

    @Transactional
    public void eliminarLogicamente(Integer id) {
        actualizarEstado(id, clsEstadoUsuario.INACTIVO.getId());
    }

    @Transactional(readOnly = true)
    public List<clsDTOEstadoResponse> obtenerCatalogoEstados() {
        return List.of(
                new clsDTOEstadoResponse(clsEstadoUsuario.ACTIVO.getId(), clsEstadoUsuario.ACTIVO.getDescripcion()),
                new clsDTOEstadoResponse(clsEstadoUsuario.INACTIVO.getId(), clsEstadoUsuario.INACTIVO.getDescripcion())
        );
    }

    private void validarDatosObligatoriosParaCreacion(clsDTOUsuarioRequest request) {
        if (!StringUtils.hasText(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña es obligatoria");
        }
    }

    private void validarRestriccionesUnicas(clsDTOUsuarioRequest request, Integer idActual) {
        if (StringUtils.hasText(request.getCodigo())) {
            boolean existeCodigo = (idActual == null)
                    ? repositorioUsuario.existsByCodigoIgnoreCase(request.getCodigo())
                    : repositorioUsuario.existsByCodigoIgnoreCaseAndIdNot(request.getCodigo(), idActual);
            if (existeCodigo) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El código ya se encuentra registrado");
            }
        }

        if (StringUtils.hasText(request.getEmail())) {
            boolean existeCorreo = (idActual == null)
                    ? repositorioUsuario.existsByEmailIgnoreCase(request.getEmail())
                    : repositorioUsuario.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), idActual);
            if (existeCorreo) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya se encuentra registrado");
            }
        }

        if (StringUtils.hasText(request.getNumeroDocumento())) {
            boolean existeDocumento = (idActual == null)
                    ? repositorioUsuario.existsByNumeroDocumentoIgnoreCase(request.getNumeroDocumento())
                    : repositorioUsuario.existsByNumeroDocumentoIgnoreCaseAndIdNot(request.getNumeroDocumento(), idActual);
            if (existeDocumento) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El número de documento ya se encuentra registrado");
            }
        }
    }

    private void actualizarEntidadConRequest(clsEntidadUsuario entidad, clsDTOUsuarioRequest request, boolean esCreacion) {
        entidad.setNombres(request.getNombres());
        entidad.setApellidos(request.getApellidos());
        entidad.setCodigo(request.getCodigo());
        entidad.setEmail(request.getEmail());
        entidad.setTipoDocumento(request.getTipoDocumento());
        entidad.setNumeroDocumento(request.getNumeroDocumento());
        entidad.setRolId(request.getRolId());
        entidad.setFacultadId(request.getFacultadId());
        entidad.setEscuelaId(request.getEscuelaId());
        entidad.setCelular(request.getCelular());
        entidad.setGenero(request.getGenero());

        if (esCreacion || StringUtils.hasText(request.getPassword())) {
            entidad.setPassword(request.getPassword());
        }

        if (request.getEstado() != null) {
            clsEstadoUsuario estadoUsuario = clsEstadoUsuario.fromId(request.getEstado())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "El estado proporcionado no es válido"));
            entidad.setEstado(estadoUsuario.getId());
        } else if (esCreacion && entidad.getEstado() == null) {
            entidad.setEstado(clsEstadoUsuario.ACTIVO.getId());
        }

        entidad.setSesion(Objects.requireNonNullElse(request.getSesion(), Boolean.FALSE));
    }

    private clsDTOUsuarioResponse mapearARespuesta(clsEntidadUsuario entidad) {
        clsDTOUsuarioResponse response = new clsDTOUsuarioResponse();
        response.setId(entidad.getId());
        response.setNombres(entidad.getNombres());
        response.setApellidos(entidad.getApellidos());
        response.setCodigo(entidad.getCodigo());
        response.setEmail(entidad.getEmail());
        response.setTipoDocumento(entidad.getTipoDocumento());
        response.setNumeroDocumento(entidad.getNumeroDocumento());
        response.setRolId(entidad.getRolId());
        response.setFacultadId(entidad.getFacultadId());
        response.setEscuelaId(entidad.getEscuelaId());
        response.setCelular(entidad.getCelular());
        response.setGenero(entidad.getGenero());
        response.setEstado(entidad.getEstado());
        response.setSesion(entidad.getSesion());

        clsEstadoUsuario estadoUsuario = clsEstadoUsuario.fromId(entidad.getEstado())
                .orElse(clsEstadoUsuario.ACTIVO);
        response.setEstadoDescripcion(estadoUsuario.getDescripcion());

        return response;
    }
}