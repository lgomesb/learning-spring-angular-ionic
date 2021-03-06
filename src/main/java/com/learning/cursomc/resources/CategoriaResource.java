package com.learning.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.cursomc.domain.Categoria;
import com.learning.cursomc.dto.CategoriaDTO;
import com.learning.cursomc.services.CategoriaService;

@RestController
@RequestMapping( value = "/categorias" )
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@GetMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<List<CategoriaDTO>> findAll() throws Exception {
		
		List<Categoria> categorias = service.findAll();		
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
	
	@GetMapping(value = "/page")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Page<CategoriaDTO>> findPage( 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction ) throws Exception {
		
		Page<Categoria> categorias = service.findPage(page, linesPerPage, orderBy, direction);		
		Page<CategoriaDTO> categoriasDTO = categorias.map(c -> new CategoriaDTO(c));
		
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) throws Exception {
		
		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert( @Valid @RequestBody CategoriaDTO dto) {
		
		Categoria categoria = service.fromDTO(dto);		
		categoria = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO dto) throws Exception {
		
		Categoria categoria = service.fromDTO(dto);
		categoria.setId(id);
		categoria = service.update(categoria);
		
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable Integer id) throws Exception {
		
		service.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
}
