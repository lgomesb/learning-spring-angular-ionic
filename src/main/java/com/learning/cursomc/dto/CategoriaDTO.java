package com.learning.cursomc.dto;

import java.io.Serializable;

import com.learning.cursomc.domain.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	
	public CategoriaDTO( Categoria categoria) {
		
		this.id = categoria.getId();
		this.name = categoria.getName();
		
	}

}
