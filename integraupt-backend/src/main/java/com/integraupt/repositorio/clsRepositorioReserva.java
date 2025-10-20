package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repositorio para la entidad Reserva
 * Proporciona acceso a datos de reservas de espacios
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Repository
public interface clsRepositorioReserva extends JpaRepository<clsEntidadReserva, String> {

    /**
     * Busca reservas por usuario
     * @param usuarioId ID del usuario
     * @return lista de reservas del usuario
     */
    List<clsEntidadReserva> findByUsuarioId(String usuarioId);

    /**
     * Busca reservas por espacio
     * @param espacioId ID del espacio
     * @return lista de reservas del espacio
     */
    List<clsEntidadReserva> findByEspacioId(String espacioId);

    /**
     * Busca reservas por estado
     * @param estado estado de la reserva (pending, approved, cancelled)
     * @return lista de reservas con ese estado
     */
    List<clsEntidadReserva> findByEstado(String estado);

    /**
     * Busca reservas por fecha
     * @param fecha fecha de la reserva
     * @return lista de reservas en esa fecha
     */
    List<clsEntidadReserva> findByFecha(LocalDate fecha);

    /**
     * Busca reservas por usuario y estado
     * @param usuarioId ID del usuario
     * @param estado estado de la reserva
     * @return lista de reservas que coinciden
     */
    List<clsEntidadReserva> findByUsuarioIdAndEstado(String usuarioId, String estado);

    /**
     * Busca reservas por espacio y estado
     * @param espacioId ID del espacio
     * @param estado estado de la reserva
     * @return lista de reservas que coinciden
     */
    List<clsEntidadReserva> findByEspacioIdAndEstado(String espacioId, String estado);

    /**
     * Busca reservas pendientes
     * @return lista de reservas pendientes ordenadas por fecha
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.estado = 'pending' ORDER BY r.fecha, r.horaInicio")
    List<clsEntidadReserva> findReservasPendientes();

    /**
     * Busca reservas aprobadas de un usuario
     * @param usuarioId ID del usuario
     * @return lista de reservas aprobadas
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.usuarioId = :usuarioId AND r.estado = 'approved' ORDER BY r.fecha DESC, r.horaInicio")
    List<clsEntidadReserva> findReservasAprobadasPorUsuario(@Param("usuarioId") String usuarioId);

    /**
     * Busca reservas futuras de un usuario
     * @param usuarioId ID del usuario
     * @param fechaActual fecha actual
     * @return lista de reservas futuras
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.usuarioId = :usuarioId AND r.fecha >= :fechaActual ORDER BY r.fecha, r.horaInicio")
    List<clsEntidadReserva> findReservasFuturas(@Param("usuarioId") String usuarioId, @Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca reservas de hoy para un espacio
     * @param espacioId ID del espacio
     * @param fecha fecha actual
     * @return lista de reservas de hoy
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.espacioId = :espacioId AND r.fecha = :fecha AND r.estado = 'approved' ORDER BY r.horaInicio")
    List<clsEntidadReserva> findReservasDelDia(@Param("espacioId") String espacioId, @Param("fecha") LocalDate fecha);

    /**
     * Verifica si hay conflicto de horarios para una reserva
     * @param espacioId ID del espacio
     * @param fecha fecha de la reserva
     * @param horaInicio hora de inicio
     * @param horaFin hora de fin
     * @return cantidad de reservas que se solapan
     */
    @Query("SELECT COUNT(r) FROM clsEntidadReserva r WHERE " +
           "r.espacioId = :espacioId AND r.fecha = :fecha AND r.estado = 'approved' AND " +
           "((r.horaInicio < :horaFin AND r.horaFin > :horaInicio))")
    long verificarConflictoHorario(
        @Param("espacioId") String espacioId,
        @Param("fecha") LocalDate fecha,
        @Param("horaInicio") LocalTime horaInicio,
        @Param("horaFin") LocalTime horaFin
    );

    /**
     * Cuenta reservas por estado
     * @param estado estado a contar
     * @return cantidad de reservas
     */
    long countByEstado(String estado);

    /**
     * Cuenta reservas de un usuario
     * @param usuarioId ID del usuario
     * @return cantidad de reservas
     */
    long countByUsuarioId(String usuarioId);

    /**
     * Cuenta reservas de un espacio
     * @param espacioId ID del espacio
     * @return cantidad de reservas
     */
    long countByEspacioId(String espacioId);

    /**
     * Busca las últimas N reservas de un usuario
     * @param usuarioId ID del usuario
     * @return lista de las últimas reservas
     */
    @Query(value = "SELECT * FROM reservas WHERE usuario_id = :usuarioId ORDER BY created_at DESC", nativeQuery = true)
    //@Query(value = "SELECT * FROM reservas WHERE usuario_id = :usuarioId ORDER BY created_at DESC LIMIT :limite", nativeQuery = true)
    List<clsEntidadReserva> findUltimasReservasPorUsuario(@Param("usuarioId") String usuarioId, @Param("limite") int limite);
}
