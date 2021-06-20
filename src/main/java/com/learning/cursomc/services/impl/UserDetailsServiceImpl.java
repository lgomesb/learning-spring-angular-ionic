package com.learning.cursomc.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.cursomc.domain.Cliente;
import com.learning.cursomc.repositories.ClienteRepository;
import com.learning.cursomc.security.UserSecurity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
		return new UserSecurity(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfils());
	}

}
