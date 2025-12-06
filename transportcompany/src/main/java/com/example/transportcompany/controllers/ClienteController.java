package com.example.transportcompany.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.transportcompany.models.Cliente;
import com.example.transportcompany.models.Pedido;
import com.example.transportcompany.repositories.ClienteRepository;
import com.example.transportcompany.repositories.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @GetMapping
    public String listClientes(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Cliente> clientePage = clienteRepository.findAll(pageable);
        model.addAttribute("clientes", clientePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientePage.getTotalPages());
        return "listaClientes";
    }

    @GetMapping("/nuevo")
    public String newCliente(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "nuevocliente";
    }

    @PostMapping("/crear")
    public String createCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteRepository.save(cliente);
        logger.info("Cliente creado: {}", cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editPedido(@PathVariable Long id, Model model) {
        if (!clienteRepository.existsById(id)) {
            model.addAttribute("error", "El cliente no existe");
            return "redirect:/clientes";
        }
        Cliente cliente = clienteRepository.findById(id).get();
        model.addAttribute("cliente", cliente);
        return "editarCliente";
    }

    @PostMapping("/modificar")
    public String modifyCliente(@ModelAttribute("cliente") Cliente cliente) {
        clienteRepository.save(cliente);
        logger.info("Cliente modificado: {}", cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/info/{id}")
    public String infoCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).get();
        model.addAttribute("cliente", cliente);
        List<Pedido> pedidos = pedidoRepository.findByClienteIdCliente(cliente.getIdCliente());
        model.addAttribute("pedidos", pedidos);
        return "mostrarCliente";
    }

    @GetMapping("/eliminar/{id}")
    public String removePedido(@PathVariable Long id, RedirectAttributes redAttrib) {
        if (!clienteRepository.existsById(id))
            redAttrib.addFlashAttribute("error", "El cliente no existe");
        else {
            clienteRepository.deleteById(id);
            logger.info("Cliente eliminado con id {}", id);
            redAttrib.addFlashAttribute("success", "Se ha borrado correctamente el cliente con id " + id);
        }
        return "redirect:/clientes";
    }
}
