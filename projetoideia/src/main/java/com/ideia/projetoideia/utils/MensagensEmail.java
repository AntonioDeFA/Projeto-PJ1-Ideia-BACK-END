package com.ideia.projetoideia.utils;

import com.ideia.projetoideia.model.Usuario;

public class MensagensEmail {
	
	public static  String ASSUNTO_RESETAR_SENHA = "Alerta de segurança";
	
	
	public static String montarCorpoResetarSenha(Usuario user , String senha) {
		
		String mensagem = "Olá senhor(a): " + user.getNomeUsuario()+ "\n "
				+ "sua senha do Ideia foi resetada, recomendamos que a atualize o mais rápido possível !\n"
				+ "Sua nova senha é: "+senha;
		
		return mensagem;
		
	}

}
