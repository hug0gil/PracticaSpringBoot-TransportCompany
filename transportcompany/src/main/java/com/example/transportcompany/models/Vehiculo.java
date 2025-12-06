package com.example.transportcompany.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_transporte", nullable = true)
    private Transporte transporte;

    private String matricula;
    private String modelo;
    private int anyo;
    private int capacidad;

    @ManyToMany
    @JoinTable(
        name = "Conductor_has_Vehiculo",
        joinColumns = @JoinColumn(name = "idVehiculo"),
        inverseJoinColumns = @JoinColumn(name = "idConductor")
    )
    private List<Conductor> conductores = new ArrayList<>();
}
