package com.example.transportcompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.transportcompany.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByAlmacenIdAlmacen(int idAlmacen);
}
