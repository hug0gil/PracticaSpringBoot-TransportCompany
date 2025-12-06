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
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransporte;

    private String tipoTransporte;
    private int capacidad;
    private String estadoTransporte;

    @OneToMany(mappedBy = "transporte", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos = new ArrayList<>();
}
