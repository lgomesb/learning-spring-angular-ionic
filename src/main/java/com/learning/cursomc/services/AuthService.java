package com.learning.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.repositories.ClienteRepository;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) throws Exception {
		
		Cliente cliente = clienteRepository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
		
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		for(int i=0; i< 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		
		switch (opt) {
		case 0:
			return (char) (rand.nextInt(10) + 48 ); // https://unicode-table.com/pt/ gera um digito
		case 1:
			return (char) (rand.nextInt(26) + 48 ); // https://unicode-table.com/pt/ gera letra maiuscula
		default:
			return (char) (rand.nextInt(26) + 97 ); // https://unicode-table.com/pt/ gera letra minuscula
		}		
	}
	
}
