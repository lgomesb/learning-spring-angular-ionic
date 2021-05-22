package com.learning.cursomc.domain.enums;

import lombok.Getter;

@Getter
public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {

		this.cod = cod;
		this.descricao = descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		if( cod == null ) {
			return null;
		}
		
		for (EstadoPagamento item : EstadoPagamento.values()) {
			if(cod.equals(item.getCod())) {
				return item;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	
}
