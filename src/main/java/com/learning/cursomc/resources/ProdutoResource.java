package com.learning.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cursomc.domain.Produto;
import com.learning.cursomc.dto.ProdutoDTO;
import com.learning.cursomc.resources.utils.URL;
import com.learning.cursomc.services.ProdutoService;

@RestController
@RequestMapping( value = "/produtos" )
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> find(@PathVariable Integer id) throws Exception {
		
		Produto produto = service.find(id);
		return ResponseEntity.ok().body(produto);
		
	}
	
	@GetMapping()
	public ResponseEntity<Page<ProdutoDTO>> findPage( 
			@RequestParam(value="nome", defaultValue="") String nome, 
			@RequestParam(value="categorias", defaultValue="") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="name") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) throws Exception {
		
		List<Integer> ids = URL.decodeIntList(categorias);
		String nomeDecoded = URL.decodeParam(nome);
		
		Page<Produto> produtos = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);		
		Page<ProdutoDTO> produtosDTO = produtos.map(p -> new ProdutoDTO(p));
		
		return ResponseEntity.ok().body(produtosDTO);
		
	}
	
}
