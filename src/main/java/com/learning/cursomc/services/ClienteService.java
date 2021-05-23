package com.learning.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.dto.ClienteDTO;
import com.learning.cursomc.repositories.ClienteRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente update(Cliente cliente) throws ObjectNotFoundException {

		Cliente newCliente = this.find(cliente.getId());
		updateData(newCliente, cliente);
		return repository.save(newCliente);
	}


	public void delete(Integer id) throws ObjectNotFoundException, DataIntegrityViolationException {

		this.find(id);	
		
		try {
			repository.deleteById(id);			
		} catch ( DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
				
		return repository.findAll();
	}
	
	public Page<Cliente> findPage( Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
		
	}

	public Cliente fromDTO(ClienteDTO dto) {

		return new Cliente(dto.getId(), dto.getName(), dto.getEmail(), null, null);
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setName(cliente.getName());
		newCliente.setEmail(cliente.getEmail());
	}
	
}
