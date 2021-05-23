package com.learning.cursomc.resources.exception;

import lombok.Setter;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;
}
