package com.example.transportcompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.transportcompany.models.Factura;

public interface FacturaRepository extends JpaRepository<Factura,Long> {

}
