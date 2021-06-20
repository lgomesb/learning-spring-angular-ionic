package com.learning.cursomc.dto;

import java.io.Serializable;

import com.learning.cursomc.domain.Cidade;

import lombok.Data;

@Data
public class CidadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id; 
	private String nome;

	public CidadeDTO(Cidade c) {
		this.id = c.getId();
		this.nome = c.getNome();
	}
	
}
