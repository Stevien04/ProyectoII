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
 * @version 1.0.1
 */
@Repository
public interface clsRepositorioReserva extends JpaRepository<clsEntidadReserva, String> {

    // -------------------- CONSULTAS BÁSICAS --------------------

    /**
     * Busca reservas por usuario
     */
    List<clsEntidadReserva> findByUsuarioId(String usuarioId);

    /**
     * Busca reservas por espacio
     */
    List<clsEntidadReserva> findByEspacioId(String espacioId);

    /**
     * Busca reservas por estado
     */
    List<clsEntidadReserva> findByEstado(String estado);

    /**
     * Busca reservas por fecha
     */
    List<clsEntidadReserva> findByFecha(LocalDate fecha);

    /**
     * Busca reservas por usuario y estado
     */
    List<clsEntidadReserva> findByUsuarioIdAndEstado(String usuarioId, String estado);

    /**
     * Busca reservas por espacio y estado
     */
    List<clsEntidadReserva> findByEspacioIdAndEstado(String espacioId, String estado);

    // -------------------- CONSULTAS PERSONALIZADAS --------------------

    /**
     * Busca reservas pendientes (ordenadas por fecha)
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.estado = 'pending' ORDER BY r.fecha, r.horaInicio")
    List<clsEntidadReserva> findReservasPendientes();

    /**
     * Busca reservas aprobadas de un usuario
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.usuarioId = :usuarioId AND r.estado = 'approved' ORDER BY r.fecha DESC, r.horaInicio")
    List<clsEntidadReserva> findReservasAprobadasPorUsuario(@Param("usuarioId") String usuarioId);

    /**
     * Busca reservas futuras de un usuario
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.usuarioId = :usuarioId AND r.fecha >= :fechaActual ORDER BY r.fecha, r.horaInicio")
    List<clsEntidadReserva> findReservasFuturas(@Param("usuarioId") String usuarioId, @Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca reservas del día para un espacio
     */
    @Query("SELECT r FROM clsEntidadReserva r WHERE r.espacioId = :espacioId AND r.fecha = :fecha AND r.estado = 'approved' ORDER BY r.horaInicio")
    List<clsEntidadReserva> findReservasDelDia(@Param("espacioId") String espacioId, @Param("fecha") LocalDate fecha);

    /**
     * Verifica si hay conflicto de horarios para una reserva
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

    // -------------------- MÉTODOS DE CONTEO --------------------

    long countByEstado(String estado);
    long countByUsuarioId(String usuarioId);
    long countByEspacioId(String espacioId);

    // -------------------- ÚLTIMAS RESERVAS --------------------

    /**
     * Busca las últimas N reservas de un usuario
     */
    @Query(value = "SELECT * FROM reservas WHERE usuario_id = :usuarioId ORDER BY created_at DESC LIMIT :limite", nativeQuery = true)
    List<clsEntidadReserva> findUltimasReservasPorUsuario(
            @Param("usuarioId") String usuarioId,
            @Param("limite") int limite);
}
