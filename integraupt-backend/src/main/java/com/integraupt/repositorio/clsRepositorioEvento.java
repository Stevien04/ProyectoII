package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la entidad Evento
 * Proporciona acceso a datos de eventos institucionales
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Repository
public interface clsRepositorioEvento extends JpaRepository<clsEntidadEvento, String> {

    /**
     * Busca eventos por estado
     * @param estado estado del evento (Activo, Cancelado, Finalizado)
     * @return lista de eventos con ese estado
     */
    List<clsEntidadEvento> findByEstado(String estado);

    /**
     * Busca eventos por tipo
     * @param tipo tipo de evento (Conferencia, Taller, Seminario, etc.)
     * @return lista de eventos de ese tipo
     */
    List<clsEntidadEvento> findByTipo(String tipo);

    /**
     * Busca eventos activos ordenados por fecha
     * @return lista de eventos activos
     */
    @Query("SELECT e FROM clsEntidadEvento e WHERE e.estado = 'Activo' ORDER BY e.fechaInicio")
    List<clsEntidadEvento> findEventosActivos();

    /**
     * Busca eventos próximos (activos y con fecha futura)
     * @param fechaActual fecha actual
     * @return lista de eventos próximos
     */
    @Query("SELECT e FROM clsEntidadEvento e WHERE e.estado = 'Activo' AND e.fechaInicio >= :fechaActual ORDER BY e.fechaInicio")
    List<clsEntidadEvento> findEventosProximos(@Param("fechaActual") LocalDateTime fechaActual);

    /**
     * Busca eventos del día actual
     * @param inicio inicio del día
     * @param fin fin del día
     * @return lista de eventos de hoy
     */
    @Query("SELECT e FROM clsEntidadEvento e WHERE e.fechaInicio >= :inicio AND e.fechaInicio < :fin AND e.estado = 'Activo' ORDER BY e.fechaInicio")
    List<clsEntidadEvento> findEventosDelDia(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    /**
     * Busca eventos en un rango de fechas
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de eventos en ese rango
     */
    @Query("SELECT e FROM clsEntidadEvento e WHERE e.fechaInicio BETWEEN :inicio AND :fin ORDER BY e.fechaInicio")
    List<clsEntidadEvento> findEventosEnRango(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    /**
     * Busca eventos por título (búsqueda parcial)
     * @param termino término de búsqueda
     * @return lista de eventos que coinciden
     */
    @Query("SELECT e FROM clsEntidadEvento e WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :termino, '%')) ORDER BY e.fechaInicio")
    List<clsEntidadEvento> buscarPorTitulo(@Param("termino") String termino);

    /**
     * Cuenta eventos por estado
     * @param estado estado a contar
     * @return cantidad de eventos
     */
    long countByEstado(String estado);

    /**
     * Cuenta eventos por tipo
     * @param tipo tipo a contar
     * @return cantidad de eventos
     */
    long countByTipo(String tipo);
}
