package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
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
	
	@Test
	public void testeCadastroCompeticaoCorreto() {
		System.out.println(competicao);
		competicaoRepositorio.save(competicao);
		assertNotNull(competicaoRepositorio.findById(competicao.getId()));
	}

	@Test
	public void testeCadastroComNomeEmBranco() {
		competicaoRepositorio.save(competicao);
	}

	@Test
	public void testeQuantidadeMaiorQueMaxMembro() {
		competicao.setQntdMaximaMembrosPorEquipe(31);
		competicaoRepositorio.save(competicao);
	}

	@Test
	public void testeQuantidadeMenorQueMaxMembro() {
		competicao.setQntdMaximaMembrosPorEquipe(0);
		competicaoRepositorio.save(competicao);
	}
	@Test
	public void testeQuantidadeMaiorQueMinMembro() {
		competicao.setQntdMinimaMembrosPorEquipe(30);
		competicaoRepositorio.save(competicao);
	}

	@Test
	public void testeQuantidadeMenorQueMinMembro() {
		competicao.setQntdMinimaMembrosPorEquipe(0);
		competicaoRepositorio.save(competicao);
	}
	
	@Test
	public void testeTempoMaiorQueTempoMaximoVideo() {
		competicao.setTempoMaximoVideoEmSeg(1201f);
		competicaoRepositorio.save(competicao);
	}

	@Test
	public void testeTempoMenoQueTempoMaximoVideo() {
		competicao.setTempoMaximoVideoEmSeg(29f);
		competicaoRepositorio.save(competicao);
	}
	@Test
	public void testeSemArquivo() {
		competicao.setArquivoRegulamentoCompeticao(null);;
		competicaoRepositorio.save(competicao);
	}
}
