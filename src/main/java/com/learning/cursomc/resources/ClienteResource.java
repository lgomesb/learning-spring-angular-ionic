package com.learning.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.services.ClienteService;

@RestController
@RequestMapping( value = "/clientes" )
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> find(@PathVariable Integer id) throws Exception {
		
		Cliente cliente = service.buscar(id);
		return ResponseEntity.ok().body(cliente);
		
	}
	
}
