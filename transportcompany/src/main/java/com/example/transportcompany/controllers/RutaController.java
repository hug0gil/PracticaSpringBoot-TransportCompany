package com.example.transportcompany.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.transportcompany.models.Pedido;
import com.example.transportcompany.models.Ruta;
import com.example.transportcompany.repositories.PedidoRepository;
import com.example.transportcompany.repositories.RutaRepository;

@Controller
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private static final Logger logger = LoggerFactory.getLogger(RutaController.class);

    // LISTADO
    @GetMapping
    public String listRutas(Model model) {
        List<Ruta> rutas = rutaRepository.findAll();
        model.addAttribute("rutas", rutas);
        return "listaRutas";
    }

    // NUEVA RUTA
    @GetMapping("/nuevo")
    public String newRuta(Model model) {
        model.addAttribute("ruta", new Ruta());
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "nuevaRuta";
    }

    // CREAR
    @PostMapping("/crear")
    public String createRuta(@ModelAttribute("ruta") Ruta ruta, @RequestParam(required = false) List<Long> pedidoIds) {
        if (pedidoIds != null) {
            List<Pedido> pedidos = pedidoRepository.findAllById(pedidoIds);
            ruta.setPedidos(pedidos);
        }
        rutaRepository.save(ruta);
        logger.info("Ruta creada: {}", ruta);
        return "redirect:/rutas";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editRuta(@PathVariable int id, Model model, RedirectAttributes redAttrib) {
        if (!rutaRepository.existsById((long) id)) {
            redAttrib.addFlashAttribute("error", "La ruta no existe");
            return "redirect:/rutas";
        }

        Ruta ruta = rutaRepository.findById((long) id).get();
        model.addAttribute("ruta", ruta);
        model.addAttribute("pedidos", pedidoRepository.findAll());
        return "editarRuta";
    }

    // MODIFICAR
    @PostMapping("/modificar")
    public String modifyRuta(@ModelAttribute("ruta") Ruta ruta, @RequestParam(required = false) List<Long> pedidoIds) {
        if (pedidoIds != null) {
            List<Pedido> pedidos = pedidoRepository.findAllById(pedidoIds);
            ruta.setPedidos(pedidos);
        }
        rutaRepository.save(ruta);
        logger.info("Ruta modificada: {}", ruta);
        return "redirect:/rutas";
    }

    // INFO
    @GetMapping("/info/{id}")
    public String infoRuta(@PathVariable int id, Model model, RedirectAttributes redAttrib) {
        if (!rutaRepository.existsById((long) id)) {
            redAttrib.addFlashAttribute("error", "La ruta no existe");
            return "redirect:/rutas";
        }

        Ruta ruta = rutaRepository.findById((long) id).get();
        List<Pedido> pedidos = ruta.getPedidos();

        model.addAttribute("ruta", ruta);
        model.addAttribute("pedidos", pedidos);
        return "mostrarRuta";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String removeRuta(@PathVariable int id, RedirectAttributes redAttrib) {
        if (!rutaRepository.existsById((long) id)) {
            redAttrib.addFlashAttribute("error", "La ruta no existe");
        } else {
            rutaRepository.deleteById((long) id);
            logger.info("Ruta eliminada con id {}", id);
            redAttrib.addFlashAttribute("success", "Se ha borrado correctamente la ruta con id " + id);
        }
        return "redirect:/rutas";
    }
}
