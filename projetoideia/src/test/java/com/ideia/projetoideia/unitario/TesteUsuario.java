package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteUsuario {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	Usuario usuario = new Usuario();

	@BeforeEach
	public void incializar() {
		usuario.setNomeUsuario("João");
		usuario.setEmail("joao123@gmail.com");
		usuario.setSenha("joao123");
	}
	
//	  						Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testeCadastroUsuarioCorreto() {
		usuarioRepositorio.save(usuario);
		Usuario usuarioTest = usuarioRepositorio.findByNomeUsuario(usuario.getNomeUsuario()).get(0);

		assertNotNull(usuarioTest);

		usuarioRepositorio.deleteById(usuarioTest.getId());
	}	
	
	@Test
	public void testeAutualizarUsuarioCorreto() {
		usuarioRepositorio.save(usuario);
		Usuario usuarioTest = usuarioRepositorio.findByNomeUsuario(usuario.getNomeUsuario()).get(0);

		assertNotNull(usuarioTest);

		usuario.setNomeUsuario("Maria");
		usuario.setEmail("maria123@gmail.com");
		usuario.setSenha("maria123"); 
		
		usuarioRepositorio.save(usuario);

		Usuario usuarioNovoTest = usuarioRepositorio.findByNomeUsuario(usuario.getNomeUsuario()).get(0);
		assertNotNull(usuarioNovoTest); 
		
		usuarioRepositorio.deleteById(usuarioNovoTest.getId());
	}
	
	
//  						Exceptions 	
//---------------------------------------------------------------------------------------------------------------------------
	
	@Test
	public void testarNomeErrado() {
		usuario.setNomeUsuario(null);;
		assertThrows(DataIntegrityViolationException.class,() ->{
			usuarioRepositorio.save(usuario);
		});

	}

//---------------------------------------------

	@Test
	public void testarEmailErrado() {
		usuario.setEmail(null);;
		assertThrows(DataIntegrityViolationException.class,() ->{
			usuarioRepositorio.save(usuario);
		});
	}

//---------------------------------------------
	
	@Test
	public void testarSenhaErrado() {
		usuario.setSenha(null);;
		assertThrows(DataIntegrityViolationException.class,() ->{
			usuarioRepositorio.save(usuario);
		});
	}
}
