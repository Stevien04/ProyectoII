package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Perfil
 * Proporciona acceso a datos de usuarios
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Repository
public interface clsRepositorioPerfil extends JpaRepository<clsEntidadPerfil, String> {

    /**
     * Busca un perfil por código de estudiante/docente
     * @param codigo código del usuario
     * @return Optional con el perfil si existe
     */
    Optional<clsEntidadPerfil> findByCodigo(String codigo);

    /**
     * Busca un perfil por email
     * @param email email del usuario
     * @return Optional con el perfil si existe
     */
    Optional<clsEntidadPerfil> findByEmail(String email);

    /**
     * Busca un perfil por código o email (útil para login)
     * @param codigo código del usuario
     * @param email email del usuario
     * @return Optional con el perfil si existe
     */
    @Query("SELECT p FROM clsEntidadPerfil p WHERE p.codigo = :codigo OR p.email = :email")
    Optional<clsEntidadPerfil> findByCodigoOrEmail(@Param("codigo") String codigo, @Param("email") String email);

    /**
     * Busca perfiles por rol
     * @param rol rol a buscar (Estudiante, Docente, Administrador)
     * @return lista de perfiles con ese rol
     */
    List<clsEntidadPerfil> findByRol(String rol);

    /**
     * Busca perfiles por facultad
     * @param facultad facultad a buscar
     * @return lista de perfiles de esa facultad
     */
    List<clsEntidadPerfil> findByFacultad(String facultad);

    /**
     * Busca perfiles por escuela
     * @param escuela escuela a buscar
     * @return lista de perfiles de esa escuela
     */
    List<clsEntidadPerfil> findByEscuela(String escuela);

    /**
     * Busca perfiles por estado
     * @param estado estado a buscar (Activo, Inactivo)
     * @return lista de perfiles con ese estado
     */
    List<clsEntidadPerfil> findByEstado(String estado);

    /**
     * Busca perfiles por tipo de login
     * @param tipoLogin tipo de login (academico, administrativo)
     * @return lista de perfiles con ese tipo
     */
    List<clsEntidadPerfil> findByTipoLogin(String tipoLogin);

    /**
     * Busca perfiles activos por rol
     * @param rol rol a buscar
     * @param estado estado (Activo)
     * @return lista de perfiles activos con ese rol
     */
    List<clsEntidadPerfil> findByRolAndEstado(String rol, String estado);

    /**
     * Busca perfiles por nombre o apellido (búsqueda parcial)
     * @param termino término de búsqueda
     * @return lista de perfiles que coinciden
     */
    @Query("SELECT p FROM clsEntidadPerfil p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<clsEntidadPerfil> buscarPorNombreOApellido(@Param("termino") String termino);

    /**
     * Verifica si existe un perfil con ese código
     * @param codigo código a verificar
     * @return true si existe
     */
    boolean existsByCodigo(String codigo);

    /**
     * Verifica si existe un perfil con ese email
     * @param email email a verificar
     * @return true si existe
     */
    boolean existsByEmail(String email);

    /**
     * Cuenta perfiles por rol
     * @param rol rol a contar
     * @return cantidad de perfiles
     */
    long countByRol(String rol);

    /**
     * Cuenta perfiles por facultad
     * @param facultad facultad a contar
     * @return cantidad de perfiles
     */
    long countByFacultad(String facultad);
}
