package com.learning.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.cursomc.domain.Pedido;
import com.learning.cursomc.services.PedidoService;

@RestController
@RequestMapping( value = "/pedidos" )
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> find(@PathVariable Integer id) throws Exception {
		
		Pedido pedido = service.find(id);
		return ResponseEntity.ok().body(pedido);
		
	}
	
	@PostMapping
	public ResponseEntity<Void> insert( @Valid @RequestBody Pedido pedido) throws Exception {
		
		pedido = service.insert(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(pedido.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Page<Pedido>> findPage( 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "DESC") String direction ) throws Exception {
		
		Page<Pedido> pedidos = service.findPage(page, linesPerPage, orderBy, direction);		
		
		return ResponseEntity.ok().body(pedidos);
		
	}
	
}
