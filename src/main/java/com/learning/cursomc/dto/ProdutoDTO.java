package com.learning.cursomc.dto;

import java.io.Serializable;

import com.learning.cursomc.domain.Produto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Double preco;

	public ProdutoDTO(Produto p) {

		this.id = p.getId();
		this.nome = p.getName();
		this.preco = p.getPreco();
		
	}

}
