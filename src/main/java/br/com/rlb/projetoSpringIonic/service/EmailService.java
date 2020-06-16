package br.com.rlb.projetoSpringIonic.service;

import org.springframework.mail.SimpleMailMessage;

import br.com.rlb.projetoSpringIonic.entity.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
