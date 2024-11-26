package com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDTO(
		@NotNull
		String nome,
		@NotNull
		String descricao,
		@NotNull
		Long quantidade,
		@NotNull
		Double preco
) {
}
