package com.ideia.projetoideia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ideia.projetoideia.services.UsuarioService;

@SpringBootApplication
public class ProjetoideiaApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetoideiaApplication.class, args);
	}


	public void run(String... args) {

		usuarioService.inicializarPerfil(); 
		
	}

}
