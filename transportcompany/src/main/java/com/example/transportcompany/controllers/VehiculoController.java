package com.example.transportcompany.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.transportcompany.models.Conductor;
import com.example.transportcompany.models.Transporte;
import com.example.transportcompany.models.Vehiculo;
import com.example.transportcompany.repositories.ConductorRepository;
import com.example.transportcompany.repositories.TransporteRepository;
import com.example.transportcompany.repositories.VehiculoRepository;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    // LISTADO
    @GetMapping
    public String listarVehiculos(Model model) {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        model.addAttribute("vehiculos", vehiculos);
        return "listaVehiculos";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevoVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("transportes", transporteRepository.findAll());
        model.addAttribute("conductores", conductorRepository.findAll());
        return "nuevoVehiculo";
    }

    // CREAR
    @PostMapping("/crear")
    public String crearVehiculo(@ModelAttribute Vehiculo vehiculo,
            @RequestParam(required = false) List<Long> conductorIds) {
        if (conductorIds != null) {
            List<Conductor> conductores = conductorRepository.findAllById(conductorIds);
            vehiculo.setConductores(conductores);
        }
        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/editar/{id}")
    public String editarVehiculoForm(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow();
        List<Transporte> transportes = transporteRepository.findAll();
        List<Conductor> conductores = conductorRepository.findAll();
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("transportes", transportes);
        model.addAttribute("conductores", conductores);
        return "editarVehiculo";
    }

    @PostMapping("/modificar")
    public String modificarVehiculo(@ModelAttribute Vehiculo vehiculo,
            @RequestParam(required = false) List<Long> conductorIds) {
        if (conductorIds != null) {
            List<Conductor> conductores = conductorRepository.findAllById(conductorIds);
            vehiculo.setConductores(conductores);
        } else {
            vehiculo.setConductores(new ArrayList<>()); // si no se selecciona ninguno
        }
        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos";
    }

    // MOSTRAR
    @GetMapping("/info/{id}")
    public String mostrarVehiculo(@PathVariable Long id, Model model) {
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);
        if (vehiculoOpt.isEmpty()) {
            return "redirect:/vehiculos";
        }
        model.addAttribute("vehiculo", vehiculoOpt.get());
        return "mostrarVehiculo";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoRepository.deleteById(id);
        return "redirect:/vehiculos";
    }
}
