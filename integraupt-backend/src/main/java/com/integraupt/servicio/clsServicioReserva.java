package com.integraupt.servicio;

import com.integraupt.entidad.clsEntidadReserva;
import com.integraupt.repositorio.clsRepositorioReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de reservas de espacios
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Service
@Transactional
public class clsServicioReserva {

    @Autowired
    private clsRepositorioReserva repositorioReserva;

    /**
     * Obtiene todas las reservas
     * @return lista de reservas
     */
    public List<clsEntidadReserva> obtenerTodas() {
        return repositorioReserva.findAll();
    }

    /**
     * Obtiene una reserva por ID
     * @param id ID de la reserva
     * @return Optional con la reserva
     */
    public Optional<clsEntidadReserva> obtenerPorId(String id) {
        return repositorioReserva.findById(id);
    }

    /**
     * Obtiene reservas de un usuario
     * @param usuarioId ID del usuario
     * @return lista de reservas
     */
    public List<clsEntidadReserva> obtenerPorUsuario(String usuarioId) {
        return repositorioReserva.findByUsuarioId(usuarioId);
    }

    /**
     * Obtiene reservas de un espacio
     * @param espacioId ID del espacio
     * @return lista de reservas
     */
    public List<clsEntidadReserva> obtenerPorEspacio(String espacioId) {
        return repositorioReserva.findByEspacioId(espacioId);
    }

    /**
     * Obtiene reservas pendientes
     * @return lista de reservas pendientes
     */
    public List<clsEntidadReserva> obtenerPendientes() {
        return repositorioReserva.findReservasPendientes();
    }

    /**
     * Obtiene reservas aprobadas de un usuario
     * @param usuarioId ID del usuario
     * @return lista de reservas aprobadas
     */
    public List<clsEntidadReserva> obtenerAprobadasPorUsuario(String usuarioId) {
        return repositorioReserva.findReservasAprobadasPorUsuario(usuarioId);
    }

    /**
     * Obtiene reservas futuras de un usuario
     * @param usuarioId ID del usuario
     * @return lista de reservas futuras
     */
    public List<clsEntidadReserva> obtenerFuturasDeUsuario(String usuarioId) {
        return repositorioReserva.findReservasFuturas(usuarioId, LocalDate.now());
    }

    /**
     * Obtiene reservas del día para un espacio
     * @param espacioId ID del espacio
     * @param fecha fecha
     * @return lista de reservas del día
     */
    public List<clsEntidadReserva> obtenerReservasDelDia(String espacioId, LocalDate fecha) {
        return repositorioReserva.findReservasDelDia(espacioId, fecha);
    }

    /**
     * Verifica si hay conflicto de horario
     * @param espacioId ID del espacio
     * @param fecha fecha de la reserva
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     * @return true si hay conflicto
     */
    public boolean hayConflictoHorario(String espacioId, LocalDate fecha, 
                                       LocalTime horaInicio, LocalTime horaFin) {
        long conflictos = repositorioReserva.verificarConflictoHorario(
            espacioId, fecha, horaInicio, horaFin
        );
        return conflictos > 0;
    }

    /**
     * Crea una nueva reserva
     * @param reserva entidad a crear
     * @return reserva creada
     */
    public clsEntidadReserva crear(clsEntidadReserva reserva) {
        // Verificar conflictos de horario antes de crear
        if (hayConflictoHorario(reserva.getEspacioId(), reserva.getFecha(), 
                                reserva.getHoraInicio(), reserva.getHoraFin())) {
            throw new RuntimeException("Ya existe una reserva en ese horario");
        }
        return repositorioReserva.save(reserva);
    }

    /**
     * Actualiza una reserva existente
     * @param id ID de la reserva
     * @param reserva datos actualizados
     * @return reserva actualizada
     */
    public Optional<clsEntidadReserva> actualizar(String id, clsEntidadReserva reserva) {
        return repositorioReserva.findById(id)
                .map(reservaExistente -> {
                    if (reserva.getFecha() != null) reservaExistente.setFecha(reserva.getFecha());
                    if (reserva.getHoraInicio() != null) reservaExistente.setHoraInicio(reserva.getHoraInicio());
                    if (reserva.getHoraFin() != null) reservaExistente.setHoraFin(reserva.getHoraFin());
                    if (reserva.getCiclo() != null) reservaExistente.setCiclo(reserva.getCiclo());
                    if (reserva.getCurso() != null) reservaExistente.setCurso(reserva.getCurso());
                    if (reserva.getMotivo() != null) reservaExistente.setMotivo(reserva.getMotivo());
                    if (reserva.getEstado() != null) reservaExistente.setEstado(reserva.getEstado());
                    
                    return repositorioReserva.save(reservaExistente);
                });
    }

    /**
     * Aprueba una reserva
     * @param id ID de la reserva
     * @param aprobadorId ID del administrador que aprueba
     * @return reserva aprobada
     */
    public Optional<clsEntidadReserva> aprobar(String id, String aprobadorId) {
        return repositorioReserva.findById(id)
                .map(reserva -> {
                    reserva.aprobar(aprobadorId);
                    return repositorioReserva.save(reserva);
                });
    }

    /**
     * Rechaza una reserva
     * @param id ID de la reserva
     * @param aprobadorId ID del administrador que rechaza
     * @param motivo motivo del rechazo
     * @return reserva rechazada
     */
    public Optional<clsEntidadReserva> rechazar(String id, String aprobadorId, String motivo) {
        return repositorioReserva.findById(id)
                .map(reserva -> {
                    reserva.rechazar(aprobadorId, motivo);
                    return repositorioReserva.save(reserva);
                });
    }

    /**
     * Elimina una reserva (cancelar)
     * @param id ID de la reserva a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(String id) {
        if (repositorioReserva.existsById(id)) {
            repositorioReserva.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Cuenta reservas por estado
     * @param estado estado a contar
     * @return cantidad de reservas
     */
    public long contarPorEstado(String estado) {
        return repositorioReserva.countByEstado(estado);
    }
}
