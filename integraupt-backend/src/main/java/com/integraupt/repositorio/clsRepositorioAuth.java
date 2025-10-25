package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los perfiles de usuario registrados.
 */
@Repository
public interface clsRepositorioAuth extends JpaRepository<clsEntidadUsuario, String> {

    Optional<clsEntidadUsuario> findFirstByCodigoIgnoreCase(String codigo);

    Optional<clsEntidadUsuario> findFirstByEmailIgnoreCase(String email);

    Optional<clsEntidadUsuario> findFirstByTipoLoginIgnoreCaseAndCodigoIgnoreCase(String tipoLogin, String codigo);

    Optional<clsEntidadUsuario> findFirstByTipoLoginIgnoreCaseAndEmailIgnoreCase(String tipoLogin, String email);
}
