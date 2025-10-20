package com.integraupt.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Entidad que representa un espacio físico (aula o laboratorio)
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Entity
@Table(name = "espacios")
public class clsEntidadEspacio {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @NotBlank(message = "El nombre del espacio es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @NotBlank(message = "El tipo es obligatorio")
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo; // Aula o Laboratorio

    @NotBlank(message = "La facultad es obligatoria")
    @Column(name = "facultad", length = 100, nullable = false)
    private String facultad;

    @NotBlank(message = "La escuela es obligatoria")
    @Column(name = "escuela", length = 100, nullable = false)
    private String escuela;

    @Column(name = "ubicacion", length = 200)
    private String ubicacion;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(name = "capacidad")
    private Integer capacidad;

    @Column(name = "recursos", columnDefinition = "TEXT")
    private String recursos;

    @Column(name = "estado", length = 50)
    private String estado = "Disponible";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsEntidadEspacio() {
        this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public clsEntidadEspacio(String nombre, String tipo, String facultad, String escuela) {
        this();
        this.nombre = nombre;
        this.tipo = tipo;
        this.facultad = facultad;
        this.escuela = escuela;
    }

    // ========================================
    // MÉTODOS DEL CICLO DE VIDA JPA
    // ========================================

    @PrePersist
    protected void onCreate() {
        if (this.id == null || this.id.isEmpty()) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ========================================
    // GETTERS Y SETTERS
    // ========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ========================================
    // MÉTODOS ÚTILES
    // ========================================

    /**
     * Verifica si el espacio está disponible
     * @return true si está disponible
     */
    public boolean estaDisponible() {
        return "Disponible".equalsIgnoreCase(this.estado);
    }

    /**
     * Verifica si es un laboratorio
     * @return true si es laboratorio
     */
    public boolean esLaboratorio() {
        return "Laboratorio".equalsIgnoreCase(this.tipo);
    }

    /**
     * Verifica si es un aula
     * @return true si es aula
     */
    public boolean esAula() {
        return "Aula".equalsIgnoreCase(this.tipo);
    }

    @Override
    public String toString() {
        return "Espacio{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", facultad='" + facultad + '\'' +
                ", escuela='" + escuela + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", capacidad=" + capacidad +
                ", estado='" + estado + '\'' +
                '}';
    }
}
