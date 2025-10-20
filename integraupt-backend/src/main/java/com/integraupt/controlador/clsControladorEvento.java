package com.integraupt.controlador;

import com.integraupt.entidad.clsEntidadEvento;
import com.integraupt.servicio.clsServicioEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para gestión de eventos
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class clsControladorEvento {

    @Autowired
    private clsServicioEvento servicioEvento;

    /**
     * Obtiene todos los eventos
     * GET /api/eventos
     * @return lista de eventos
     */
    @GetMapping
    public ResponseEntity<List<clsEntidadEvento>> obtenerTodos() {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerTodos();
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene un evento por ID
     * GET /api/eventos/{id}
     * @param id ID del evento
     * @return evento encontrado o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<clsEntidadEvento> obtenerPorId(@PathVariable String id) {
        return servicioEvento.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene eventos activos
     * GET /api/eventos/activos
     * @return lista de eventos activos
     */
    @GetMapping("/activos")
    public ResponseEntity<List<clsEntidadEvento>> obtenerActivos() {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerActivos();
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene eventos próximos
     * GET /api/eventos/proximos
     * @return lista de eventos próximos
     */
    @GetMapping("/proximos")
    public ResponseEntity<List<clsEntidadEvento>> obtenerProximos() {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerProximos();
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene eventos del día actual
     * GET /api/eventos/hoy
     * @return lista de eventos de hoy
     */
    @GetMapping("/hoy")
    public ResponseEntity<List<clsEntidadEvento>> obtenerEventosDeHoy() {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerEventosDeHoy();
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene eventos por tipo
     * GET /api/eventos/tipo/{tipo}
     * @param tipo tipo de evento
     * @return lista de eventos
     */
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<clsEntidadEvento>> obtenerPorTipo(@PathVariable String tipo) {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerPorTipo(tipo);
        return ResponseEntity.ok(eventos);
    }

    /**
     * Busca eventos por título
     * GET /api/eventos/buscar?termino=xxx
     * @param termino término de búsqueda
     * @return lista de eventos
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<clsEntidadEvento>> buscar(@RequestParam String termino) {
        List<clsEntidadEvento> eventos = servicioEvento.buscarPorTitulo(termino);
        return ResponseEntity.ok(eventos);
    }

    /**
     * Obtiene eventos en un rango de fechas
     * GET /api/eventos/rango?inicio=xxx&fin=xxx
     * @param inicio fecha de inicio (formato: yyyy-MM-dd'T'HH:mm:ss)
     * @param fin fecha de fin
     * @return lista de eventos en ese rango
     */
    @GetMapping("/rango")
    public ResponseEntity<List<clsEntidadEvento>> obtenerEnRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<clsEntidadEvento> eventos = servicioEvento.obtenerEnRango(inicio, fin);
        return ResponseEntity.ok(eventos);
    }

    /**
     * Crea un nuevo evento
     * POST /api/eventos
     * @param evento datos del evento
     * @return evento creado
     */
    @PostMapping
    public ResponseEntity<clsEntidadEvento> crear(@RequestBody clsEntidadEvento evento) {
        try {
            clsEntidadEvento eventoCreado = servicioEvento.crear(evento);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza un evento existente
     * PUT /api/eventos/{id}
     * @param id ID del evento
     * @param evento datos actualizados
     * @return evento actualizado o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<clsEntidadEvento> actualizar(
            @PathVariable String id, 
            @RequestBody clsEntidadEvento evento) {
        return servicioEvento.actualizar(id, evento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina un evento
     * DELETE /api/eventos/{id}
     * @param id ID del evento a eliminar
     * @return 204 si se eliminó o 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (servicioEvento.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cuenta eventos por estado
     * GET /api/eventos/contar/estado/{estado}
     * @param estado estado a contar
     * @return cantidad de eventos
     */
    @GetMapping("/contar/estado/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable String estado) {
        long cantidad = servicioEvento.contarPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }

    /**
     * Cuenta eventos por tipo
     * GET /api/eventos/contar/tipo/{tipo}
     * @param tipo tipo a contar
     * @return cantidad de eventos
     */
    @GetMapping("/contar/tipo/{tipo}")
    public ResponseEntity<Long> contarPorTipo(@PathVariable String tipo) {
        long cantidad = servicioEvento.contarPorTipo(tipo);
        return ResponseEntity.ok(cantidad);
    }
}
