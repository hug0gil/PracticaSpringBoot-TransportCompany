package com.example.transportcompany.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.transportcompany.models.Transporte;
import com.example.transportcompany.repositories.TransporteRepository;

@Controller
@RequestMapping("/transportes")
public class TransporteController {

    @Autowired
    private TransporteRepository transporteRepository;

    // LISTADO
    @GetMapping
    public String listarTransportes(Model model) {
        List<Transporte> transportes = transporteRepository.findAll();
        model.addAttribute("transportes", transportes);
        return "listaTransportes";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoTransporte(Model model) {
        model.addAttribute("transporte", new Transporte());
        return "nuevoTransporte";
    }

    // CREAR
    @PostMapping("/crear")
    public String crearTransporte(@ModelAttribute Transporte transporte) {
        transporteRepository.save(transporte);
        return "redirect:/transportes";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editarTransporte(@PathVariable Long id, Model model) {
        Optional<Transporte> transporteOpt = transporteRepository.findById(id);
        if (transporteOpt.isEmpty()) {
            return "redirect:/transportes";
        }
        model.addAttribute("transporte", transporteOpt.get());
        return "editarTransporte";
    }

    // MODIFICAR
    @PostMapping("/modificar")
    public String modificarTransporte(@ModelAttribute Transporte transporte) {
        transporteRepository.save(transporte);
        return "redirect:/transportes";
    }

    // MOSTRAR
    @GetMapping("/info/{id}")
    public String mostrarTransporte(@PathVariable Long id, Model model) {
        Optional<Transporte> transporteOpt = transporteRepository.findById(id);
        if (transporteOpt.isEmpty()) {
            return "redirect:/transportes";
        }
        model.addAttribute("transporte", transporteOpt.get());
        return "mostrarTransporte";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarTransporte(@PathVariable Long id) {
        transporteRepository.deleteById(id);
        return "redirect:/transportes";
    }
}
