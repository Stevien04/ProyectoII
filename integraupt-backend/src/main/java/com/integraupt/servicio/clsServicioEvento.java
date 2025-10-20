package com.integraupt.servicio;

import com.integraupt.entidad.clsEntidadEvento;
import com.integraupt.repositorio.clsRepositorioEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestión de eventos institucionales
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Service
@Transactional
public class clsServicioEvento {

    @Autowired
    private clsRepositorioEvento repositorioEvento;

    /**
     * Obtiene todos los eventos
     * @return lista de eventos
     */
    public List<clsEntidadEvento> obtenerTodos() {
        return repositorioEvento.findAll();
    }

    /**
     * Obtiene un evento por ID
     * @param id ID del evento
     * @return Optional con el evento
     */
    public Optional<clsEntidadEvento> obtenerPorId(String id) {
        return repositorioEvento.findById(id);
    }

    /**
     * Obtiene eventos activos
     * @return lista de eventos activos
     */
    public List<clsEntidadEvento> obtenerActivos() {
        return repositorioEvento.findEventosActivos();
    }

    /**
     * Obtiene eventos próximos
     * @return lista de eventos próximos
     */
    public List<clsEntidadEvento> obtenerProximos() {
        return repositorioEvento.findEventosProximos(LocalDateTime.now());
    }

    /**
     * Obtiene eventos del día actual
     * @return lista de eventos de hoy
     */
    public List<clsEntidadEvento> obtenerEventosDeHoy() {
        LocalDateTime inicioDia = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime finDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return repositorioEvento.findEventosDelDia(inicioDia, finDia);
    }

    /**
     * Obtiene eventos en un rango de fechas
     * @param inicio fecha de inicio
     * @param fin fecha de fin
     * @return lista de eventos en ese rango
     */
    public List<clsEntidadEvento> obtenerEnRango(LocalDateTime inicio, LocalDateTime fin) {
        return repositorioEvento.findEventosEnRango(inicio, fin);
    }

    /**
     * Busca eventos por título
     * @param termino término de búsqueda
     * @return lista de eventos
     */
    public List<clsEntidadEvento> buscarPorTitulo(String termino) {
        return repositorioEvento.buscarPorTitulo(termino);
    }

    /**
     * Obtiene eventos por tipo
     * @param tipo tipo de evento
     * @return lista de eventos
     */
    public List<clsEntidadEvento> obtenerPorTipo(String tipo) {
        return repositorioEvento.findByTipo(tipo);
    }

    /**
     * Crea un nuevo evento
     * @param evento entidad a crear
     * @return evento creado
     */
    public clsEntidadEvento crear(clsEntidadEvento evento) {
        return repositorioEvento.save(evento);
    }

    /**
     * Actualiza un evento existente
     * @param id ID del evento
     * @param evento datos actualizados
     * @return evento actualizado
     */
    public Optional<clsEntidadEvento> actualizar(String id, clsEntidadEvento evento) {
        return repositorioEvento.findById(id)
                .map(eventoExistente -> {
                    if (evento.getTitulo() != null) eventoExistente.setTitulo(evento.getTitulo());
                    if (evento.getDescripcion() != null) eventoExistente.setDescripcion(evento.getDescripcion());
                    if (evento.getFechaInicio() != null) eventoExistente.setFechaInicio(evento.getFechaInicio());
                    if (evento.getFechaFin() != null) eventoExistente.setFechaFin(evento.getFechaFin());
                    if (evento.getUbicacion() != null) eventoExistente.setUbicacion(evento.getUbicacion());
                    if (evento.getOrganizador() != null) eventoExistente.setOrganizador(evento.getOrganizador());
                    if (evento.getImagenUrl() != null) eventoExistente.setImagenUrl(evento.getImagenUrl());
                    if (evento.getTipo() != null) eventoExistente.setTipo(evento.getTipo());
                    if (evento.getEstado() != null) eventoExistente.setEstado(evento.getEstado());
                    
                    return repositorioEvento.save(eventoExistente);
                });
    }

    /**
     * Elimina un evento
     * @param id ID del evento a eliminar
     * @return true si se eliminó correctamente
     */
    public boolean eliminar(String id) {
        if (repositorioEvento.existsById(id)) {
            repositorioEvento.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Cuenta eventos por estado
     * @param estado estado a contar
     * @return cantidad de eventos
     */
    public long contarPorEstado(String estado) {
        return repositorioEvento.countByEstado(estado);
    }

    /**
     * Cuenta eventos por tipo
     * @param tipo tipo a contar
     * @return cantidad de eventos
     */
    public long contarPorTipo(String tipo) {
        return repositorioEvento.countByTipo(tipo);
    }
}
