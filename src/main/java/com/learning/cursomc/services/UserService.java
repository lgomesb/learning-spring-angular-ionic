package com.learning.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.learning.cursomc.security.UserSecurity;

@Service
public class UserService {

	public static UserSecurity authenticated() {
		try {
			return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
