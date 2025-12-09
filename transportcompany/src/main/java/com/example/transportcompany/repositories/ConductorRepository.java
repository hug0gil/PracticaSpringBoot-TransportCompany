package com.example.transportcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transportcompany.models.Conductor;

public interface ConductorRepository extends JpaRepository<Conductor, Long> {
}
