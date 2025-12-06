package com.example.transportcompany.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private float precioUnitario;
    private int stock;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idAlmacen", nullable = true)
    private Almacen almacen;
}
