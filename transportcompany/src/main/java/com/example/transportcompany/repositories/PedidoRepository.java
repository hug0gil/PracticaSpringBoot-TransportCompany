package com.example.transportcompany.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.transportcompany.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findAll(Pageable pageable);

    List<Pedido> findByClienteIdCliente(Long id);

    List<Pedido> findByRutasIdRutaIn(List<Long> idRutas);

    List<Pedido> findByEstadoPedidoAndClienteName(String estadoPedido, String nombreCliente);

    List<Pedido> findByFechaPedidoBetweenAndEstadoPedido(LocalDate inicio, LocalDate fin, String estadoPedido);

    List<Pedido> findByEstadoPedidoOrderByFechaEntregaDesc(String estadoPedido);

    long countByEstadoPedido(String estadoPedido);

    Pedido findTopByClienteIdClienteOrderByFechaPedidoDesc(Long idCliente);

    void deleteByEstadoPedido(String estado);

    void deleteByFechaPedidoBefore(LocalDate fecha);

    List<Pedido> findByEstadoPedidoAndFechaPedidoBetween(String estado, LocalDate inicio, LocalDate fin);

    @Query("SELECT p FROM Pedido p " +
            "WHERE (:nombre IS NULL OR LOWER(p.cliente.name) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:estado IS NULL OR p.estadoPedido = :estado)")
    List<Pedido> buscarPorClienteYEstado(@Param("nombre") String nombre, @Param("estado") String estado);

    @Query("SELECT p.estadoPedido, COUNT(p) FROM Pedido p GROUP BY p.estadoPedido")
    List<Object[]> contarPedidosPorEstado();
}
