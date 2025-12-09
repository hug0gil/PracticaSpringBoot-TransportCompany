package com.example.transportcompany.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transportcompany.models.Almacen;
import com.example.transportcompany.models.Producto;
import com.example.transportcompany.repositories.AlmacenRepository;
import com.example.transportcompany.repositories.ProductoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/almacenes")
public class AlmacenController {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private static final Logger logger = LoggerFactory.getLogger(AlmacenController.class);

    // LISTADO
    @GetMapping
    public String listAlmacenes(Model model) {
        List<Almacen> almacenes = almacenRepository.findAll();
        model.addAttribute("almacenes", almacenes);
        return "listaAlmacenes";
    }

    // NUEVO ALMACÉN
    @GetMapping("/nuevo")
    public String newAlmacen(Model model) {
        model.addAttribute("almacen", new Almacen());
        return "nuevoAlmacen";
    }

    // CREAR
    @PostMapping("/crear")
    public String createAlmacen(@ModelAttribute("almacen") Almacen almacen) {
        almacenRepository.save(almacen);
        logger.info("Almacén creado: {}", almacen);
        return "redirect:/almacenes";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editAlmacen(@PathVariable int id, Model model, RedirectAttributes redAttrib) {
        if (!almacenRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El almacén no existe");
            return "redirect:/almacenes";
        }

        Almacen almacen = almacenRepository.findById(id).get();
        model.addAttribute("almacen", almacen);
        return "editarAlmacen";
    }

    // MODIFICAR
    @PostMapping("/modificar")
    public String modifyAlmacen(@ModelAttribute("almacen") Almacen almacen) {
        almacenRepository.save(almacen);
        logger.info("Almacén modificado: {}", almacen);
        return "redirect:/almacenes";
    }

    // INFO
    @GetMapping("/info/{id}")
    public String infoAlmacen(@PathVariable int id, Model model) {
        Almacen almacen = almacenRepository.findById(id).get();
        List<Producto> productos = productoRepository.findByAlmacenIdAlmacen(id);

        model.addAttribute("almacen", almacen);
        model.addAttribute("productos", productos);

        return "mostrarAlmacen";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String removeAlmacen(@PathVariable int id, RedirectAttributes redAttrib) {
        if (!almacenRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El almacén no existe");
        } else {
            almacenRepository.deleteById(id);
            logger.info("Almacén eliminado con id {}", id);
            redAttrib.addFlashAttribute("success", "Se ha borrado correctamente el almacén con id " + id);
        }
        return "redirect:/almacenes";
    }
}
