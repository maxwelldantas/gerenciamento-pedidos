package com.github.maxwelldantas.gerenciamento_pedidos.domain.model;

import com.github.maxwelldantas.gerenciamento_pedidos.domain.enums.StatusPedidoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PEDIDO")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToMany
	private List<Produto> produtos;
	@Column
	private BigDecimal valorTotalProdutos;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private StatusPedidoEnum status;
}
