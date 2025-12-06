package com.example.transportcompany.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.transportcompany.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Page<Cliente> findAll(Pageable pageable);

    List<Cliente> findByNameContainingAndDni(String name, String dni);

    List<Cliente> findByEmailAndTelefono(String email, int telefono);

    List<Cliente> findByNameOrderByNameAsc(String name);

    @Query("SELECT c FROM Cliente c WHERE c.name LIKE %:name%")
    List<Cliente> buscarPorName(@Param("name") String name);

    List<Cliente> findByDireccionContaining(String direccion);
}
