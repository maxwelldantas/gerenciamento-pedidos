package com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
		Long id,
		List<ProdutoResponseDTO> produtos,
		BigDecimal valorTotalProdutos,
		StatusPedidoEnum status
) {
}
