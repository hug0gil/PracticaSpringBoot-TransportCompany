package com.example.transportcompany.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Almacen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlmacen;
    private String nombre;
    private String ubicacion;
    private int capacidadMaxima;

    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
    private List<Producto> productos = new ArrayList<>();

    @Override
    public String toString() {
        return "Almacen{id=" + idAlmacen + ", nombre='" + nombre + "'}";
    }

}
