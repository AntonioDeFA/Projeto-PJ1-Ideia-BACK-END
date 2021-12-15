package com.ideia.projetoideia;

import java.io.File;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.TipoEtapa;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.services.CompeticaoService;
import com.ideia.projetoideia.services.UsuarioService;

@SpringBootApplication
public class ProjetoideiaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoideiaApplication.class, args);
	}

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private CompeticaoService competicaoService;

	@Autowired
	private EtapaRepositorio etapaRepositorio;

	@Autowired
	private CompeticaoRepositorio competicaoRepositorio;

	public void run(String... args) {

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
//		

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
//		comp.setEtapa(null);
//		etapa.setCompeticao(comp);
//		
//		try {
//			competicaoService.criarCompeticao(comp);
//			etapaRepositorio.save(etapa);
//			
//			comp.setEtapa(etapa);
//			competicaoRepositorio.save(comp);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}
