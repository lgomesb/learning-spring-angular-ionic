package com.learning.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.learning.cursomc.domain.Cidade;
import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.domain.Endereco;
import com.learning.cursomc.domain.enums.Perfil;
import com.learning.cursomc.domain.enums.TipoCliente;
import com.learning.cursomc.dto.ClienteDTO;
import com.learning.cursomc.dto.ClienteNewDTO;
import com.learning.cursomc.repositories.ClienteRepository;
import com.learning.cursomc.repositories.EnderecoRepository;
import com.learning.cursomc.resources.exception.AuthorizationException;
import com.learning.cursomc.security.UserSecurity;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	public Cliente find(Integer id) throws Exception {

		UserSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrato! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null);
		Cliente newCliente = repository.save(cliente);
		enderecoRepository.saveAll(cliente.getEnderecos());
		return newCliente;
	}

	public Cliente update(Cliente cliente) throws Exception {
		Cliente newCliente = this.find(cliente.getId());
		updateData(newCliente, cliente);
		return repository.save(newCliente);
	}

	public void delete(Integer id) throws Exception {
		this.find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possível excluir porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findByEmail(String email) throws Exception {
		UserSecurity user = UserService.authenticated();
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}

		Cliente cliente = repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Email: " + email + ", Tipo: " + Cliente.class.getName()));

		return cliente;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
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

		if (dto.getTelefone2() != null)
			cliente.getTelefones().add(dto.getTelefone2());

		if (dto.getTelefone3() != null)
			cliente.getTelefones().add(dto.getTelefone3());

		return cliente;
	}

	private void updateData(Cliente newCliente, Cliente cliente) {
		newCliente.setName(cliente.getName());
		newCliente.setEmail(cliente.getEmail());
	}

	public URI uploadProfilePicture(MultipartFile multipartFile) throws Exception {

		UserSecurity user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}

}
