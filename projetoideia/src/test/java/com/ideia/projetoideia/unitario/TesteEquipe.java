package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteEquipe {

	@Autowired
	private EquipeRepositorio equipeRepositorio;
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	Usuario usuario = new Usuario();
	Competicao competicao = new Competicao();
	List<UsuarioMembroComum> usuarios = new ArrayList<UsuarioMembroComum>();
	Equipe equipe = new Equipe();

	@BeforeEach
	public void incializar() {

		// busque um usuário e uma competicao já existente no banco
		usuario = usuarioRepositorio.findByEmail("Gabriel@gmail.com").get();
		competicao = competicaoRepositorio.findById(79).get();

		UsuarioMembroComum user1 = new UsuarioMembroComum();
		user1.setEmail("user1@gmail.com");
		user1.setNome("user 1");

		UsuarioMembroComum user2 = new UsuarioMembroComum();
		user1.setEmail("user2@gmail.com");
		user1.setNome("user 2");

		usuarios.add(user1);
		usuarios.add(user2);

		equipe.setNomeEquipe("EQUIPE 1");
		equipe.setToken("TOKEN_EQUIPE_1");
		equipe.setDataInscricao(LocalDate.now());
		equipe.setLider(usuario);
		equipe.setCompeticaoCadastrada(competicao);
		usuarios.get(0).setEquipe(equipe);
		usuarios.get(1).setEquipe(equipe);
		equipe.setUsuarios(usuarios);

	}

//												Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCriarEquipeCorreto() {

		Integer idEquipe = equipeRepositorio.save(equipe).getId();

		Equipe equipeRetorno = equipeRepositorio.findById(idEquipe).get();

		assertNotNull(equipeRetorno);

		equipeRepositorio.delete(equipeRetorno);
	}

	@Test
	public void testeAtualizarEquipeCorreto() {

		Integer idEquipe = equipeRepositorio.save(equipe).getId();

		Equipe equipeRetorno = equipeRepositorio.findById(idEquipe).get();

		equipeRetorno.setNomeEquipe("EQUIPE_ATUALIZADA");
		equipeRetorno.setToken("TOKEN_EQUIPE_ATUALIZADA");

		Integer idEquipeAtualizada = equipeRepositorio.save(equipeRetorno).getId();

		Equipe equipeAtualizada = equipeRepositorio.findById(idEquipeAtualizada).get();

		assertNotNull(equipeAtualizada);

		equipeRepositorio.delete(equipeAtualizada);

	}

	@Test
	public void testeDeletarEquipeCorreto() {

		Integer idEquipe = equipeRepositorio.save(equipe).getId();

		Equipe equipeRetorno = equipeRepositorio.findById(idEquipe).get();

		assertNotNull(equipeRetorno); // valida que a equipe existe no banco

		equipeRepositorio.delete(equipeRetorno);

		Equipe equipeDeletada = equipeRepositorio.findById(idEquipe).orElse(null);

		assertNull(equipeDeletada);

	}

//  														Exceptions 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCadastroEquipeComNomeNuloException() {
		equipe.setNomeEquipe(null);

		assertThrows(DataIntegrityViolationException.class, () -> {
			equipeRepositorio.save(equipe);
		});

	}

	@Test
	public void testeCadastroEquipeComTokenEmBrancoException() {
		equipe.setToken(null);

		assertThrows(DataIntegrityViolationException.class, () -> {
			equipeRepositorio.save(equipe);
		});

	}

	@Test
	public void testeCadastroEquipeSemDataException() {
		equipe.setDataInscricao(null);

		assertThrows(DataIntegrityViolationException.class, () -> {
			equipeRepositorio.save(equipe);
		});

	}

}
