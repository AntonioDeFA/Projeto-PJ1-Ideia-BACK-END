package com.ideia.projetoideia;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.support.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.LeanCanvas;
import com.ideia.projetoideia.model.PapelUsuarioCompeticao;
import com.ideia.projetoideia.model.TipoEtapa;
import com.ideia.projetoideia.model.TipoPapelUsuario;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EquipeRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.LeanCanvasRepositorio;
import com.ideia.projetoideia.repository.PapelUsuarioCompeticaoRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.CompeticaoService;
import com.ideia.projetoideia.services.EquipeService;
import com.ideia.projetoideia.services.UsuarioService;

import javassist.NotFoundException;

@SpringBootApplication
public class ProjetoideiaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoideiaApplication.class, args);
	}

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CompeticaoService competicaoService;

	@Autowired
	private EtapaRepositorio etapaRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	private LeanCanvasRepositorio leanCanvasRepositorio;

	@Autowired
	private EquipeService equipeService;

	@Autowired
	private EquipeRepositorio equipeRepositorio;
	
	@Autowired
	private PapelUsuarioCompeticaoRepositorio papelUsuarioCompeticaoRepositorio;
	
	public void run(String... args) {

		usuarioService.inicializarPerfil();
		
//		Usuario usuario = new Usuario();
		
//		usuario.setNomeUsuario("Teste");
//		usuario.setEmail("Teste@gmail.com");
//		usuario.setSenha("asd123");
//		
//		usuarioRepositorio.save(usuario);
//		
		Competicao competicao = new Competicao();
		competicao.setNomeCompeticao("teste");
		competicao.setQntdMaximaMembrosPorEquipe(2);
		competicao.setQntdMinimaMembrosPorEquipe(2);
		competicao.setTempoMaximoVideoEmSeg(222f);
		competicao.setArquivoRegulamentoCompeticao(new File("local/"));
		
		competicaoRepositorio.save(competicao);
				
		Usuario usuario = usuarioRepositorio.findById(2).get();
		
		PapelUsuarioCompeticao papelUsuarioCompeticao= new PapelUsuarioCompeticao();
		papelUsuarioCompeticao.setTipoPapelUsuario(TipoPapelUsuario.AVALIADOR);
		papelUsuarioCompeticao.setUsuario(usuario);
		papelUsuarioCompeticao.setCompeticao(competicao);
		
		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);
//		papelUsuarioCompeticaoRepositorio.save(papelUsuarioCompeticao);

		//		Etapa etapa = new Etapa();

		//		etapa.setDataInicio(LocalDate.now());
		//		etapa.setDataTermino(LocalDate.now());
		//		etapa.setTipoEtapa(TipoEtapa.AQUECIMENTO);

		//		etapaRepositorio.save(etapa);
		
		//		etapa.setDataInicio(LocalDate.now());
		//		etapa.setDataTermino(LocalDate.now());
		//		etapa.setTipoEtapa(TipoEtapa.AQUECIMENTO);
		
		//		etapaRepositorio.save(etapa);


		//Competicao competicao = competicaoRepositorio.getById(5);
		//		competicao.setNomeCompeticao("teste");
		//competicao.setQntdMaximaMembrosPorEquipe(2);
		//competicao.setQntdMinimaMembrosPorEquipe(2);
		//competicao.setTempoMaximoVideoEmSeg(222f);
		//competicao.setArquivoRegulamentoCompeticao(new File("local/"));
				
		
//		List<Etapa> etapas = competicao.getEtapas();
//		etapas.add(etapaRepositorio.findById(2).get());
//		etapas.add(etapaRepositorio.findById(3).get());

//		competicao.setEtapas(etapas);
//		Equipe equipe = new Equipe();
//		equipe.setNomeEquipe("Teste2");
//		equipe.setToken("Teste2");
//		equipe.setDataInscricao(LocalDate.now());
		
//		equipeRepositorio.save(equipe);
		
//		List<Equipe> equipes= new ArrayList<Equipe>();

//		Equipe equipe = equipeRepositorio.findById(6).get();
//		equipe.setCompeticaoCadastrada(competicao);
//		equipeRepositorio.save(equipe);
//		equipes.add(equipe);
		
//		equipe = equipeRepositorio.findById(7).get();
//		equipe.setCompeticaoCadastrada(competicao);
//		equipeRepositorio.save(equipe);
//		equipes.add(equipe);

//		competicao.setEquipesCadatradas(equipes);
//		competicaoRepositorio.save(competicao);
		
//		System.out.println(competicao);

//		Etapa etapa = etapaRepositorio.findById(2).get();
//		etapa.setCompeticao(competicao);
//		etapaRepositorio.save(etapa);
//		etapa = etapaRepositorio.findById(3).get();
//		etapa.setCompeticao(competicao);
//		etapaRepositorio.save(etapa);
//		System.out.println(etapa);

		
		
//		competicaoRepositorio.save(competicao);
//	try {
//		equipeService.deletarEquipe(5);
//	} catch (NotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

//	LeanCanvas canvas  = new LeanCanvas();
//	
//	Equipe equipe = new Equipe();
//	
//	equipe.setNomeEquipe("Nome");
//	equipe.setDataInscricao(LocalDate.now());
//	equipe.setToken("aajiqasjidqijjisd");
//	
//	canvas.setEquipe(equipe);
//	
//	equipe.getCanvasDaEquipe().add(canvas);
//	
//	
//	
//	
//	try {
//		equipeService.criarEquipe(equipe);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

//	Usuario user = new Usuario();
//	user.setEmail("Gabryel@gmail.com");
//	user.setNomeUsuario("Gabryel");
//	user.setSenha("1");
//	
//	try {
//		usuarioService.criarUsuario(user);
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	System.out.println("TERMINEI");

//		Etapa etapa = new Etapa();
//		etapa.setDataInicio(LocalDate.now());
//		etapa.setDataTermino(LocalDate.now());
//		etapa.setTipoEtapa(TipoEtapa.INSCRICAO);
//		
//		Competicao comp = new Competicao();
//		comp.setNomeCompeticao("Comp");
//		comp.setArquivoRegulamentoCompeticao(new File("AAAAAAAA"));
//		comp.setQntdMaximaMembrosPorEquipe(1);
//		comp.setQntdMinimaMembrosPorEquipe(1);
//		comp.setTempoMaximoVideo(12f);
//		comp.setEtapa(etapa);
//		
//		try {
//			competicaoService.criarCompeticao(comp);
//
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		Competicao compp= competicaoRepositorio.findById(4).get();
//		compp.setOrganiador(usuarioRepositorio.findById(2).get());
//		competicaoRepositorio.save(compp);

//		System.out.println(competicaoRepositorio.findByOrganiador(usuarioRepositorio.findById(2).get()).get(0).getId());

//		System.out.println(competicaoRepositorio.findById(4).get().getOrganiador().getId());

//		competicaoService.consultarCompeticoesDoUsuario(2, 1);

//		int num=1;
//		Direction sortDirection = Sort.Direction.ASC;
//		Sort sort = Sort.by(sortDirection, "nome_competicao");
//		competicaoRepositorio.findByUsuario(3, PageRequest.of(--num, 6, sort));

	}

}
