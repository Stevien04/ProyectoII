package com.integraupt.entidad;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entidad que representa un registro de auditoría del sistema
 * 
 * @author IntegraUPT Team
 * @version 1.0.0
 */
@Entity
@Table(name = "auditoria")
public class clsEntidadAuditoria {

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "usuario_id", length = 36)
    private String usuarioId;

    @Column(name = "usuario_nombre", length = 200)
    private String usuarioNombre;

    @NotBlank(message = "La acción es obligatoria")
    @Column(name = "accion", length = 100, nullable = false)
    private String accion;

    @NotBlank(message = "El módulo es obligatorio")
    @Column(name = "modulo", length = 100, nullable = false)
    private String modulo;

    @Column(name = "estado", length = 50)
    private String estado;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "metadata", columnDefinition = "JSON")
    private String metadata;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ========================================
    // CONSTRUCTORES
    // ========================================

    public clsEntidadAuditoria() {
        this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    public clsEntidadAuditoria(String usuarioId, String usuarioNombre, String accion, String modulo) {
        this();
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.accion = accion;
        this.modulo = modulo;
        this.estado = "success";
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

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // ========================================
    // MÉTODOS ÚTILES
    // ========================================

    /**
     * Verifica si la acción fue exitosa
     * @return true si fue exitosa
     */
    public boolean fueExitosa() {
        return "success".equalsIgnoreCase(this.estado);
    }

    /**
     * Verifica si la acción falló
     * @return true si falló
     */
    public boolean fallo() {
        return "failed".equalsIgnoreCase(this.estado);
    }

    @Override
    public String toString() {
        return "Auditoria{" +
                "id='" + id + '\'' +
                ", usuarioNombre='" + usuarioNombre + '\'' +
                ", accion='" + accion + '\'' +
                ", modulo='" + modulo + '\'' +
                ", estado='" + estado + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
