package com.learning.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.dto.ClienteDTO;
import com.learning.cursomc.services.ClienteService;

@RestController
@RequestMapping( value = "/clientes" )
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<List<ClienteDTO>> findAll() throws Exception {
		
		List<Cliente> clientes = service.findAll();		
		List<ClienteDTO> clientesDTO = clientes.stream().map(c -> new ClienteDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(clientesDTO);
		
	}
	
	@GetMapping(value = "/page")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Page<ClienteDTO>> findPage( 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction ) throws Exception {
		
		Page<Cliente> clientes = service.findPage(page, linesPerPage, orderBy, direction);		
		Page<ClienteDTO> clientesDTO = clientes.map(c -> new ClienteDTO(c));
		
		return ResponseEntity.ok().body(clientesDTO);
		
	}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) throws Exception {
		
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);
		
	}
	
//	@PostMapping
//	public ResponseEntity<Void> insert( @Valid @RequestBody ClienteDTO dto) {
//		
//		Cliente cliente = service.fromDTO(dto);		
//		cliente = service.insert(cliente);
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//				.path("/{id}").buildAndExpand(cliente.getId()).toUri();
//		
//		return ResponseEntity.created(uri).build();
//	}
	
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO dto) throws Exception {
		
		Cliente cliente = service.fromDTO(dto);
		cliente.setId(id);
		cliente = service.update(cliente);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
}
