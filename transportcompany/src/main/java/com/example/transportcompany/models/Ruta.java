package com.example.transportcompany.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRuta;

    @ManyToMany(mappedBy = "rutas")
    @JsonManagedReference
    private List<Pedido> pedidos = new ArrayList<>();

    private String origen;
    private String destino;
    private float distancia;
    private float tiempoEstimado;

    @Override
    public String toString() {
        return "Ruta{idRuta=" + idRuta +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", distancia=" + distancia +
                ", tiempoEstimado=" + tiempoEstimado +
                '}';
    }
}
