package com.github.maxwelldantas.gerenciamento_pedidos.domain.service;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.PedidoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.PedidoResponseDTO;

public interface OrderService {
	void calcularValorTotalProdutos(PedidoRequestDTO pedidoRequestDTO);

	PedidoResponseDTO consultarPedido(Long pedidoId);
}
