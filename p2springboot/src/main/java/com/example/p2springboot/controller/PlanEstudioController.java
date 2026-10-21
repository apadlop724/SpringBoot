package com.example.p2springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PlanEstudioController {

    @GetMapping("/")
    public String verPlanEstudio(
            @RequestParam(name = "nombre", required = false, defaultValue = "Estudiante") String nombre,
            Model model) {

        // 1. Datos del estudiante y título
        model.addAttribute("nombreEstudiante", nombre);
        model.addAttribute("tituloPlan", "Plan de Estudio Diario");

        // 2. Listas paralelas de asignaturas y duraciones (en minutos)
        List<String> asignaturas = List.of("Programación", "Bases de datos", "Lenguajes de marcas");
        List<Integer> duraciones = List.of(25, 40, 30);

        model.addAttribute("asignaturas", asignaturas);
        model.addAttribute("duraciones", duraciones);

        // 3. Valor decimal de ejemplo para probar la utilidad #numbers.formatDecimal
        double totalHorasEstimadas = 1.5833;
        model.addAttribute("totalHoras", totalHorasEstimadas);

        // Devuelve el nombre de la plantilla HTML (plan-estudio.html)
        return "plan-estudio";
    }
}
