package com.integraupt.controlador;

import com.integraupt.entidad.clsEntidadEspacio;
import com.integraupt.servicio.clsServicioEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de espacios (aulas y laboratorios)
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/espacios")
@CrossOrigin(origins = "*")
public class clsControladorEspacio {

    @Autowired
    private clsServicioEspacio servicioEspacio;

    /**
     * Obtiene todos los espacios
     * GET /api/espacios
     * @return lista de espacios
     */
    @GetMapping
    public ResponseEntity<List<clsEntidadEspacio>> obtenerTodos() {
        List<clsEntidadEspacio> espacios = servicioEspacio.obtenerTodos();
        return ResponseEntity.ok(espacios);
    }

    /**
     * Obtiene un espacio por ID
     * GET /api/espacios/{id}
     * @param id ID del espacio
     * @return espacio encontrado o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<clsEntidadEspacio> obtenerPorId(@PathVariable String id) {
        return servicioEspacio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene espacios disponibles
     * GET /api/espacios/disponibles
     * @return lista de espacios disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<clsEntidadEspacio>> obtenerDisponibles() {
        List<clsEntidadEspacio> espacios = servicioEspacio.obtenerDisponibles();
        return ResponseEntity.ok(espacios);
    }

    /**
     * Obtiene laboratorios disponibles
     * GET /api/espacios/laboratorios/disponibles
     * @return lista de laboratorios disponibles
     */
    @GetMapping("/laboratorios/disponibles")
    public ResponseEntity<List<clsEntidadEspacio>> obtenerLaboratoriosDisponibles() {
        List<clsEntidadEspacio> laboratorios = servicioEspacio.obtenerLaboratoriosDisponibles();
        return ResponseEntity.ok(laboratorios);
    }

    /**
     * Obtiene aulas disponibles
     * GET /api/espacios/aulas/disponibles
     * @return lista de aulas disponibles
     */
    @GetMapping("/aulas/disponibles")
    public ResponseEntity<List<clsEntidadEspacio>> obtenerAulasDisponibles() {
        List<clsEntidadEspacio> aulas = servicioEspacio.obtenerAulasDisponibles();
        return ResponseEntity.ok(aulas);
    }

    /**
     * Obtiene espacios por tipo
     * GET /api/espacios/tipo/{tipo}
     * @param tipo tipo de espacio (Aula, Laboratorio)
     * @return lista de espacios
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<clsEntidadEspacio>> obtenerPorTipo(@PathVariable String tipo) {
        List<clsEntidadEspacio> espacios = servicioEspacio.obtenerPorTipo(tipo);
        return ResponseEntity.ok(espacios);
    }

    /**
     * Obtiene espacios por facultad
     * GET /api/espacios/facultad/{facultad}
     * @param facultad facultad
     * @return lista de espacios
     */
    @GetMapping("/facultad/{facultad}")
    public ResponseEntity<List<clsEntidadEspacio>> obtenerPorFacultad(@PathVariable String facultad) {
        List<clsEntidadEspacio> espacios = servicioEspacio.obtenerPorFacultad(facultad);
        return ResponseEntity.ok(espacios);
    }

    /**
     * Busca espacios por nombre
     * GET /api/espacios/buscar?termino=xxx
     * @param termino término de búsqueda
     * @return lista de espacios
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<clsEntidadEspacio>> buscar(@RequestParam String termino) {
        List<clsEntidadEspacio> espacios = servicioEspacio.buscarPorNombre(termino);
        return ResponseEntity.ok(espacios);
    }

    /**
     * Crea un nuevo espacio
     * POST /api/espacios
     * @param espacio datos del espacio
     * @return espacio creado
     */
    @PostMapping
    public ResponseEntity<clsEntidadEspacio> crear(@RequestBody clsEntidadEspacio espacio) {
        try {
            clsEntidadEspacio espacioCreado = servicioEspacio.crear(espacio);
            return ResponseEntity.status(HttpStatus.CREATED).body(espacioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un espacio existente
     * PUT /api/espacios/{id}
     * @param id ID del espacio
     * @param espacio datos actualizados
     * @return espacio actualizado o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<clsEntidadEspacio> actualizar(
            @PathVariable String id, 
            @RequestBody clsEntidadEspacio espacio) {
        return servicioEspacio.actualizar(id, espacio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un espacio
     * DELETE /api/espacios/{id}
     * @param id ID del espacio a eliminar
     * @return 204 si se eliminó o 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (servicioEspacio.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cuenta espacios por tipo
     * GET /api/espacios/contar/tipo/{tipo}
     * @param tipo tipo a contar
     * @return cantidad de espacios
     */
    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@PathVariable String tipo) {
        long cantidad = servicioEspacio.contarPorTipo(tipo);
        return ResponseEntity.ok(cantidad);
    }
}
