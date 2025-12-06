package com.example.transportcompany.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.transportcompany.models.Cliente;
import com.example.transportcompany.models.Pedido;
import com.example.transportcompany.repositories.ClienteRepository;
import com.example.transportcompany.repositories.PedidoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MenuController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public String menuPrincipal() {
        return "menu";
    }

    @GetMapping("/exportar/csv")
    public void exportarCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=pedidos.csv");

        List<Pedido> pedidos = pedidoRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        PrintWriter writer = response.getWriter();
        writer.println("Pedidos");
        writer.println("ID,Cliente,Estado,Fecha Pedido,Fecha Entrega");

        for (Pedido p : pedidos) {
            writer.println(p.getIdPedido() + "," + p.getCliente().getName() + "," + p.getEstadoPedido() + "," +
                    p.getFechaPedido() + "," + p.getFechaEntrega());
        }

        writer.println("Clientes");
        writer.println("ID,DNI,Nombre,Email Direccion,Telefono");

        for (Cliente c : clientes) {
            writer.println(c.getIdCliente() + "," + c.getDni() + "," + c.getName() + "," + c.getEmail() + ","
                    + c.getDireccion() + "," + c.getTelefono());
        }
    }

}