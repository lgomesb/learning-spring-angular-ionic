package com.learning.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public AuthorizationException(String msg) {
		super(msg);		
	}

	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);		
	}
	
}
