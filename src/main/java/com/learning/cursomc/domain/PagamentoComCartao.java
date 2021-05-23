package com.learning.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import com.learning.cursomc.domain.enums.EstadoPagamento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PagamentoComCartao extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		
		super(id,estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	
}
