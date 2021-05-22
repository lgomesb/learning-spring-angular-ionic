package com.learning.cursomc.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.learning.cursomc.domain.enums.EstadoPagamento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PagamentoComBoleto extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonFormat( pattern = "dd/MM/yyyy")
	private Date dataVentimento;
	
	@JsonFormat( pattern = "dd/MM/yyyy")
	private Date dataPagamento;
	
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado.getCod(), pedido);
		this.dataVentimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	
	
}
