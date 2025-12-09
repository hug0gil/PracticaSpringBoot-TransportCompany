package com.example.transportcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transportcompany.models.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}
