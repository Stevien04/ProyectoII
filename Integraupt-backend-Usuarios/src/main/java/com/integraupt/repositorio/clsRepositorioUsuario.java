package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio especializado para las operaciones del módulo de usuarios.
 */
@Repository
public interface clsRepositorioUsuario extends JpaRepository<clsEntidadUsuario, Integer> {

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Integer id);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Integer id);

    boolean existsByNumeroDocumentoIgnoreCase(String numeroDocumento);

    boolean existsByNumeroDocumentoIgnoreCaseAndIdNot(String numeroDocumento, Integer id);
}