package com.learning.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.cursomc.domain.Cidade;
import com.learning.cursomc.domain.Estado;
import com.learning.cursomc.dto.CidadeDTO;
import com.learning.cursomc.dto.EstadoDTO;
import com.learning.cursomc.services.CidadeService;
import com.learning.cursomc.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoRerource {
	
	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = service.findAll(); 
		List<EstadoDTO> estados = list.stream().map(e -> new EstadoDTO(e)).collect(Collectors.toList());
		
		return ResponseEntity.ok(estados);
	}
	
	@GetMapping("/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades( @PathVariable Integer estadoId ) {
		List<Cidade> list = cidadeService.findByEstado(estadoId); 
		List<CidadeDTO> cidades = list.stream().map(c -> new CidadeDTO(c)).collect(Collectors.toList());
		
		return ResponseEntity.ok(cidades);
	}

}
