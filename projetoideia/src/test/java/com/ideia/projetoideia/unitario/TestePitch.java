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
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Pitch;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.UsuarioMembroComum;
import com.ideia.projetoideia.model.enums.EtapaArtefatoPitch;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.PitchRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
@SpringBootTest(classes = ProjetoideiaApplication.class)
public class TestePitch {

	@Autowired
	private PitchRepositorio pitchRepositorio;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	private EquipeRepositorio equipeRepositorio;

	private Pitch pitch;

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

		pitch = new Pitch();
		pitch.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.AVALIADO_AVALIADOR);
		pitch.setTitulo("Titulo");
		pitch.setDescricao("Descrição");
		pitch.setDataCriacao(LocalDateTime.now());
		pitch.setPitchDeck("Pitch deck");
		pitch.setEquipe(equipe);
	}

//												Caminho Feliz 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeCriarPitchCorreto() {

		Integer idPitch = pitchRepositorio.save(pitch).getId();

		Pitch pitchRetorno = pitchRepositorio.findById(idPitch).get();

		assertNotNull(pitchRetorno);

		pitchRepositorio.delete(pitchRetorno);
	}

	@Test
	public void testeAtualizarPitchCorreto() {

		Integer idPitch = pitchRepositorio.save(pitch).getId();

		Pitch pitchRetorno = pitchRepositorio.findById(idPitch).get();

		pitch.setEtapaAvaliacaoVideo(EtapaArtefatoPitch.AVALIADO_AVALIADOR);
		pitch.setTitulo("Titulo");
		pitch.setDescricao("Descrição");
		pitch.setEquipe(equipe);
		pitch.setDataCriacao(LocalDateTime.now());
		pitch.setPitchDeck("Pitch deck");

		Integer idPitchAtualizado = pitchRepositorio.save(pitchRetorno).getId();

		Pitch pitchAtualizado = pitchRepositorio.findById(idPitchAtualizado).get();

		assertNotNull(idPitchAtualizado);

		pitchRepositorio.delete(pitchAtualizado);

	}

	@Test
	public void testeDeletarPitchCorreto() {

		Integer idPitch = pitchRepositorio.save(pitch).getId();

		Pitch pitchRetorno = pitchRepositorio.findById(idPitch).get();

		assertNotNull(pitchRetorno);

		pitchRepositorio.delete(pitchRetorno);

		Pitch pitchDeletada = pitchRepositorio.findById(idPitch).orElse(null);

		assertNull(pitchDeletada);

	}

//										Exceptions 	
//---------------------------------------------------------------------------------------------------------------------------

	@Test
	public void testeTituloNuloErrado() {

		pitch.setTitulo(null);

		assertThrows(Exception.class, () -> {
			pitchRepositorio.save(pitch);
		});
	}

	@Test
	public void testeDescricaoNuloErrado() {

		pitch.setDescricao(null);

		assertThrows(Exception.class, () -> {
			pitchRepositorio.save(pitch);
		});

	}
	
	@Test
	public void testeDataErrada() {

		assertThrows(Exception.class, () -> {
			pitch.setDataCriacao(null);
			pitchRepositorio.save(pitch);
		});

	}
}
