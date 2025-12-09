package com.example.transportcompany.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.transportcompany.models.Conductor;
import com.example.transportcompany.repositories.ConductorRepository;

@Controller
@RequestMapping("/conductores")
public class ConductorController {

    @Autowired
    private ConductorRepository conductorRepository;

    // LISTADO
    @GetMapping
    public String listarConductores(Model model) {
        List<Conductor> conductores = conductorRepository.findAll();
        model.addAttribute("conductores", conductores);
        return "listaConductores";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoConductor(Model model) {
        model.addAttribute("conductor", new Conductor());
        return "nuevoConductor";
    }

    // CREAR
    @PostMapping("/crear")
    public String crearConductor(@ModelAttribute Conductor conductor) {
        conductorRepository.save(conductor);
        return "redirect:/conductores";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarConductor(@PathVariable Long id, Model model) {
        Optional<Conductor> conductorOpt = conductorRepository.findById(id);
        if (conductorOpt.isEmpty()) {
            return "redirect:/conductores";
        }
        model.addAttribute("conductor", conductorOpt.get());
        return "editarConductor";
    }

    // MODIFICAR
    @PostMapping("/modificar")
    public String modificarConductor(@ModelAttribute Conductor conductor) {
        conductorRepository.save(conductor);
        return "redirect:/conductores";
    }

    // MOSTRAR
    @GetMapping("/info/{id}")
    public String mostrarConductor(@PathVariable Long id, Model model) {
        Optional<Conductor> conductorOpt = conductorRepository.findById(id);
        if (conductorOpt.isEmpty()) {
            return "redirect:/conductores";
        }
        model.addAttribute("conductor", conductorOpt.get());
        return "mostrarConductor";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarConductor(@PathVariable Long id) {
        conductorRepository.deleteById(id);
        return "redirect:/conductores";
    }
}
