package com.integraupt.entidad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class clsEntidadUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdUsuario;

    @Column(nullable = false, length = 30)
    private String Nombre;

    @Column(nullable = false, length = 30)
    private String Apellido;

    @Column(nullable = false, length = 20)
    private String CodigoU;

    @Column(nullable = false, length = 30)
    private String CorreoU;

    @Column(nullable = false, length = 255)
    private String Password;

    @Column(nullable = false)
    private Integer Rol;

    @Column(nullable = false)
    private Integer Facultad;

    @Column(nullable = false)
    private Integer Escuela;

    @Column(nullable = false)
    private Integer Estado;

    // Getters y Setters
    public Integer getIdUsuario() { return IdUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.IdUsuario = idUsuario; }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { this.Nombre = nombre; }

    public String getApellido() { return Apellido; }
    public void setApellido(String apellido) { this.Apellido = apellido; }

    public String getCodigoU() { return CodigoU; }
    public void setCodigoU(String codigoU) { this.CodigoU = codigoU; }

    public String getCorreoU() { return CorreoU; }
    public void setCorreoU(String correoU) { this.CorreoU = correoU; }

    public String getPassword() { return Password; }
    public void setPassword(String password) { this.Password = password; }

    public Integer getRol() { return Rol; }
    public void setRol(Integer rol) { this.Rol = rol; }

    public Integer getFacultad() { return Facultad; }
    public void setFacultad(Integer facultad) { this.Facultad = facultad; }

    public Integer getEscuela() { return Escuela; }
    public void setEscuela(Integer escuela) { this.Escuela = escuela; }

    public Integer getEstado() { return Estado; }
    public void setEstado(Integer estado) { this.Estado = estado; }
}
