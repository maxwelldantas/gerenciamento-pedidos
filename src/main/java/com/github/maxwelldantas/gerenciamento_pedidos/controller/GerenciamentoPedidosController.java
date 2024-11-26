package com.github.maxwelldantas.gerenciamento_pedidos.controller;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.PedidoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.PedidoResponseDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class GerenciamentoPedidosController {

	private final OrderService orderService;

	/**
	 * Integração disponibilizada para o Produto Externo A para recebimento dos pedidos.
	 *
	 * @param pedidoRequestDTO corpo da requisição em JSON com o pedido.
	 * @return 200 OK.
	 */
	@PostMapping
	public ResponseEntity<Void> receberPedidos(@RequestBody PedidoRequestDTO pedidoRequestDTO) {
		orderService.calcularValorTotalProdutos(pedidoRequestDTO);
		return ResponseEntity.ok().build();
	}

	/**
	 * Integração para o Produto Externo B consultar o pedido, seus produtos relacionados, status
	 * e valor total dos produtos.
	 *
	 * @param pedidoId ID do pedido.
	 * @return 200 OK. Resposta com corpo JSON do pedido.
	 */
	@GetMapping("{id}")
	public ResponseEntity<PedidoResponseDTO> consultarPedido(@PathVariable("id") Long pedidoId) {
		return ResponseEntity.ok().body(orderService.consultarPedido(pedidoId));
	}
}
