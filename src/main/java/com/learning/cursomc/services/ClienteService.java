package com.learning.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learning.cursomc.domain.Cidade;
import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.domain.Endereco;
import com.learning.cursomc.domain.enums.TipoCliente;
import com.learning.cursomc.dto.ClienteDTO;
import com.learning.cursomc.dto.ClienteNewDTO;
import com.learning.cursomc.repositories.ClienteRepository;
import com.learning.cursomc.repositories.EnderecoRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert( Cliente cliente ) {
		cliente.setId(null);
		Cliente newCliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
	
		return newCliente;
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

		return new Cliente(dto.getId(), dto.getName(), dto.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {

		Cliente cliente = new Cliente(null, dto.getName(), dto.getEmail(), dto.getCpfOrCnpj(),
				TipoCliente.toEnum(dto.getTipo()), pe.encode(dto.getSenha()));
		Endereco endereco = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(),
				dto.getBairro(), dto.getCep(), cliente, new Cidade(dto.getCidadeId(), null, null));
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(dto.getTelefone1());
		
		if( dto.getTelefone2() != null)
			cliente.getTelefones().add(dto.getTelefone2());
		
		if( dto.getTelefone3() != null)
			cliente.getTelefones().add(dto.getTelefone3());
		
		return cliente;
	}


	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setName(cliente.getName());
		newCliente.setEmail(cliente.getEmail());
	}
	
}
