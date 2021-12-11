package com.ideia.projetoideia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ideia.projetoideia.services.UsuarioService;

@SpringBootApplication
public class ProjetoideiaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoideiaApplication.class, args);
	}
	
	@Autowired
	private UsuarioService usuarioService;
	
	public void run(String... args) {
	
		
//	Usuario user = new Usuario();
//	user.setEmail("Gabryel@gmail.com");
//	user.setNome("Gabryel");
//	user.setSenha("1");
//	
//	try {
//		usuarioService.criarUsuario(user);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.println("TERMINEI");
	}

}
