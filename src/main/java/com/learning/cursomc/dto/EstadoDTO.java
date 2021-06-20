package com.learning.cursomc.dto;

import java.io.Serializable;

import com.learning.cursomc.domain.Estado;

import lombok.Data;

@Data
public class EstadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id; 
	private String nome;
	
	public EstadoDTO(Estado e)  {
		id = e.getId();
		nome = e.getNome();
				
	}
}
