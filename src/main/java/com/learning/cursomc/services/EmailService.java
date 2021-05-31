package com.learning.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.learning.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
	
}
