package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.UsuarioService;

import static utils.Utils.*;
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class IniciarTestes {
	
	
	
	@Autowired
	public UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	public UsuarioService usuarioService;
	
	@Test
	public void  inicializarUsuarioTestes() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNomeUsuario(NOME_USUARIO);
		usuario.setEmail(EMAIL);
		usuario.setSenha(SENHA);
		
		usuarioService.criarUsuario(usuario);
		
	}
	
	@Test
	public void inicializarConsultoresEAvaliadores() throws Exception {
		Usuario usuario1 = new Usuario();
		usuario1.setNomeUsuario("Consultor");
		usuario1.setEmail("consultor@gmail.com");
		usuario1.setSenha(SENHA);
		
		Usuario usuario2 = new Usuario();
		usuario2.setNomeUsuario("Avaliador");
		usuario2.setEmail("avaliador@gmail.com");
		usuario2.setSenha(SENHA);
		
		usuarioService.criarUsuario(usuario1);
		
		usuarioService.criarUsuario(usuario2);
	}
	

}
