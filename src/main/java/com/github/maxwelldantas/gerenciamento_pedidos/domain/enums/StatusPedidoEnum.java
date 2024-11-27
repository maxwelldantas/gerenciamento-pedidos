package com.github.maxwelldantas.gerenciamento_pedidos.domain.enums;

import com.github.maxwelldantas.gerenciamento_pedidos.exception.BusinessException;

public enum StatusPedidoEnum {
	RECEBIDO(0),
	PREPARADO(1),
	ENVIADO(2),
	ENTREGUE(3),
	CANCELADO(4);

	private final Integer codigo;

	StatusPedidoEnum(Integer codigo) {
		this.codigo = codigo;
	}

	public static StatusPedidoEnum codigoStatus(Integer codigo) {
		for (StatusPedidoEnum status : values()) {
			if (status.codigo.equals(codigo)) {
				return status;
			}
		}

		throw new BusinessException("Código de status não existe!");
	}
}
