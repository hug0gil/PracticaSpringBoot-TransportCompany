package com.example.transportcompany.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConductor;

    private String nombre;
    private String licencia;
    private int telefono;
    private String direccion;

    @ManyToMany(mappedBy = "conductores")
    private List<Vehiculo> vehiculos = new ArrayList<>();
}
