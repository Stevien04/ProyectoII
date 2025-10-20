package com.integraupt.controlador;

import com.integraupt.entidad.clsEntidadReserva;
import com.integraupt.servicio.clsServicioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestión de reservas
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class clsControladorReserva {

    @Autowired
    private clsServicioReserva servicioReserva;

    /**
     * Obtiene todas las reservas
     * GET /api/reservas
     * @return lista de reservas
     */
    @GetMapping
    public ResponseEntity<List<clsEntidadReserva>> obtenerTodas() {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerTodas();
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene una reserva por ID
     * GET /api/reservas/{id}
     * @param id ID de la reserva
     * @return reserva encontrada o 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<clsEntidadReserva> obtenerPorId(@PathVariable String id) {
        return servicioReserva.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene reservas de un usuario
     * GET /api/reservas/usuario/{usuarioId}
     * @param usuarioId ID del usuario
     * @return lista de reservas
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<clsEntidadReserva>> obtenerPorUsuario(@PathVariable String usuarioId) {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene reservas aprobadas de un usuario
     * GET /api/reservas/usuario/{usuarioId}/aprobadas
     * @param usuarioId ID del usuario
     * @return lista de reservas aprobadas
     */
    @GetMapping("/usuario/{usuarioId}/aprobadas")
    public ResponseEntity<List<clsEntidadReserva>> obtenerAprobadasPorUsuario(@PathVariable String usuarioId) {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerAprobadasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene reservas futuras de un usuario
     * GET /api/reservas/usuario/{usuarioId}/futuras
     * @param usuarioId ID del usuario
     * @return lista de reservas futuras
     */
    @GetMapping("/usuario/{usuarioId}/futuras")
    public ResponseEntity<List<clsEntidadReserva>> obtenerFuturasDeUsuario(@PathVariable String usuarioId) {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerFuturasDeUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene reservas pendientes
     * GET /api/reservas/pendientes
     * @return lista de reservas pendientes
     */
    @GetMapping("/pendientes")
    public ResponseEntity<List<clsEntidadReserva>> obtenerPendientes() {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerPendientes();
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene reservas de un espacio
     * GET /api/reservas/espacio/{espacioId}
     * @param espacioId ID del espacio
     * @return lista de reservas
     */
    @GetMapping("/espacio/{espacioId}")
    public ResponseEntity<List<clsEntidadReserva>> obtenerPorEspacio(@PathVariable String espacioId) {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerPorEspacio(espacioId);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Obtiene reservas del día para un espacio
     * GET /api/reservas/espacio/{espacioId}/fecha/{fecha}
     * @param espacioId ID del espacio
     * @param fecha fecha (formato: yyyy-MM-dd)
     * @return lista de reservas del día
     */
    @GetMapping("/espacio/{espacioId}/fecha/{fecha}")
    public ResponseEntity<List<clsEntidadReserva>> obtenerReservasDelDia(
            @PathVariable String espacioId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<clsEntidadReserva> reservas = servicioReserva.obtenerReservasDelDia(espacioId, fecha);
        return ResponseEntity.ok(reservas);
    }

    /**
     * Crea una nueva reserva
     * POST /api/reservas
     * @param reserva datos de la reserva
     * @return reserva creada o error
     */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody clsEntidadReserva reserva) {
        try {
            clsEntidadReserva reservaCreada = servicioReserva.crear(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaCreada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al crear la reserva: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Actualiza una reserva existente
     * PUT /api/reservas/{id}
     * @param id ID de la reserva
     * @param reserva datos actualizados
     * @return reserva actualizada o 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<clsEntidadReserva> actualizar(
            @PathVariable String id, 
            @RequestBody clsEntidadReserva reserva) {
        return servicioReserva.actualizar(id, reserva)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Aprueba una reserva
     * PUT /api/reservas/{id}/aprobar
     * @param id ID de la reserva
     * @param body debe contener: { "aprobadorId": "xxx" }
     * @return reserva aprobada o 404
     */
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<clsEntidadReserva> aprobar(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String aprobadorId = body.get("aprobadorId");
        if (aprobadorId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return servicioReserva.aprobar(id, aprobadorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Rechaza una reserva
     * PUT /api/reservas/{id}/rechazar
     * @param id ID de la reserva
     * @param body debe contener: { "aprobadorId": "xxx", "motivo": "xxx" }
     * @return reserva rechazada o 404
     */
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<clsEntidadReserva> rechazar(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String aprobadorId = body.get("aprobadorId");
        String motivo = body.get("motivo");
        
        if (aprobadorId == null || motivo == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return servicioReserva.rechazar(id, aprobadorId, motivo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Elimina una reserva (cancelar)
     * DELETE /api/reservas/{id}
     * @param id ID de la reserva a eliminar
     * @return 204 si se eliminó o 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (servicioReserva.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Cuenta reservas por estado
     * GET /api/reservas/contar/{estado}
     * @param estado estado a contar
     * @return cantidad de reservas
     */
    @GetMapping("/contar/{estado}")
    public ResponseEntity<Long> contarPorEstado(@PathVariable String estado) {
        long cantidad = servicioReserva.contarPorEstado(estado);
        return ResponseEntity.ok(cantidad);
    }
}
