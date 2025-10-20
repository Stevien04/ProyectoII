package com.integraupt.controlador;

import com.integraupt.dto.clsDTOPerfilResponse;
import com.integraupt.entidad.clsEntidadPerfil;
import com.integraupt.servicio.clsServicioPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de perfiles de usuario
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/perfiles")
@CrossOrigin(origins = "*")
public class clsControladorPerfil {

    @Autowired
    private clsServicioPerfil servicioPerfil;

    /**
     * Obtiene todos los perfiles
     * GET /api/perfiles
     * @return lista de perfiles
     */
    @GetMapping
    public ResponseEntity<List<clsDTOPerfilResponse>> obtenerTodos() {
        List<clsDTOPerfilResponse> perfiles = servicioPerfil.obtenerTodos();
        return ResponseEntity.ok(perfiles);
    }

    /**
     * Obtiene un perfil por ID
     * GET /api/perfiles/{id}
     * @param id ID del perfil
     * @return perfil encontrado o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<clsDTOPerfilResponse> obtenerPorId(@PathVariable String id) {
        return servicioPerfil.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene un perfil por código
     * GET /api/perfiles/codigo/{codigo}
     * @param codigo código del usuario
     * @return perfil encontrado o 404
     */
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<clsDTOPerfilResponse> obtenerPorCodigo(@PathVariable String codigo) {
        return servicioPerfil.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene perfiles por rol
     * GET /api/perfiles/rol/{rol}
     * @param rol rol a buscar
     * @return lista de perfiles
     */
    @GetMapping("/rol/{rol}")
    public ResponseEntity<List<clsDTOPerfilResponse>> obtenerPorRol(@PathVariable String rol) {
        List<clsDTOPerfilResponse> perfiles = servicioPerfil.obtenerPorRol(rol);
        return ResponseEntity.ok(perfiles);
    }

    /**
     * Obtiene perfiles por facultad
     * GET /api/perfiles/facultad/{facultad}
     * @param facultad facultad a buscar
     * @return lista de perfiles
     */
    @GetMapping("/facultad/{facultad}")
    public ResponseEntity<List<clsDTOPerfilResponse>> obtenerPorFacultad(@PathVariable String facultad) {
        List<clsDTOPerfilResponse> perfiles = servicioPerfil.obtenerPorFacultad(facultad);
        return ResponseEntity.ok(perfiles);
    }

    /**
     * Busca perfiles por nombre o apellido
     * GET /api/perfiles/buscar?termino=xxx
     * @param termino término de búsqueda
     * @return lista de perfiles
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<clsDTOPerfilResponse>> buscar(@RequestParam String termino) {
        List<clsDTOPerfilResponse> perfiles = servicioPerfil.buscarPorNombre(termino);
        return ResponseEntity.ok(perfiles);
    }

    /**
     * Crea un nuevo perfil
     * POST /api/perfiles
     * @param perfil datos del perfil
     * @return perfil creado
     */
    @PostMapping
    public ResponseEntity<clsDTOPerfilResponse> crear(@RequestBody clsEntidadPerfil perfil) {
        try {
            clsDTOPerfilResponse perfilCreado = servicioPerfil.crear(perfil);
            return ResponseEntity.status(HttpStatus.CREATED).body(perfilCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un perfil existente
     * PUT /api/perfiles/{id}
     * @param id ID del perfil
     * @param perfil datos actualizados
     * @return perfil actualizado o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<clsDTOPerfilResponse> actualizar(
            @PathVariable String id, 
            @RequestBody clsEntidadPerfil perfil) {
        return servicioPerfil.actualizar(id, perfil)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un perfil
     * DELETE /api/perfiles/{id}
     * @param id ID del perfil a eliminar
     * @return 204 si se eliminó o 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (servicioPerfil.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cuenta perfiles por rol
     * GET /api/perfiles/contar/rol/{rol}
     * @param rol rol a contar
     * @return cantidad de perfiles
     */
    @GetMapping("/contar/rol/{rol}")
    public ResponseEntity<Long> contarPorRol(@PathVariable String rol) {
        long cantidad = servicioPerfil.contarPorRol(rol);
        return ResponseEntity.ok(cantidad);
    }
}
