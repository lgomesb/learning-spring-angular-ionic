package com.learning.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public FileException(String msg) {
		super(msg);		
	}

	public FileException(String msg, Throwable cause) {
		super(msg, cause);		
	}
	
}
