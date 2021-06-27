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
public class StandardError implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long timestamp;
	private Integer status; 
	private String error; 
	private String message;
	private String path;
	
}
