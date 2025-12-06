package com.example.transportcompany.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFactura;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idPedido", referencedColumnName = "idPedido")
    @JsonManagedReference
    private Pedido pedido;

    private LocalDate fechaEmision;

    private float subtotal;

    private String metodoPago;

}

/*
 * Tu caso: Pedido → Factura (Pedido es más fuerte)
 * Quieres que Factura dependa de Pedido → la tabla factura tendrá la columna
 * id_pedido.
 * Entonces la anotación va en Factura:
 * 
 * @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
 * 
 * @JoinColumn(name = "id_pedido", referencedColumnName = "id_pedido")
 * private Pedido pedido;
 */