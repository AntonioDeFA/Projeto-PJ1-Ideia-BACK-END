package utils;

import org.junit.jupiter.api.Disabled;
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
import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.QuestaoAvaliativa;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.enums.TipoPapelUsuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.QuestaoAvaliativaRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.UsuarioService;
import com.ideia.projetoideia.services.utils.ConversorDeArquivos;

import static utils.Utils.*;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	public EtapaRepositorio etapaRepositorio;

	@Autowired
	public CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	public PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;

	@Autowired
	public QuestaoAvaliativaRepositorio questaoAvaliativaRepositorio;

	@Test
	public void inicializarUsuarioTestes() throws Exception {
		Usuario usuario = new Usuario();
		usuario.setNomeUsuario(NOME_USUARIO);
		usuario.setEmail(EMAIL);
		usuario.setSenha(SENHA);

		usuarioService.criarUsuario(usuario);

	}

	@Test
	public void inicializarConsultoresEAvaliadoresEOrganizador() throws Exception {
		Usuario usuario1 = new Usuario();
		usuario1.setNomeUsuario("Consultor");
		usuario1.setEmail("consultor@gmail.com");
		usuario1.setSenha(SENHA);

		Usuario usuario2 = new Usuario();
		usuario2.setNomeUsuario("Avaliador");
		usuario2.setEmail("avaliador@gmail.com");
		usuario2.setSenha(SENHA);
		
		
		Usuario usuario3 = new Usuario();
		usuario3.setNomeUsuario("Organizador");
		usuario3.setEmail("organizador@gmail.com");
		usuario3.setSenha(SENHA);

		usuarioService.criarUsuario(usuario1);
		usuarioService.criarUsuario(usuario2);
		usuarioService.criarUsuario(usuario3);
	}

	@Test
	@Disabled
	public void inicializarCriarCompeticao() throws Exception {

		Usuario usuario = new Usuario();

		usuario.setEmail("organizador@gmail.com");
		usuario.setSenha("1");
		usuario.setNomeUsuario("Organizador");
		usuarioService.criarUsuario(usuario);

		Competicao competicao = CompeticaoUtils.criarCompeticao();

		competicao.setOrganizador(usuario);

		List<Etapa> etapas = new ArrayList<Etapa>();

		for (Etapa etapa : etapas) {
			etapa.setCompeticao(null);
			etapaRepositorio.save(etapa);
		}
		if (competicao.getQntdMaximaMembrosPorEquipe() < competicao.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		competicao.setOrganizador(usuario);
		Integer idCompeticao = competicaoRepositorio.save(competicao).getId();
		// aqui tem que converter a string base64 em um arquivo
		ConversorDeArquivos.converterStringParaArquivo(competicao.getArquivoRegulamentoCompeticao(), idCompeticao);

		for (Etapa etapa : etapas) {
			etapa.setCompeticao(competicao);
			etapaRepositorio.save(etapa);
		}
		PapelUsuarioCompeticao papelUsuarioCompeticao = new PapelUsuarioCompeticao();
		papelUsuarioCompeticao.setTipoPapelUsuario(TipoPapelUsuario.ORGANIZADOR);
		papelUsuarioCompeticao.setUsuario(usuario);
		papelUsuarioCompeticao.setCompeticaoCadastrada(competicao);

		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);

		for (QuestaoAvaliativa questao : competicao.getQuestoesAvaliativas()) {
			questao.setCompeticaoCadastrada(competicao);
			questaoAvaliativaRepositorio.save(questao);

		}

	}

}
