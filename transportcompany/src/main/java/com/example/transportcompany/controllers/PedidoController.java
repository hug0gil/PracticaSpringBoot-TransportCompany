package com.example.transportcompany.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.transportcompany.models.Cliente;
import com.example.transportcompany.models.Factura;
import com.example.transportcompany.models.Pedido;
import com.example.transportcompany.models.Ruta;
import com.example.transportcompany.repositories.ClienteRepository;
import com.example.transportcompany.repositories.FacturaRepository;
import com.example.transportcompany.repositories.PedidoRepository;
import com.example.transportcompany.repositories.RutaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private RutaRepository rutaRepository;
    @Autowired
    private FacturaRepository facturaRepository;

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @GetMapping
    public String listPedidos(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pedido> pedidoPage = pedidoRepository.findAll(pageable);
        model.addAttribute("pedidos", pedidoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pedidoPage.getTotalPages());
        model.addAttribute("totalItems", pedidoPage.getTotalElements());

        return "listaPedidos";
    }

    @GetMapping("/nuevo")
    public String newPedido(@RequestParam(value = "clienteId", required = false) Long clienteId, Model model) {
        Pedido pedido = new Pedido();

        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("rutas", rutaRepository.findAll());
        pedido.setRutas(rutaRepository.findAll());
        return "nuevoPedido";
    }

    @PostMapping("/crear")
    public String createPedido(@ModelAttribute Pedido pedido, @RequestParam Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);
        pedido.setCliente(cliente);
        List<Ruta> rutas = new ArrayList<>();
        for (Ruta r : pedido.getRutas()) {
            Ruta ruta = rutaRepository.findById(r.getIdRuta()).orElse(null);
            if (ruta != null)
                rutas.add(ruta);
        }
        pedido.setRutas(rutas);
        Factura factura = new Factura();
        factura.setPedido(pedido);
        factura.setFechaEmision(LocalDate.now());
        factura.setSubtotal(new Random().nextFloat() * 1000);
        factura.setMetodoPago("Tarjeta");
        pedido.setFactura(factura);
        facturaRepository.save(factura);
        pedidoRepository.save(pedido);
        logger.info("Pedido creado: {}", pedido);
        return "redirect:/pedidos";
    }

    @GetMapping("/editar/{id}")
    public String editPedido(@PathVariable Long id, Model model) {
        if (!pedidoRepository.existsById(id)) {
            model.addAttribute("error", "El pedido no existe");
            return "redirect:/pedidos";
        }
        Pedido pedido = pedidoRepository.findById(id).get();
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("rutas", rutaRepository.findAll());
        return "editarPedido";
    }

    @PostMapping("/modificar")
    public String modifyPedido(@ModelAttribute("pedido") Pedido pedido, @RequestParam Long clienteId) {

        Pedido pedidoOriginal = pedidoRepository.findById(pedido.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no existe"));

        pedidoOriginal.setCliente(clienteRepository.findById(clienteId).orElse(null));
        pedidoOriginal.setFechaPedido(pedido.getFechaPedido());
        pedidoOriginal.setFechaEntrega(pedido.getFechaEntrega());
        pedidoOriginal.setEstadoPedido(pedido.getEstadoPedido());

        List<Ruta> rutasActualizadas = new ArrayList<>();
        if (pedido.getRutas() != null) {
            for (Ruta r : pedido.getRutas()) {
                rutaRepository.findById(r.getIdRuta()).ifPresent(rutasActualizadas::add);
            }
        }
        pedidoOriginal.setRutas(rutasActualizadas);

        pedidoRepository.save(pedidoOriginal);
        logger.info("Pedido modificado: {}", pedidoOriginal);

        return "redirect:/pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String removePedido(@PathVariable Long id, RedirectAttributes redAttrib) {
        if (!pedidoRepository.existsById(id)) {
            redAttrib.addFlashAttribute("error", "El pedido no existe");
        } else {
            pedidoRepository.deleteById(id);
            logger.info("Pedido eliminado con id {}", id);
            redAttrib.addFlashAttribute("success", "Se ha borrado correctamente el pedido con id " + id);
        }
        return "redirect:/pedidos";
    }

    @GetMapping("/info/{id}")
    public String infoPedido(@PathVariable Long id, Model model) {
        Pedido pedido = pedidoRepository.findById(id).get();
        model.addAttribute("pedido", pedido);
        model.addAttribute("rutas", rutaRepository.findAll());
        return "mostrarPedido";
    }

    @GetMapping("/buscar")
    public String mostrarFormularioBusqueda() {
        return "buscarPedido";
    }

    @PostMapping("/buscar")
    public String buscarPedidos(@RequestParam(required = false) String estado,
            @RequestParam(required = false) String clienteNombre,
            Model model) {

        if (clienteNombre != null && clienteNombre.isBlank())
            clienteNombre = null;
        if (estado != null && estado.isBlank())
            estado = null;

        List<Pedido> resultados = pedidoRepository.buscarPorClienteYEstado(clienteNombre, estado);
        model.addAttribute("pedidos", resultados);
        model.addAttribute("currentPage", 0); 
        model.addAttribute("totalPages", 1); 
        model.addAttribute("totalItems", resultados.size());
        return "listaPedidos";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        List<Object[]> stats = pedidoRepository.contarPedidosPorEstado();
        model.addAttribute("estadisticas", stats);
        return "estadisticasPedidos";
    }

}
