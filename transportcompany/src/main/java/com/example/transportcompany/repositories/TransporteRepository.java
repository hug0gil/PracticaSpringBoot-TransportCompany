package com.example.transportcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transportcompany.models.Transporte;

public interface TransporteRepository extends JpaRepository<Transporte, Long> {
}
