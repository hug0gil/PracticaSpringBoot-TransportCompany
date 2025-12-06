package com.example.transportcompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.transportcompany.models.Factura;
import com.example.transportcompany.repositories.FacturaRepository;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping("/info/{id}")
    public String infoCliente(@PathVariable Long id, Model model) {

        Factura factura = facturaRepository.findById(id).get();
        model.addAttribute("factura", factura);


        return "mostrarFactura";
    }

}
