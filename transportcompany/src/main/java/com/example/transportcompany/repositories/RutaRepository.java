package com.example.transportcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transportcompany.models.Ruta;

public interface RutaRepository extends JpaRepository<Ruta,Long>{
    
}
