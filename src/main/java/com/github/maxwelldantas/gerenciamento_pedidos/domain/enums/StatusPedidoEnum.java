package com.github.maxwelldantas.gerenciamento_pedidos.domain.enums;

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
		throw new RuntimeException("Código de status não existe!");
	}
}