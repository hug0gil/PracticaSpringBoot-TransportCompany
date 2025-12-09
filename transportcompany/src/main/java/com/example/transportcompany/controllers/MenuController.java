package com.example.transportcompany.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.transportcompany.models.Almacen;
import com.example.transportcompany.models.Cliente;
import com.example.transportcompany.models.Conductor;
import com.example.transportcompany.models.Factura;
import com.example.transportcompany.models.Pedido;
import com.example.transportcompany.models.Producto;
import com.example.transportcompany.models.Ruta;
import com.example.transportcompany.models.Transporte;
import com.example.transportcompany.models.Vehiculo;
import com.example.transportcompany.repositories.AlmacenRepository;
import com.example.transportcompany.repositories.ClienteRepository;
import com.example.transportcompany.repositories.ConductorRepository;
import com.example.transportcompany.repositories.FacturaRepository;
import com.example.transportcompany.repositories.PedidoRepository;
import com.example.transportcompany.repositories.ProductoRepository;
import com.example.transportcompany.repositories.RutaRepository;
import com.example.transportcompany.repositories.TransporteRepository;
import com.example.transportcompany.repositories.VehiculoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MenuController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping
    public String menuPrincipal() {
        return "menu";
    }

    @GetMapping("/exportar/csv")
    public void exportarCsvCompleto(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=export_completo.csv");

        PrintWriter writer = response.getWriter();

        java.util.function.Function<String, String> escapeCsv = value -> {
            if (value == null)
                return "";
            return "\"" + value.replace("\"", "\"\"") + "\"";
        };

        // --- Clientes ---
        writer.println("Clientes");
        writer.println("ID,DNI,Nombre,Email,Direccion,Telefono");
        for (Cliente c : clienteRepository.findAll()) {
            writer.println(c.getIdCliente() + "," +
                    escapeCsv.apply(c.getDni()) + "," +
                    escapeCsv.apply(c.getName()) + "," +
                    escapeCsv.apply(c.getEmail()) + "," +
                    escapeCsv.apply(c.getDireccion()) + "," +
                    c.getTelefono());
        }
        writer.println();

        // --- Pedidos ---
        writer.println("Pedidos");
        writer.println("ID,Cliente,Estado,Fecha Pedido,Fecha Entrega,Rutas,Factura");
        for (Pedido p : pedidoRepository.findAll()) {
            String clienteNombre = p.getCliente() != null ? p.getCliente().getName() : "";
            String estado = p.getEstadoPedido() != null ? p.getEstadoPedido() : "";
            String fechaPedido = p.getFechaPedido() != null ? p.getFechaPedido().toString() : "";
            String fechaEntrega = p.getFechaEntrega() != null ? p.getFechaEntrega().toString() : "";

            String rutas = p.getRutas().stream()
                    .map(r -> r.getOrigen() + " - " + r.getDestino())
                    .collect(Collectors.joining("; "));

            String factura = p.getFactura() != null ? "ID:" + p.getFactura().getIdFactura() +
                    ", Subtotal:" + p.getFactura().getSubtotal() + ", MetodoPago:" + p.getFactura().getMetodoPago()
                    : "";

            writer.println(
                    p.getIdPedido() + "," +
                            escapeCsv.apply(clienteNombre) + "," +
                            escapeCsv.apply(estado) + "," +
                            escapeCsv.apply(fechaPedido) + "," +
                            escapeCsv.apply(fechaEntrega) + "," +
                            escapeCsv.apply(rutas) + "," +
                            escapeCsv.apply(factura));
        }
        writer.println();

        // --- Rutas ---
        writer.println("Rutas");
        writer.println("ID,Origen,Destino,Distancia,TiempoEstimado");
        for (Ruta r : rutaRepository.findAll()) {
            writer.println(
                    r.getIdRuta() + "," +
                            escapeCsv.apply(r.getOrigen()) + "," +
                            escapeCsv.apply(r.getDestino()) + "," +
                            r.getDistancia() + "," +
                            r.getTiempoEstimado());
        }
        writer.println();

        // --- Almacenes ---
        writer.println("Almacenes");
        writer.println("ID,Nombre,Ubicacion,CapacidadMaxima");
        for (Almacen a : almacenRepository.findAll()) {
            writer.println(
                    a.getIdAlmacen() + "," +
                            escapeCsv.apply(a.getNombre()) + "," +
                            escapeCsv.apply(a.getUbicacion()) + "," +
                            a.getCapacidadMaxima());
        }
        writer.println();

        // --- Productos ---
        writer.println("Productos");
        writer.println("ID,Nombre,Descripcion,PrecioUnitario,Stock,Almacen");
        for (Producto p : productoRepository.findAll()) {
            String almacenNombre = p.getAlmacen() != null ? p.getAlmacen().getNombre() : "";
            writer.println(
                    p.getIdProducto() + "," +
                            escapeCsv.apply(p.getNombre()) + "," +
                            escapeCsv.apply(p.getDescripcion()) + "," +
                            p.getPrecioUnitario() + "," +
                            p.getStock() + "," +
                            escapeCsv.apply(almacenNombre));
        }
        writer.println();

        // --- Vehículos ---
        writer.println("Vehiculos");
        writer.println("ID,Matricula,Modelo,Anyo,Capacidad,Transporte,Conductores");
        for (Vehiculo v : vehiculoRepository.findAll()) {
            String transporteNombre = v.getTransporte() != null ? v.getTransporte().getTipoTransporte() : "";
            String conductores = v.getConductores().stream()
                    .map(Conductor::getNombre)
                    .collect(Collectors.joining("; "));
            writer.println(
                    v.getIdVehiculo() + "," +
                            escapeCsv.apply(v.getMatricula()) + "," +
                            escapeCsv.apply(v.getModelo()) + "," +
                            v.getAnyo() + "," +
                            v.getCapacidad() + "," +
                            escapeCsv.apply(transporteNombre) + "," +
                            escapeCsv.apply(conductores));
        }
        writer.println();

        // --- Conductores ---
        writer.println("Conductores");
        writer.println("ID,Nombre,Licencia,Telefono,Direccion");
        for (Conductor c : conductorRepository.findAll()) {
            writer.println(
                    c.getIdConductor() + "," +
                            escapeCsv.apply(c.getNombre()) + "," +
                            escapeCsv.apply(c.getLicencia()) + "," +
                            c.getTelefono() + "," +
                            escapeCsv.apply(c.getDireccion()));
        }
        writer.println();

        // --- Transportes ---
        writer.println("Transportes");
        writer.println("ID,TipoTransporte,Capacidad,EstadoTransporte");
        for (Transporte t : transporteRepository.findAll()) {
            writer.println(
                    t.getIdTransporte() + "," +
                            escapeCsv.apply(t.getTipoTransporte()) + "," +
                            t.getCapacidad() + "," +
                            escapeCsv.apply(t.getEstadoTransporte()));
        }
        writer.println();

        // --- Facturas ---
        writer.println("Facturas");
        writer.println("ID,Pedido,FechaEmision,Subtotal,MetodoPago");
        for (Factura f : facturaRepository.findAll()) {
            String pedidoId = f.getPedido() != null ? f.getPedido().getIdPedido().toString() : "";
            String fechaEmision = f.getFechaEmision() != null ? f.getFechaEmision().toString() : "";
            writer.println(
                    f.getIdFactura() + "," +
                            escapeCsv.apply(pedidoId) + "," +
                            escapeCsv.apply(fechaEmision) + "," +
                            f.getSubtotal() + "," +
                            escapeCsv.apply(f.getMetodoPago()));
        }

        writer.flush();
    }
}
