package com.ideia.projetoideia.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ideia.projetoideia.model.Usuario;

@Component
public class EnviarEmail {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void enviarEmailDeResetDeSenha(Usuario user, String senha) {
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(user.getEmail());
		email.setSubject(MensagensEmail.ASSUNTO_RESETAR_SENHA);
		email.setText(MensagensEmail.montarCorpoResetarSenha(user, senha));
		javaMailSender.send(email);
	}

}
