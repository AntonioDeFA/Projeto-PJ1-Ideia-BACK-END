package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionSystemException;

import com.ideia.projetoideia.ProjetoideiaApplication;
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteCompeticao {

	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	Competicao competicao = new Competicao();

	@BeforeEach
	public void incializar() {
		competicao.setNomeCompeticao("Competição IFPB");
		competicao.setQntdMaximaMembrosPorEquipe(25);
		competicao.setQntdMinimaMembrosPorEquipe(2);
		competicao.setTempoMaximoVideoEmSeg(255f);
		competicao.setArquivoRegulamentoCompeticao(new File("local"));
	}

//  							Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCadastroCompeticaoCorreto() {
		competicaoRepositorio.save(competicao);
		Competicao competicaoTest = competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0);

		assertNotNull(competicaoTest);

		competicaoRepositorio.deleteById(competicaoTest.getId());
	}

	@Test
	public void testeAtualizarCompeticao() {
		competicaoRepositorio.save(competicao);
		Competicao competicaoTest = competicaoRepositorio.findByNomeCompeticao(competicao.getNomeCompeticao()).get(0);
		assertNotNull(competicaoTest);

		competicaoTest.setNomeCompeticao("Novo nome da Competição");
		competicaoTest.setQntdMaximaMembrosPorEquipe(10);
		competicaoTest.setQntdMinimaMembrosPorEquipe(9);
		competicaoTest.setTempoMaximoVideoEmSeg(200f);
		competicaoTest.setArquivoRegulamentoCompeticao(new File("remoto"));

		competicaoRepositorio.save(competicaoTest);

		Competicao competicaoNovoTest = competicaoRepositorio.findByNomeCompeticao(competicaoTest.getNomeCompeticao())
				.get(0);
		assertNotNull(competicaoNovoTest);

		competicaoRepositorio.deleteById(competicaoNovoTest.getId());

	}
	
	@Test
	public void testeDeletarCompeticaoCorreto() {

		Integer idCompeticao = competicaoRepositorio.save(competicao).getId();

		Competicao competicaoRcuperada = competicaoRepositorio.findById(idCompeticao).get();

		assertNotNull(competicaoRcuperada); // valida que a equipe existe no banco

		competicaoRepositorio.delete(competicaoRcuperada);

		Competicao competicaoDeletado = competicaoRepositorio.findById(idCompeticao).orElse(null);

		assertNull(competicaoDeletado);

	}
	
//                                       Exceptions 	
//  ---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCadastroComNomeEmBrancoException() {
		competicao.setNomeCompeticao(null);

		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});

	}

//------------------- Exception Maximo e Minimo membros de Equipe   -------------
	@Test
	public void testeQuantidadeMaiorQueMaxMembroExeption() {
		competicao.setQntdMaximaMembrosPorEquipe(31);

		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

	@Test
	public void testeQuantidadeMenorQueMaxMembroExeption() {
		competicao.setQntdMaximaMembrosPorEquipe(0);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

	@Test
	public void testeQuantidadeMaiorQueMinMembro() {
		competicao.setQntdMinimaMembrosPorEquipe(30);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

	@Test
	public void testeQuantidadeMenorQueMinMembro() {
		competicao.setQntdMinimaMembrosPorEquipe(0);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

//---------------------------------------------

	@Test
	public void testeTempoMaiorQueTempoMaximoVideo() {
		competicao.setTempoMaximoVideoEmSeg(1801f);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

	@Test
	public void testeTempoMenoQueTempoMaximoVideo() {
		competicao.setTempoMaximoVideoEmSeg(29f);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}

	@Test
	public void testeSemArquivo() {
		competicao.setArquivoRegulamentoCompeticao(null);
		assertThrows(TransactionSystemException.class, () -> {
			competicaoRepositorio.save(competicao);
		});
	}
}
