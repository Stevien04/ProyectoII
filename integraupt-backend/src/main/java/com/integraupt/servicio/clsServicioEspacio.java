package com.integraupt.servicio;

import com.integraupt.entidad.clsEntidadEspacio;
import com.integraupt.repositorio.clsRepositorioEspacio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de espacios (aulas y laboratorios)
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Service
@Transactional
public class clsServicioEspacio {

    @Autowired
    private clsRepositorioEspacio repositorioEspacio;

    /**
     * Obtiene todos los espacios
     * @return lista de espacios
     */
    public List<clsEntidadEspacio> obtenerTodos() {
        return repositorioEspacio.findAll();
    }

    /**
     * Obtiene un espacio por ID
     * @param id ID del espacio
     * @return Optional con el espacio
     */
    public Optional<clsEntidadEspacio> obtenerPorId(String id) {
        return repositorioEspacio.findById(id);
    }

    /**
     * Obtiene espacios por tipo
     * @param tipo tipo de espacio (Aula, Laboratorio)
     * @return lista de espacios
     */
    public List<clsEntidadEspacio> obtenerPorTipo(String tipo) {
        return repositorioEspacio.findByTipo(tipo);
    }

    /**
     * Obtiene espacios por facultad
     * @param facultad facultad
     * @return lista de espacios
     */
    public List<clsEntidadEspacio> obtenerPorFacultad(String facultad) {
        return repositorioEspacio.findByFacultad(facultad);
    }

    /**
     * Obtiene espacios disponibles
     * @return lista de espacios disponibles
     */
    public List<clsEntidadEspacio> obtenerDisponibles() {
        return repositorioEspacio.findEspaciosDisponibles();
    }

    /**
     * Obtiene laboratorios disponibles
     * @return lista de laboratorios disponibles
     */
    public List<clsEntidadEspacio> obtenerLaboratoriosDisponibles() {
        return repositorioEspacio.findLaboratoriosDisponibles();
    }

    /**
     * Obtiene aulas disponibles
     * @return lista de aulas disponibles
     */
    public List<clsEntidadEspacio> obtenerAulasDisponibles() {
        return repositorioEspacio.findAulasDisponibles();
    }

    /**
     * Busca espacios por nombre
     * @param termino término de búsqueda
     * @return lista de espacios
     */
    public List<clsEntidadEspacio> buscarPorNombre(String termino) {
        return repositorioEspacio.buscarPorNombre(termino);
    }

    /**
     * Crea un nuevo espacio
     * @param espacio entidad a crear
     * @return espacio creado
     */
    public clsEntidadEspacio crear(clsEntidadEspacio espacio) {
        return repositorioEspacio.save(espacio);
    }

    /**
     * Actualiza un espacio existente
     * @param id ID del espacio
     * @param espacio datos actualizados
     * @return espacio actualizado
     */
    public Optional<clsEntidadEspacio> actualizar(String id, clsEntidadEspacio espacio) {
        return repositorioEspacio.findById(id)
                .map(espacioExistente -> {
                    if (espacio.getNombre() != null) espacioExistente.setNombre(espacio.getNombre());
                    if (espacio.getTipo() != null) espacioExistente.setTipo(espacio.getTipo());
                    if (espacio.getFacultad() != null) espacioExistente.setFacultad(espacio.getFacultad());
                    if (espacio.getEscuela() != null) espacioExistente.setEscuela(espacio.getEscuela());
                    if (espacio.getUbicacion() != null) espacioExistente.setUbicacion(espacio.getUbicacion());
                    if (espacio.getCapacidad() != null) espacioExistente.setCapacidad(espacio.getCapacidad());
                    if (espacio.getRecursos() != null) espacioExistente.setRecursos(espacio.getRecursos());
                    if (espacio.getEstado() != null) espacioExistente.setEstado(espacio.getEstado());
                    
                    return repositorioEspacio.save(espacioExistente);
                });
    }

    /**
     * Elimina un espacio
     * @param id ID del espacio a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(String id) {
        if (repositorioEspacio.existsById(id)) {
            repositorioEspacio.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Cuenta espacios por tipo
     * @param tipo tipo a contar
     * @return cantidad de espacios
     */
    public long contarPorTipo(String tipo) {
        return repositorioEspacio.countByTipo(tipo);
    }

    /**
     * Cuenta espacios por estado
     * @param estado estado a contar
     * @return cantidad de espacios
     */
    public long contarPorEstado(String estado) {
        return repositorioEspacio.countByEstado(estado);
    }
}
