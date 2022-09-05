package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.model.AvaliacaoPitch;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;
import com.ideia.projetoideia.repository.AvaliacaoPitchRpositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.PitchRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteAvaliacaoPitch {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	@Autowired
	private AvaliacaoPitchRpositorio avaliacaoPitchRepositorio;

	@Autowired
	private PitchRepositorio pitchRepositorio;

	@Autowired
	private QuestaoAvaliativaRepositorio questaoAvaliativaRepositorio;

	private AvaliacaoPitch avaliacaoPitch;

	private Usuario usuario = new Usuario();

	private Competicao competicao = new Competicao();

	private QuestaoAvaliativa questaoAvaliativa = new QuestaoAvaliativa();

	private List<UsuarioMembroComum> usuarios = new ArrayList<UsuarioMembroComum>();

	private Equipe equipe = new Equipe();

	private Pitch pitch;

	@BeforeEach
	public void incializar() {

		usuario.setNomeUsuario("João");
		usuario.setEmail("joao123@gmail.com");
		usuario.setSenha("joao123");
		usuario = usuarioRepositorio.findById(usuarioRepositorio.save(usuario).getId()).get();

		competicao.setNomeCompeticao("Competição IFPB");
		competicao.setQntdMaximaMembrosPorEquipe(25);
		competicao.setQntdMinimaMembrosPorEquipe(2);
		competicao.setTempoMaximoVideoEmSeg(255f);
		competicao.setArquivoRegulamentoCompeticao("");
		competicao = competicaoRepositorio.findById(competicaoRepositorio.save(competicao).getId()).get();

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
		equipe = equipeRepositorio.findById(equipeRepositorio.save(equipe).getId()).get();

		pitch = new Pitch();
		pitch.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.AVALIADO_AVALIADOR);
		pitch.setTitulo("Titulo");
		pitch.setDescricao("Descrição");
		pitch.setDataCriacao(LocalDateTime.now());
		pitch.setPitchDeck("Pitch deck");
		pitch.setEquipe(equipe);
		pitchRepositorio.save(pitch).getId();

		questaoAvaliativa.setNotaMax(10);
		questaoAvaliativa.setQuestao("Questão Teste");
		questaoAvaliativa.setTipoQuestaoAvaliativa(TipoQuestaoAvaliativa.ADAPTABILIDADE);
		questaoAvaliativa.setCompeticaoCadastrada(competicao);
		questaoAvaliativa.setEnumeracao(1);
		questaoAvaliativaRepositorio.save(questaoAvaliativa);

		avaliacaoPitch = new AvaliacaoPitch(LocalDate.now(), 5, "observação", pitch, questaoAvaliativa, usuario);

	}

//												Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCriarAvaliacaoPitchCorreto() {

		Integer idAvaliacaoPitch = avaliacaoPitchRepositorio.save(avaliacaoPitch).getId();

		AvaliacaoPitch avaliacaoPitchRetorno = avaliacaoPitchRepositorio.findById(idAvaliacaoPitch).get();

		assertNotNull(avaliacaoPitchRetorno);

		avaliacaoPitchRepositorio.delete(avaliacaoPitchRetorno);
	}

	@Test
	public void testeAtualizarAvaliacaoPitchCorreto() {

		Integer idAvaliacaoPitch = avaliacaoPitchRepositorio.save(avaliacaoPitch).getId();

		AvaliacaoPitch avaliacaoPitchRetorno = avaliacaoPitchRepositorio.findById(idAvaliacaoPitch).get();

		avaliacaoPitch.setNotaAtribuida(8);
		avaliacaoPitch.setObservacao("Uma observação");

		Integer idAvaliacaoPitchAtualizado = avaliacaoPitchRepositorio.save(avaliacaoPitchRetorno).getId();

		AvaliacaoPitch avaliacaoPitchAtualizado = avaliacaoPitchRepositorio.findById(idAvaliacaoPitchAtualizado).get();

		assertNotNull(avaliacaoPitchAtualizado);

		avaliacaoPitchRepositorio.delete(avaliacaoPitchAtualizado);

	}

	@Test
	public void testeDeletarAvaliacaoPitchCorreto() {

		Integer idAvaliacaoPitch = avaliacaoPitchRepositorio.save(avaliacaoPitch).getId();

		AvaliacaoPitch avaliacaoPitchRetorno = avaliacaoPitchRepositorio.findById(idAvaliacaoPitch).get();

		assertNotNull(avaliacaoPitchRetorno);

		avaliacaoPitchRepositorio.delete(avaliacaoPitchRetorno);

		AvaliacaoPitch avaliacaoPitchDeletada = avaliacaoPitchRepositorio.findById(idAvaliacaoPitch).orElse(null);

		assertNull(avaliacaoPitchDeletada);

	}

//										Exception	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void notaAtribuidaNullException() {
		avaliacaoPitch.setNotaAtribuida(null);
		assertThrows(Exception.class, () -> {
			avaliacaoPitchRepositorio.save(avaliacaoPitch);
		});
	}

	@Test
	public void observacaoNullException() {
		avaliacaoPitch.setObservacao(null);
		assertThrows(Exception.class, () -> {
			avaliacaoPitchRepositorio.save(avaliacaoPitch);
		});
	}

	@Test
	public void valorInferiorNotaMaximaException() {
		avaliacaoPitch.setDataAvaliacao(null);
		assertThrows(Exception.class, () -> {
			avaliacaoPitchRepositorio.save(avaliacaoPitch);
		});
	}
}
