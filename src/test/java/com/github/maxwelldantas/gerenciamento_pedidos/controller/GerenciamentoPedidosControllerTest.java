package com.github.maxwelldantas.gerenciamento_pedidos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.PedidoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.request.ProdutoRequestDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.dto.response.PedidoResponseDTO;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.enums.StatusPedidoEnum;
import com.github.maxwelldantas.gerenciamento_pedidos.domain.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GerenciamentoPedidosController.class)
class GerenciamentoPedidosControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OrderService orderService;

	@Test
	void deveReceberPedidoEChamarOrderService() throws Exception {
		PedidoRequestDTO pedidoRequestDTO = massaPedidoRequestDTO();

		ObjectMapper objectMapper = new ObjectMapper();

		mockMvc.perform(MockMvcRequestBuilders.post("/pedidos")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pedidoRequestDTO)))

				.andExpect(status().isOk());

		verify(orderService).calcularValorTotalProdutos(pedidoRequestDTO);
	}

	@Test
	void deveConsultarPedidoERetornarOk() throws Exception {
		Long pedidoId = 1L;
		PedidoResponseDTO pedidoResponseDTO = pedidoResponseDTO();
		when(orderService.consultarPedido(pedidoId)).thenReturn(pedidoResponseDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/pedidos/{id}", pedidoId))
				.andExpect(status().isOk());
	}

	private PedidoRequestDTO massaPedidoRequestDTO() {
		return new PedidoRequestDTO(massaListaProdutoRequestDTO(), 0);
	}

	private List<ProdutoRequestDTO> massaListaProdutoRequestDTO() {
		List<ProdutoRequestDTO> listaProdutoRequestDTO = new ArrayList<>();
		ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(
				"Iphone 13", "Excelente dispositivo eletrônico",
				45L, 3500.90);
		ProdutoRequestDTO produtoRequestDTO2 = new ProdutoRequestDTO(
				"Iphone 16 Pro Max", "Excelente dispositivo eletrônico de Titanium",
				15L, 9900.90);
		listaProdutoRequestDTO.add(produtoRequestDTO);
		listaProdutoRequestDTO.add(produtoRequestDTO2);

		return listaProdutoRequestDTO;
	}

	private PedidoResponseDTO pedidoResponseDTO() {
		return new PedidoResponseDTO(1L, null, new BigDecimal("6000.00"), StatusPedidoEnum.RECEBIDO);
	}
}
