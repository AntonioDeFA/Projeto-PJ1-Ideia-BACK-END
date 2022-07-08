package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
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
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.LeanCanvasRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TesteLeanCanvas {

	@Autowired
	private LeanCanvasRepositorio leanCanvasRepositorio;

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	private LeanCanvas leanCanvas = new LeanCanvas();

	private Equipe equipe;

	@BeforeAll
	public void setUp() {

		equipe = new Equipe();

		equipe.setNomeEquipe("Equipe Test");
		equipe.setToken("Token Test");
		equipe.setDataInscricao(LocalDate.now());

		equipeRepositorio.save(equipe);
		equipe = equipeRepositorio.findByNomeEquipe("Equipe Test").get(0);

	}

	@BeforeEach
	public void restaurar() {

		leanCanvas.setProblema("Problema");
		leanCanvas.setSolucao("Solução");
		leanCanvas.setMetricasChave("Metricas");
		leanCanvas.setPropostaValor("Proposta");
		leanCanvas.setVantagemCompetitiva("Vantagem");
		leanCanvas.setCanais("Canais");
		leanCanvas.setSegmentosDeClientes("Segmentos");
		leanCanvas.setEstruturaDeCusto("Estrutura");
		leanCanvas.setFontesDeReceita("Fontes");
		leanCanvas.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_ELABORACAO);

		leanCanvas.setEquipe(equipe);

	}
	
	@AfterAll
	public void tearDown() {
		leanCanvasRepositorio.delete(leanCanvas);
		equipeRepositorio.delete(equipe);
	}

	@Test
	@Order(2)
	public void cadastrarLeanCanvas() {
		leanCanvasRepositorio.save(leanCanvas);

		assertNotNull(leanCanvasRepositorio.findByEquipe(equipe));
		
		leanCanvasRepositorio.delete(leanCanvasRepositorio.findByEquipe(equipe).get(0));

	}

	@Test
	@Order(1)
	public void editarLeanCanvas() {
		leanCanvasRepositorio.save(leanCanvas);
		leanCanvas.setProblema("Novo problema");
		leanCanvas.setCanais("Novos Canais");
		leanCanvasRepositorio.save(leanCanvas);

		assertNotNull(leanCanvasRepositorio.findByEquipe(equipe));
		
		LeanCanvas leanCanvasEditado =   leanCanvasRepositorio.findByEquipe(equipe).get(0);
		assertEquals(leanCanvasEditado.getProblema(),"Novo problema");
		
		
		assertEquals(leanCanvasEditado.getCanais(),"Novos Canais");
		
		leanCanvasRepositorio.delete(leanCanvasEditado);
	}

	// ------------------------------- Exceptions-------------------------------------------------
	
	@Test
	@Order(3)
	public void campoNullException() {
		leanCanvas.setMetricasChave(null);
		
		assertThrows(Exception.class, () -> {
			leanCanvasRepositorio.save(leanCanvas);
		});
	}
	
	@Test
	@Order(4)
	public void campoValorMinimoInvalido() {
		
		leanCanvas.setCanais("me");
		
		assertThrows(Exception.class, () -> {
			leanCanvasRepositorio.save(leanCanvas);
		});
	}
	
	@Test
	@Order(5)
	public void campoEtapaSolucaoCanvasNull() {
		leanCanvas.setEtapaSolucaoCanvas(null);
		assertThrows(Exception.class, () -> {
			leanCanvasRepositorio.save(leanCanvas);
		});
	}
	
	
	
	
}
