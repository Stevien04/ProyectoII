package com.integraupt.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entidad que representa una reserva de espacio
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Entity
@Table(name = "reservas")
public class clsEntidadReserva {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "espacio_id", length = 36)
    private String espacioId;

    @Column(name = "usuario_id", length = 36)
    private String usuarioId;

    @NotBlank(message = "El tipo de reserva es obligatorio")
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @NotNull(message = "La fecha es obligatoria")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(name = "ciclo", length = 10)
    private String ciclo;

    @Column(name = "curso", length = 200)
    private String curso;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "estado", length = 50)
    private String estado = "pending";

    @Column(name = "motivo_rechazo", columnDefinition = "TEXT")
    private String motivoRechazo;

    @Column(name = "aprobado_por", length = 36)
    private String aprobadoPor;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relaciones (opcional, pero útil para obtener objetos relacionados)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "espacio_id", referencedColumnName = "id", insertable = false, updatable = false)
    private clsEntidadEspacio espacio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", insertable = false, updatable = false)
    private clsEntidadPerfil usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aprobado_por", referencedColumnName = "id", insertable = false, updatable = false)
    private clsEntidadPerfil aprobador;

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsEntidadReserva() {
        this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public clsEntidadReserva(String espacioId, String usuarioId, String tipo, LocalDate fecha, 
                            LocalTime horaInicio, LocalTime horaFin) {
        this();
        this.espacioId = espacioId;
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

    public String getEspacioId() {
        return espacioId;
    }

    public void setEspacioId(String espacioId) {
        this.espacioId = espacioId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(String aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
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

    public clsEntidadEspacio getEspacio() {
        return espacio;
    }

    public void setEspacio(clsEntidadEspacio espacio) {
        this.espacio = espacio;
    }

    public clsEntidadPerfil getUsuario() {
        return usuario;
    }

    public void setUsuario(clsEntidadPerfil usuario) {
        this.usuario = usuario;
    }

    public clsEntidadPerfil getAprobador() {
        return aprobador;
    }

    public void setAprobador(clsEntidadPerfil aprobador) {
        this.aprobador = aprobador;
    }

    // ========================================
    // MÉTODOS ÚTILES
    // ========================================

    /**
     * Verifica si la reserva está pendiente
     * @return true si está pendiente
     */
    public boolean estaPendiente() {
        return "pending".equalsIgnoreCase(this.estado);
    }

    /**
     * Verifica si la reserva está aprobada
     * @return true si está aprobada
     */
    public boolean estaAprobada() {
        return "approved".equalsIgnoreCase(this.estado);
    }

    /**
     * Verifica si la reserva está cancelada
     * @return true si está cancelada
     */
    public boolean estaCancelada() {
        return "cancelled".equalsIgnoreCase(this.estado);
    }

    /**
     * Aprueba la reserva
     * @param aprobadorId ID del administrador que aprueba
     */
    public void aprobar(String aprobadorId) {
        this.estado = "approved";
        this.aprobadoPor = aprobadorId;
        this.motivoRechazo = null;
    }

    /**
     * Rechaza la reserva
     * @param aprobadorId ID del administrador que rechaza
     * @param motivo Motivo del rechazo
     */
    public void rechazar(String aprobadorId, String motivo) {
        this.estado = "cancelled";
        this.aprobadoPor = aprobadorId;
        this.motivoRechazo = motivo;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id='" + id + '\'' +
                ", espacioId='" + espacioId + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}
