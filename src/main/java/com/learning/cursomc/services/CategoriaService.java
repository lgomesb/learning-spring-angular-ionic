package com.learning.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.cursomc.domain.Categoria;
import com.learning.cursomc.repositories.CategoriaRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria buscar(Integer id) throws ObjectNotFoundException {
		
		Optional<Categoria> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}
	
}
