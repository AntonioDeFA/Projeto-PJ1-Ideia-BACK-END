package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.enums.TipoQuestaoAvaliativa;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;

import utils.CompeticaoUtils;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TesteQuestoesAvaliativas {

	@Autowired
	private QuestaoAvaliativaRepositorio questaoAvaliativaRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	private QuestaoAvaliativa questaoAvaliativa = new QuestaoAvaliativa();

	private Competicao competicao;

	@BeforeEach
	public void inicializar() {
		questaoAvaliativa.setNotaMax(10);
		questaoAvaliativa.setQuestao("Questão Teste");
		questaoAvaliativa.setTipoQuestaoAvaliativa(TipoQuestaoAvaliativa.ADAPTABILIDADE);
		questaoAvaliativa.setCompeticaoCadastrada(competicao);
		questaoAvaliativa.setEnumeracao(1);
	}

	@BeforeAll
	public void setUp() {
		competicaoRepositorio.save(CompeticaoUtils.criarCompeticao());
		this.competicao = competicaoRepositorio.findByNomeCompeticao(CompeticaoUtils.nomeCompeticao).get(0);
	}

	@AfterAll
	public void tearDown() {
		competicaoRepositorio.delete(competicao);
	}

//-------------------------Caminho feliz --------------------------------------
	@Test
	public void criarQuestaoAvaliativa() throws Exception {

		questaoAvaliativaRepositorio.save(questaoAvaliativa);

		Optional<QuestaoAvaliativa> questoes = questaoAvaliativaRepositorio
				.findByQuestao(questaoAvaliativa.getQuestao());

		assertTrue(questoes.isPresent());

		QuestaoAvaliativa questao = questoes.get();

		questaoAvaliativaRepositorio.delete(questao);
	}

	@Test
	public void atualizarQuestaoAvaliativa() {
		questaoAvaliativaRepositorio.save(questaoAvaliativa);

		Optional<QuestaoAvaliativa> questoes = questaoAvaliativaRepositorio
				.findByQuestao(questaoAvaliativa.getQuestao());

		assertTrue(questoes.isPresent());

		questaoAvaliativa.setEnumeracao(2);
		questaoAvaliativa.setQuestao("QuestaoAtualização");
		questaoAvaliativa.setNotaMax(20);

		questaoAvaliativaRepositorio.save(questaoAvaliativa);

		questoes = questaoAvaliativaRepositorio.findByQuestao(questaoAvaliativa.getQuestao());

		assertTrue(questoes.isPresent());

		QuestaoAvaliativa questao = questoes.get();

		questaoAvaliativaRepositorio.delete(questao);

	}

//----------------------------	Exceptions ----------------------------------------------------------------
	@Test
	public void notaNullException() {
		questaoAvaliativa.setNotaMax(null);

		assertThrows(Exception.class, () -> {
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		});
	}

	@Test
	public void questaoNullException() {
		questaoAvaliativa.setQuestao(null);
		assertThrows(Exception.class, () -> {
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		});
	}

	@Test
	public void enumeracaoNullException() {
		questaoAvaliativa.setEnumeracao(null);
		assertThrows(DataIntegrityViolationException.class, () -> {
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		});
	}

	@Test
	public void tipoQuestaoAvaliativaNullException() {
		questaoAvaliativa.setTipoQuestaoAvaliativa(null);
		assertThrows(Exception.class, () -> {
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		});
	}

	@Test
	public void valorInferiorNotaMaximaException() {
		questaoAvaliativa.setNotaMax(4);
		;
		assertThrows(Exception.class, () -> {
			questaoAvaliativaRepositorio.save(questaoAvaliativa);
		});
	}

}
