package com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @param produtos
 */
public record PedidoRequestDTO(
		@NotEmpty
		List<ProdutoRequestDTO> produtos,
		@NotNull
		Integer status
) {
}
