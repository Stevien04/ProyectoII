package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadEspacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Espacio
 * Proporciona acceso a datos de espacios (aulas y laboratorios)
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Repository
public interface clsRepositorioEspacio extends JpaRepository<clsEntidadEspacio, String> {

    /**
     * Busca espacios por tipo
     * @param tipo tipo de espacio (Aula, Laboratorio)
     * @return lista de espacios de ese tipo
     */
    List<clsEntidadEspacio> findByTipo(String tipo);

    /**
     * Busca espacios por facultad
     * @param facultad facultad a buscar
     * @return lista de espacios de esa facultad
     */
    List<clsEntidadEspacio> findByFacultad(String facultad);

    /**
     * Busca espacios por escuela
     * @param escuela escuela a buscar
     * @return lista de espacios de esa escuela
     */
    List<clsEntidadEspacio> findByEscuela(String escuela);

    /**
     * Busca espacios por estado
     * @param estado estado a buscar (Disponible, En Mantenimiento)
     * @return lista de espacios con ese estado
     */
    List<clsEntidadEspacio> findByEstado(String estado);

    /**
     * Busca espacios por tipo y estado
     * @param tipo tipo de espacio
     * @param estado estado del espacio
     * @return lista de espacios que coinciden
     */
    List<clsEntidadEspacio> findByTipoAndEstado(String tipo, String estado);

    /**
     * Busca espacios por facultad y estado
     * @param facultad facultad a buscar
     * @param estado estado del espacio
     * @return lista de espacios que coinciden
     */
    List<clsEntidadEspacio> findByFacultadAndEstado(String facultad, String estado);

    /**
     * Busca espacios disponibles
     * @return lista de espacios disponibles
     */
    @Query("SELECT e FROM clsEntidadEspacio e WHERE e.estado = 'Disponible' ORDER BY e.tipo, e.nombre")
    List<clsEntidadEspacio> findEspaciosDisponibles();

    /**
     * Busca espacios por nombre (búsqueda parcial)
     * @param termino término de búsqueda
     * @return lista de espacios que coinciden
     */
    @Query("SELECT e FROM clsEntidadEspacio e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<clsEntidadEspacio> buscarPorNombre(@Param("termino") String termino);

    /**
     * Busca laboratorios disponibles
     * @return lista de laboratorios disponibles
     */
    @Query("SELECT e FROM clsEntidadEspacio e WHERE e.tipo = 'Laboratorio' AND e.estado = 'Disponible' ORDER BY e.nombre")
    List<clsEntidadEspacio> findLaboratoriosDisponibles();

    /**
     * Busca aulas disponibles
     * @return lista de aulas disponibles
     */
    @Query("SELECT e FROM clsEntidadEspacio e WHERE e.tipo = 'Aula' AND e.estado = 'Disponible' ORDER BY e.nombre")
    List<clsEntidadEspacio> findAulasDisponibles();

    /**
     * Cuenta espacios por tipo
     * @param tipo tipo a contar
     * @return cantidad de espacios
     */
    long countByTipo(String tipo);

    /**
     * Cuenta espacios por estado
     * @param estado estado a contar
     * @return cantidad de espacios
     */
    long countByEstado(String estado);
}
