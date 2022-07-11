package com.ideia.projetoideia.unitario;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.LeanCanvasRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.EquipeService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TesteLeanCanvas {

	@Autowired
	private LeanCanvasRepositorio leanCanvasRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	@Autowired
	private EquipeService equipeService;

	private LeanCanvas canvas;

	private Usuario usuario = new Usuario();

	private Competicao competicao = new Competicao();

	private List<UsuarioMembroComum> usuarios = new ArrayList<UsuarioMembroComum>();

	private Equipe equipe = new Equipe();

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

		canvas = new LeanCanvas();
		canvas.setEtapaSolucaoCanvas(EtapaArtefatoPitch.EM_ELABORACAO);
		canvas.setEquipe(equipe);
	}

//												Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCriarLeanCanvasCorreto() {

		Integer idCanvas = leanCanvasRepositorio.save(canvas).getId();

		LeanCanvas canvasRetorno = leanCanvasRepositorio.findById(idCanvas).get();

		assertNotNull(canvasRetorno);

		leanCanvasRepositorio.delete(canvasRetorno);
	}

	@Test
	public void testeAtualizarLeanCanvasCorreto() {

		Integer idCanvas = leanCanvasRepositorio.save(canvas).getId();

		LeanCanvas canvasRetorno = leanCanvasRepositorio.findById(idCanvas).get();

		canvasRetorno.setProblema("Problema");
		canvasRetorno.setSolucao("Solucao");
		canvasRetorno.setMetricasChave("MetricasChave");
		canvasRetorno.setPropostaValor("PropostaValor");
		canvasRetorno.setVantagemCompetitiva("VantagemCompetitiva");
		canvasRetorno.setCanais("Canais");
		canvasRetorno.setSegmentosDeClientes("SegmentosDeClientes");
		canvasRetorno.setEstruturaDeCusto("EstruturaDeCusto");
		canvasRetorno.setFontesDeReceita("FontesDeReceita");

		Integer idCanvasAtualizado = leanCanvasRepositorio.save(canvasRetorno).getId();

		LeanCanvas canvasAtualizado = leanCanvasRepositorio.findById(idCanvasAtualizado).get();

		assertNotNull(canvasAtualizado);

		leanCanvasRepositorio.delete(canvasAtualizado);

	}

	@Test
	public void testeDeletarLeanCanvasCorreto() {

		Integer idCanvas = leanCanvasRepositorio.save(canvas).getId();

		LeanCanvas canvasRetorno = leanCanvasRepositorio.findById(idCanvas).get();

		assertNotNull(canvasRetorno);

		leanCanvasRepositorio.delete(canvasRetorno);

		LeanCanvas canvasDeletada = leanCanvasRepositorio.findById(idCanvas).orElse(null);

		assertNull(canvasDeletada);

	}

//  														Exceptions 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeEnviarLeanCanvasSemConsultorException() {
		
		Integer idEquipe = equipe.getId();

		assertThrows(Exception.class, () -> {
			equipeService.enviarLeanCanvasParaConsultoria(idEquipe);
		});

	}
//
//	@Test
//	public void testeCadastroEquipeComTokenEmBrancoException() {
//		equipe.setToken(null);
//
//		assertThrows(DataIntegrityViolationException.class, () -> {
//			equipeRepositorio.save(equipe);
//		});
//
//	}
//
//	@Test
//	public void testeCadastroEquipeSemDataException() {
//		equipe.setDataInscricao(null);
//
//		assertThrows(DataIntegrityViolationException.class, () -> {
//			equipeRepositorio.save(equipe);
//		});
//
//	}

}