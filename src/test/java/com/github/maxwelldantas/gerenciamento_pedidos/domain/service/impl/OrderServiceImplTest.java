package com.github.maxwelldantas.gerenciamento_pedidos.domain.service.impl;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.PedidoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.ProdutoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.PedidoResponseDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.enums.StatusPedidoEnum;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Pedido;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.model.Produto;
import com.github.maxwelldantas.gerenciamento_pedidos.exception.BusinessException;
import com.github.maxwelldantas.gerenciamento_pedidos.repository.PedidoRepository;
import com.github.maxwelldantas.gerenciamento_pedidos.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderServiceImplTest {

	@InjectMocks
	private OrderServiceImpl orderService;
	@Mock
	private ProdutoRepository produtoRepository;
	@Mock
	private PedidoRepository pedidoRepository;

	@BeforeEach
	void setUp() throws Exception {
		openMocks(this).close();
	}

	@Test
	void deveCalcularValorTotalProdutosSucesso() {
		when(produtoRepository.save(any())).thenReturn(massaProduto());

		orderService.calcularValorTotalProdutos(massaPedidoRequestDTO(45L));

		verify(produtoRepository, times(2)).save(any());
	}

	@Test
	void deveCalcularValorTotalProdutosPedidoLancarExcecaoStatusInexistente() {
		when(produtoRepository.save(any())).thenReturn(massaProduto());

		PedidoRequestDTO massaPedidoRequestDTO = massaPedidoRequestDTOPedidoStatusInexistente();

		assertThrows(BusinessException.class, () -> orderService.calcularValorTotalProdutos(massaPedidoRequestDTO));
	}

	@ParameterizedTest
	@ValueSource(longs = {0, -1})
	void deveCalcularValorTotalProdutosLancarExcecaoQuantidade0ouMenor(Long quantidade) {
		PedidoRequestDTO massaPedidoRequestDTO = massaPedidoRequestDTO(quantidade);
		assertThrows(BusinessException.class, () -> orderService.calcularValorTotalProdutos(massaPedidoRequestDTO));
	}

	@Test
	void deveCalcularValorTotalProdutosLancarExcecaoNomeDuplicado() {
		PedidoRequestDTO massaPedidoRequestDTO = massaPedidoRequestDTOProdutoNomeDuplicado();
		assertThrows(BusinessException.class, () -> orderService.calcularValorTotalProdutos(massaPedidoRequestDTO));
	}

	@Test
	void deveConsultarPedidoSucesso() {
		when(pedidoRepository.findById(any())).thenReturn(massaPedido());

		PedidoResponseDTO resultado = orderService.consultarPedido(1L);

		assertEquals(1L, resultado.id());
	}

	private PedidoRequestDTO massaPedidoRequestDTO(Long quantidade) {
		return new PedidoRequestDTO(massaListaProdutoRequestDTO(quantidade), 0);
	}

	private PedidoRequestDTO massaPedidoRequestDTOProdutoNomeDuplicado() {
		return new PedidoRequestDTO(massaListaProdutoRequestDTOProdutoNomeDuplicado(), 0);
	}


	private PedidoRequestDTO massaPedidoRequestDTOPedidoStatusInexistente() {
		return new PedidoRequestDTO(massaListaProdutoRequestDTO(50L), 6);
	}

	private List<ProdutoRequestDTO> massaListaProdutoRequestDTO(Long quantidade) {
		List<ProdutoRequestDTO> listaProdutoRequestDTO = new ArrayList<>();
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(
				"Iphone 13", "Excelente dispositivo eletrônico",
				quantidade, 3500.90);
		ProdutoRequestDTO produtoRequestDTO2 = new ProdutoRequestDTO(
				"Iphone 16 Pro Max", "Excelente dispositivo eletrônico de Titanium",
				quantidade, 9900.90);
		listaProdutoRequestDTO.add(produtoRequestDTO);
		listaProdutoRequestDTO.add(produtoRequestDTO2);

		return listaProdutoRequestDTO;
	}

	private List<ProdutoRequestDTO> massaListaProdutoRequestDTOProdutoNomeDuplicado() {
		List<ProdutoRequestDTO> listaProdutoRequestDTO = new ArrayList<>();
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(
				"Iphone 16 Pro Max", "Excelente dispositivo eletrônico",
				50L, 10500.90);
		ProdutoRequestDTO produtoRequestDTO2 = new ProdutoRequestDTO(
				"Iphone 16 Pro Max", "Excelente dispositivo eletrônico de Titanium",
				50L, 9900.90);
		listaProdutoRequestDTO.add(produtoRequestDTO);
		listaProdutoRequestDTO.add(produtoRequestDTO2);

		return listaProdutoRequestDTO;
	}

	private Produto massaProduto() {
		return Produto.builder()
				.id(1L)
				.nome("Iphone 16 Pro Max")
				.descricao("Excelente dispositivo eletrônico de Titanium")
				.quantidade(5L)
				.preco(9900.90)
				.build();
	}

	private Optional<Pedido> massaPedido() {
		return Optional.of(
				Pedido.builder()
						.id(1L)
						.produtos(List.of(massaProduto()))
						.valorTotalProdutos(new BigDecimal("5600.00"))
						.status(StatusPedidoEnum.ENTREGUE)
						.build());
	}
}
