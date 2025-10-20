package com.integraupt.repositorio;

import com.integraupt.entidad.clsEntidadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface clsRepositorioAuth extends JpaRepository<clsEntidadUsuario, Integer> {

    /**
     * Busca un usuario por código o correo institucional
     */
    @Query("SELECT u FROM clsEntidadUsuario u " +
            "WHERE u.CodigoU = :codigoOEmail OR u.CorreoU = :codigoOEmail")
    Optional<clsEntidadUsuario> findByCodigoOEmail(@Param("codigoOEmail") String codigoOEmail);

    /**
     * Alternativas más simples (útiles en el futuro)
     */
    Optional<clsEntidadUsuario> findByCodigoU(String codigoU);

    Optional<clsEntidadUsuario> findByCorreoU(String correoU);
}
