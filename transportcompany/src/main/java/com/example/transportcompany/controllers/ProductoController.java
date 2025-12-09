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

import com.example.transportcompany.models.Producto;
import com.example.transportcompany.repositories.AlmacenRepository;
import com.example.transportcompany.repositories.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    // LISTADO
    @GetMapping
    public String listProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "listaProductos";
    }

    // NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String newProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("almacenes", almacenRepository.findAll());
        return "nuevoProducto";
    }

    // CREAR
    @PostMapping("/crear")
    public String createProducto(@ModelAttribute("producto") Producto producto) {

        // Si no selecciona almacén, poner null
        if (producto.getAlmacen() != null && producto.getAlmacen().getIdAlmacen() == 0) {
            producto.setAlmacen(null);
        }

        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // EDITAR
    @GetMapping("/editar/{id}")
    public String editProducto(@PathVariable Long id, Model model, RedirectAttributes redAttrib) {

        if (!productoRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El producto no existe");
            return "redirect:/productos";
        }

        Producto producto = productoRepository.findById(id).get();
        model.addAttribute("producto", producto);
        model.addAttribute("almacenes", almacenRepository.findAll());

        return "editarProducto";
    }

    // MODIFICAR
    @PostMapping("/modificar")
    public String modifyProducto(@ModelAttribute("producto") Producto producto) {

        if (producto.getAlmacen() != null && producto.getAlmacen().getIdAlmacen() == 0) {
            producto.setAlmacen(null);
        }

        productoRepository.save(producto);
        return "redirect:/productos";
    }

    // INFO
    @GetMapping("/info/{id}")
    public String infoProducto(@PathVariable Long id, Model model) {

        Producto producto = productoRepository.findById(id).get();
        model.addAttribute("producto", producto);

        return "mostrarProducto";
    }

    // ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String removeProducto(@PathVariable Long id, RedirectAttributes redAttrib) {

        if (!productoRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El producto no existe");
        } else {
            productoRepository.deleteById(id);
            redAttrib.addFlashAttribute("success", "Se ha borrado correctamente el producto con id " + id);
        }

        return "redirect:/productos";
    }
}
