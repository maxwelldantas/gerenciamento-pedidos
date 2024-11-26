package com.github.maxwelldantas.gerenciamento_pedidos.repository;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
