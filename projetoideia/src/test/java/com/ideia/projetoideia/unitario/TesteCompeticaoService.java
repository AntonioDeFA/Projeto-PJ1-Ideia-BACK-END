package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Convite;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.ConviteDto;
import com.ideia.projetoideia.model.dto.EmailDto;
import com.ideia.projetoideia.model.enums.StatusConvite;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.CompeticaoService;
import com.ideia.projetoideia.services.EquipeService;
import com.ideia.projetoideia.services.utils.GeradorEquipeToken;

import utils.CompeticaoUtils;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TesteCompeticaoService {

	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	@Autowired
	EquipeRepositorio equipeRepositorio;

	Competicao competicao = new Competicao();

	Usuario usuario = new Usuario();

	Usuario usuario2 = new Usuario();

	Usuario usuario3 = new Usuario();

	PapelUsuarioCompeticao papelUsuarioCompeticao = new PapelUsuarioCompeticao();

	Equipe equipe = new Equipe();

	PapelUsuarioCompeticao papelUsuarioCompeticaoEquipe = new PapelUsuarioCompeticao();

	@Autowired
	CompeticaoService competicaoService;

	@Autowired
	EquipeService equipeService;

	@BeforeAll
	public void setUp() {
		competicao = CompeticaoUtils.criarCompeticao();
		competicaoRepositorio.save(competicao);

		usuario.setNomeUsuario("João");
		usuario.setEmail("joao123@gmail.com");
		usuario.setSenha("joao123");
		usuarioRepositorio.save(usuario);

		usuario2.setNomeUsuario("Antonio");
		usuario2.setEmail("antonio@gmail.com");
		usuario2.setSenha("antonio123");
		usuarioRepositorio.save(usuario2);

		papelUsuarioCompeticao.setTipoPapelUsuario(TipoPapelUsuario.CONSULTOR);
		papelUsuarioCompeticao.setUsuario(usuario);
		papelUsuarioCompeticao.setCompeticaoCadastrada(competicao);
		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);

		equipe.setNomeEquipe("teste");
		equipe.setDataInscricao(LocalDate.now());
		equipe.setToken(GeradorEquipeToken.gerarTokenEquipe("teste"));
		equipe.setCompeticaoCadastrada(
				competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0));
		equipe.setLider(usuario);
		equipeRepositorio.save(equipe);

		usuario3.setNomeUsuario("maria");
		usuario3.setEmail("maria@gmail.com");
		usuario3.setSenha("maria123");
		usuarioRepositorio.save(usuario3);

		papelUsuarioCompeticaoEquipe.setTipoPapelUsuario(TipoPapelUsuario.COMPETIDOR);
		papelUsuarioCompeticaoEquipe.setUsuario(usuario);
		papelUsuarioCompeticaoEquipe.setCompeticaoCadastrada(equipe.getCompeticaoCadastrada());
		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticaoEquipe);

	}

	@AfterAll
	public void tearDown() {
		competicaoRepositorio.delete(competicao);
		usuarioRepositorio.delete(usuario);
		usuarioRepositorio.delete(usuario2);
		usuarioRepositorio.delete(usuario3);
		papelUsuarioCompeticaoRepositorio.delete(papelUsuarioCompeticao);
		papelUsuarioCompeticaoRepositorio.delete(papelUsuarioCompeticaoEquipe);
		equipeRepositorio.delete(equipe);
	}

//					
//---------------------------------------------------------------------------------------------------------------------------
	@Test
	public void removerUsuarioConsultorCerto() {
		try {
			competicaoService.removerUsuarioConvidado(
					competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0).getId(),
					new EmailDto("joao123@gmail.com"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void removerUsuarioConsultorErrado() {
		assertThrows(Exception.class, () -> {
			competicaoService.removerUsuarioConvidado(10000, new EmailDto("joao123@gmail.com"));
		});

	}

	@Test
	public void convidarUsuarioCerto() {
		try {

			competicaoService.convidarUsuario(new ConviteDto("antonio@gmail.com",
					competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0).getId(),
					TipoConvite.CONSULTOR));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void convidarUsuarioErrado() {
		assertThrows(Exception.class, () -> {
			competicaoService.convidarUsuario(new ConviteDto("antonio@gmail.com",
					competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0).getId(),
					TipoConvite.CONSULTOR));
		});
	}

	@Test
	public void removerEquipeCerto() {
		try {
			competicaoService.deletarequipe(
					competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0).getId(),
					equipeRepositorio.findByNomeEquipe("teste").get(0).getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void removerEquipeErrado() {
		assertThrows(Exception.class, () -> {
			competicaoService.deletarequipe(
					competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(10000).getId(),
					equipeRepositorio.findByNomeEquipe("teste").get(0).getId());
		});

	}

}
