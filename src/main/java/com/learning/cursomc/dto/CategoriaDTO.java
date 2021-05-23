package com.learning.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

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
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length( min = 5, max = 80, message = "O tamanho de ser entre 5 e 80 caracteres")
	private String name;
	
	public CategoriaDTO( Categoria categoria) {
		
		this.id = categoria.getId();
		this.name = categoria.getName();
		
	}

}
