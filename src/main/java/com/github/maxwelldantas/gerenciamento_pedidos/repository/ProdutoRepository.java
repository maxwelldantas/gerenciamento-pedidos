package com.github.maxwelldantas.gerenciamento_pedidos.repository;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
