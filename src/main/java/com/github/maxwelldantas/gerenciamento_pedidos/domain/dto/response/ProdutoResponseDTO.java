package com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response;

public record ProdutoResponseDTO(
		Long id,
		String nome,
		String descricao,
		Long quantidade,
		Double preco
) {
}
