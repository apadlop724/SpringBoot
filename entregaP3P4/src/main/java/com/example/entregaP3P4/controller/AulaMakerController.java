package com.example.entregaP3P4.controller;

import com.example.entregaP3P4.dto.SolicitudPrestamoForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

@Controller
public class AulaMakerController {

    // Catálogo en memoria estructurado como array bidimensional: [codigo, nombre, descripcion]
    private final String[][] catalogoMateriales = {
            {"PORTATIL", "Ordenador portátil", "Incluye un cargador y una funda."},
            {"MICRO", "Kit de microcontrolador", "Incluye una placa y un cable USB."},
            {"CAMARA", "Cámara de documentación", "Incluye un soporte de mesa."}
    };

    // Método auxiliar para buscar un material por su código en el array
    private String[] buscarMaterialPorCodigo(String codigo) {
        for (String[] material : catalogoMateriales) {
            if (material[0].equalsIgnoreCase(codigo)) {
                return material;
            }
        }
        return null; // Devuelve null si no existe
    }

    // 1. Ruta GET /: Muestra el catálogo de recursos
    @GetMapping("/")
    public String mostrarCatalogo(Model model) {
        model.addAttribute("materiales", catalogoMateriales);
        return "catalogo";
    }

    // 2. Ruta GET /materiales/{codigo}: Muestra la ficha individual con parámetro opcional 'turno'
    @GetMapping("/materiales/{codigo}")
    public String mostrarFicha(
            @PathVariable String codigo,
            @RequestParam(name = "turno", required = false, defaultValue = "general") String turno,
            Model model) {

        String[] materialEncontrado = buscarMaterialPorCodigo(codigo);

        // Si no existe, lanzamos una excepción personalizada o manejamos el 404
        if (materialEncontrado == null) {
            throw new RecursoNoEncontradoException("El material con código " + codigo + " no existe.");
        }

        model.addAttribute("codigo", materialEncontrado[0]);
        model.addAttribute("nombre", materialEncontrado[1]);
        model.addAttribute("descripcion", materialEncontrado[2]);
        model.addAttribute("turno", turno);

        return "ficha";
    }

    // 3. Ruta GET /solicitud/nueva: Muestra el formulario de préstamo
    @GetMapping("/solicitud/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitudPrestamoForm", new SolicitudPrestamoForm());
        model.addAttribute("materiales", catalogoMateriales);
        return "formulario";
    }

    // 4. Ruta POST /solicitudes: Procesa el formulario, valida y calcula los días
    @PostMapping("/solicitudes")
    public String procesarSolicitud(
            @Valid @ModelAttribute("solicitudPrestamoForm") SolicitudPrestamoForm form,
            BindingResult bindingResult,
            Model model) {

        // Validación personalizada cruzada: la fecha de devolución no puede ser anterior a la recogida
        if (form.getFechaRecogida() != null && form.getFechaDevolucion() != null) {
            if (form.getFechaDevolucion().isBefore(form.getFechaRecogida())) {
                bindingResult.rejectValue("fechaDevolucion", "error.fechaDevolucion",
                        "La fecha de devolución no puede ser anterior a la fecha de recogida.");
            }
        }

        // Validación adicional: asegurar que el material seleccionado existe en el catálogo
        if (form.getMaterial() != null && !form.getMaterial().isEmpty()) {
            if (buscarMaterialPorCodigo(form.getMaterial()) == null) {
                bindingResult.rejectValue("material", "error.material", "El material seleccionado no es válido.");
            }
        }

        // Si hay errores de validación, se devuelve el formulario conservando datos y opciones
        if (bindingResult.hasErrors()) {
            model.addAttribute("materiales", catalogoMateriales);
            return "formulario";
        }

        // Lógica de cálculo auxiliar para días de préstamo y días-unidad
        long diasPrestamo = ChronoUnit.DAYS.between(form.getFechaRecogida(), form.getFechaDevolucion()) + 1;
        long diasUnidad = diasPrestamo * form.getUnidades();

        // Pasar resultados a la vista de resumen
        model.addAttribute("solicitud", form);
        model.addAttribute("diasPrestamo", diasPrestamo);
        model.addAttribute("diasUnidad", diasUnidad);

        return "resultado";
    }
}

// Excepción auxiliar para activar el estado HTTP 404
@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}