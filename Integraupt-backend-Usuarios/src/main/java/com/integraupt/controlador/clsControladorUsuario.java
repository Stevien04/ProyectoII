package com.integraupt.controlador;

import com.integraupt.dto.clsDTOEstadoResponse;
import com.integraupt.dto.clsDTOUsuarioRequest;
import com.integraupt.dto.clsDTOUsuarioResponse;
import com.integraupt.servicio.clsServicioUsuario;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de gestionar las operaciones sobre los usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
public class clsControladorUsuario {

    private final clsServicioUsuario servicioUsuario;

    public clsControladorUsuario(clsServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping
    public List<clsDTOUsuarioResponse> listarUsuarios() {
        return servicioUsuario.listarUsuarios();
    }

    @GetMapping("/{id}")
    public clsDTOUsuarioResponse obtenerUsuario(@PathVariable Integer id) {
        return servicioUsuario.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<clsDTOUsuarioResponse> crearUsuario(@Valid @RequestBody clsDTOUsuarioRequest request) {
        clsDTOUsuarioResponse response = servicioUsuario.crearUsuario(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public clsDTOUsuarioResponse actualizarUsuario(@PathVariable Integer id,
                                                   @Valid @RequestBody clsDTOUsuarioRequest request) {
        return servicioUsuario.actualizarUsuario(id, request);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(@PathVariable Integer id,
                                                 @Valid @RequestBody clsDTOActualizarEstadoRequest request) {
        servicioUsuario.actualizarEstado(id, request.getEstado());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        servicioUsuario.eliminarLogicamente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/catalogos/estados")
    public List<clsDTOEstadoResponse> obtenerEstados() {
        return servicioUsuario.obtenerCatalogoEstados();
    }
}