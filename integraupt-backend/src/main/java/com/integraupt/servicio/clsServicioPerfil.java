package com.integraupt.servicio;

import com.integraupt.dto.clsDTOPerfilResponse;
import com.integraupt.entidad.clsEntidadPerfil;
import com.integraupt.repositorio.clsRepositorioPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de perfiles de usuario
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Service
@Transactional
public class clsServicioPerfil {

    @Autowired
    private clsRepositorioPerfil repositorioPerfil;

    /**
     * Obtiene todos los perfiles
     * @return lista de perfiles DTO
     */
    public List<clsDTOPerfilResponse> obtenerTodos() {
        return repositorioPerfil.findAll()
                .stream()
                .map(clsDTOPerfilResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un perfil por ID
     * @param id ID del perfil
     * @return Optional con el perfil DTO
     */
    public Optional<clsDTOPerfilResponse> obtenerPorId(String id) {
        return repositorioPerfil.findById(id)
                .map(clsDTOPerfilResponse::new);
    }

    /**
     * Obtiene un perfil por código
     * @param codigo código del usuario
     * @return Optional con el perfil DTO
     */
    public Optional<clsDTOPerfilResponse> obtenerPorCodigo(String codigo) {
        return repositorioPerfil.findByCodigo(codigo)
                .map(clsDTOPerfilResponse::new);
    }

    /**
     * Obtiene un perfil por email
     * @param email email del usuario
     * @return Optional con el perfil DTO
     */
    public Optional<clsDTOPerfilResponse> obtenerPorEmail(String email) {
        return repositorioPerfil.findByEmail(email)
                .map(clsDTOPerfilResponse::new);
    }

    /**
     * Obtiene perfiles por rol
     * @param rol rol a buscar
     * @return lista de perfiles DTO
     */
    public List<clsDTOPerfilResponse> obtenerPorRol(String rol) {
        return repositorioPerfil.findByRol(rol)
                .stream()
                .map(clsDTOPerfilResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene perfiles por facultad
     * @param facultad facultad a buscar
     * @return lista de perfiles DTO
     */
    public List<clsDTOPerfilResponse> obtenerPorFacultad(String facultad) {
        return repositorioPerfil.findByFacultad(facultad)
                .stream()
                .map(clsDTOPerfilResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca perfiles por nombre o apellido
     * @param termino término de búsqueda
     * @return lista de perfiles DTO
     */
    public List<clsDTOPerfilResponse> buscarPorNombre(String termino) {
        return repositorioPerfil.buscarPorNombreOApellido(termino)
                .stream()
                .map(clsDTOPerfilResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo perfil
     * @param perfil entidad a crear
     * @return perfil creado DTO
     */
    public clsDTOPerfilResponse crear(clsEntidadPerfil perfil) {
        // TODO: Encriptar password con BCrypt antes de guardar
        clsEntidadPerfil perfilGuardado = repositorioPerfil.save(perfil);
        return new clsDTOPerfilResponse(perfilGuardado);
    }

    /**
     * Actualiza un perfil existente
     * @param id ID del perfil
     * @param perfil datos actualizados
     * @return perfil actualizado DTO
     */
    public Optional<clsDTOPerfilResponse> actualizar(String id, clsEntidadPerfil perfil) {
        return repositorioPerfil.findById(id)
                .map(perfilExistente -> {
                    // Actualizar solo campos no nulos
                    if (perfil.getNombres() != null) perfilExistente.setNombres(perfil.getNombres());
                    if (perfil.getApellidos() != null) perfilExistente.setApellidos(perfil.getApellidos());
                    if (perfil.getEmail() != null) perfilExistente.setEmail(perfil.getEmail());
                    if (perfil.getCelular() != null) perfilExistente.setCelular(perfil.getCelular());
                    if (perfil.getFacultad() != null) perfilExistente.setFacultad(perfil.getFacultad());
                    if (perfil.getEscuela() != null) perfilExistente.setEscuela(perfil.getEscuela());
                    if (perfil.getEstado() != null) perfilExistente.setEstado(perfil.getEstado());
                    
                    clsEntidadPerfil perfilActualizado = repositorioPerfil.save(perfilExistente);
                    return new clsDTOPerfilResponse(perfilActualizado);
                });
    }

    /**
     * Elimina un perfil
     * @param id ID del perfil a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(String id) {
        if (repositorioPerfil.existsById(id)) {
            repositorioPerfil.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Verifica si existe un perfil con ese código
     * @param codigo código a verificar
     * @return true si existe
     */
    public boolean existePorCodigo(String codigo) {
        return repositorioPerfil.existsByCodigo(codigo);
    }

    /**
     * Verifica si existe un perfil con ese email
     * @param email email a verificar
     * @return true si existe
     */
    public boolean existePorEmail(String email) {
        return repositorioPerfil.existsByEmail(email);
    }

    /**
     * Cuenta perfiles por rol
     * @param rol rol a contar
     * @return cantidad de perfiles
     */
    public long contarPorRol(String rol) {
        return repositorioPerfil.countByRol(rol);
    }
}
