package com.example.entregaP3P4.dto;


import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public class SolicitudPrestamoForm {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 60, message = "El nombre no puede superar los 60 caracteres.")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El formato del correo electrónico no es válido.")
    @Size(max = 120, message = "El correo no puede superar los 120 caracteres.")
    private String correo;

    @NotBlank(message = "Debe seleccionar un material.")
    private String material;

    @NotNull(message = "Las unidades son obligatorias.")
    @Min(value = 1, message = "Debe solicitar al menos 1 unidad.")
    @Max(value = 3, message = "No se pueden solicitar más de 3 unidades.")
    private Integer unidades;

    @NotNull(message = "La fecha de recogida es obligatoria.")
    @FutureOrPresent(message = "La fecha de recogida debe ser hoy o una fecha futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaRecogida;

    @NotNull(message = "La fecha de devolución es obligatoria.")
    @FutureOrPresent(message = "La fecha de devolución debe ser hoy o una fecha futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaDevolucion;

    @NotBlank(message = "El motivo del préstamo es obligatorio.")
    @Size(min = 30, max = 400, message = "El motivo debe tener entre 30 y 400 caracteres.")
    private String motivo;

    @AssertTrue(message = "Debe aceptar las normas del aula maker para continuar.")
    private Boolean aceptaNormas;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public Integer getUnidades() { return unidades; }
    public void setUnidades(Integer unidades) { this.unidades = unidades; }

    public LocalDate getFechaRecogida() { return fechaRecogida; }
    public void setFechaRecogida(LocalDate fechaRecogida) { this.fechaRecogida = fechaRecogida; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public Boolean getAceptaNormas() { return aceptaNormas; }
    public void setAceptaNormas(Boolean aceptaNormas) { this.aceptaNormas = aceptaNormas; }
}
