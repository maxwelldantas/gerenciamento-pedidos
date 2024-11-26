package com.github.maxwelldantas.gerenciamento_pedidos.domain.service.impl;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.PedidoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.ProdutoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.PedidoResponseDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.ProdutoResponseDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.enums.StatusPedidoEnum;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Pedido;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Produto;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.service.OrderService;
import com.github.maxwelldantas.gerenciamento_pedidos.repository.PedidoRepository;
import com.github.maxwelldantas.gerenciamento_pedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final PedidoRepository pedidoRepository;
	private final ProdutoRepository produtoRepository;

	@Override
	@Transactional
	public void calcularValorTotalProdutos(PedidoRequestDTO pedidoRequestDTO) {
		Set<String> listaProdutosNomesUnicos = new HashSet<>();
		for (ProdutoRequestDTO produtoRequestDTO : pedidoRequestDTO.produtos()) {
			if (produtoRequestDTO.quantidade() <= 0) {
				throw new RuntimeException("Produto não pode ter quantidade igual a 0 ou menor!");
			}
			listaProdutosNomesUnicos.add(produtoRequestDTO.nome());
		}

		if (pedidoRequestDTO.produtos().size() != listaProdutosNomesUnicos.size()) {
			throw new RuntimeException("Existem produtos com nome duplicado no pedido!");
		}

		List<Produto> produtos = new ArrayList<>();
		BigDecimal valorTotalPedido = new BigDecimal(0);

		for (ProdutoRequestDTO produtoRequestDTO : pedidoRequestDTO.produtos()) {
			Produto produto = produtoRequestDTOParaProduto(produtoRequestDTO);
			produtoRepository.save(produto);
			produtos.add(produto);

			BigDecimal valorTotalProduto = BigDecimal.valueOf(
					produtoRequestDTO.preco() * produtoRequestDTO.quantidade());
			valorTotalPedido = valorTotalPedido.add(valorTotalProduto);
		}

		Pedido pedido = converterParaPedido(pedidoRequestDTO, produtos, valorTotalPedido);
		pedidoRepository.save(pedido);
	}

	@Override
	public PedidoResponseDTO consultarPedido(Long pedidoId) {
		Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);
		return pedido.map(p -> new PedidoResponseDTO(
				p.getId(),
				p.getProdutos().stream().map(this::produtoParaProdutoResponseDTO).toList(),
				p.getValorTotalProdutos(),
				p.getStatus()
		)).orElse(null);
	}

	private Produto produtoRequestDTOParaProduto(ProdutoRequestDTO produtoRequestDTO) {
		return Produto.builder()
				.nome(produtoRequestDTO.nome())
				.descricao(produtoRequestDTO.descricao())
				.quantidade(produtoRequestDTO.quantidade())
				.preco(produtoRequestDTO.preco())
				.build();
	}

	private Pedido converterParaPedido(PedidoRequestDTO pedidoRequestDTO,
									   List<Produto> produtos,
									   BigDecimal valorTotalPedido) {
		return Pedido.builder()
				.produtos(produtos)
				.valorTotalProdutos(valorTotalPedido)
				.status(StatusPedidoEnum.codigoStatus(pedidoRequestDTO.status()))
				.build();
	}

	private ProdutoResponseDTO produtoParaProdutoResponseDTO(Produto produto) {
		return new ProdutoResponseDTO(produto.getId(), produto.getNome(), produto.getDescricao(),
				produto.getQuantidade(), produto.getPreco());
	}
}
