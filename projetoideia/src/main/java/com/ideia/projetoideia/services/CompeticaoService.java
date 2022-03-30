package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.CompeticaoRepositorioCustom;
import com.ideia.projetoideia.repository.EtapaRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

import javassist.NotFoundException;

@Service
public class CompeticaoService {
	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	EtapaRepositorio etapaRepositorio;
	
	private final CompeticaoRepositorioCustom competicaoRepositorioCustom;
	
	public CompeticaoService(CompeticaoRepositorioCustom competicaoRepositorioCustom) {
		this.competicaoRepositorioCustom = competicaoRepositorioCustom;
	}

	public void criarCompeticao(Competicao competicao) throws Exception {

		Etapa etapa = competicao.getEtapa();
		etapa.setCompeticao(null);
		etapaRepositorio.save(etapa);
		competicaoRepositorio.save(competicao);

		etapa.setCompeticao(competicao);
		etapaRepositorio.save(etapa);
	}

	public List<Competicao> consultarCompeticoes() {
		return competicaoRepositorio.findAll();
	}

	public Competicao recuperarCompeticaoId(Integer id) throws NotFoundException {
		Optional<Competicao> comp = competicaoRepositorio.findById(id);

		if (comp.isPresent())
			return comp.get();

		throw new NotFoundException("Competição não encontrada");
	}

//	public List<Competicao> recuperarCompeticaoPorOrganizador(Integer id) throws NotFoundException {
//		List<Competicao> comp = competicaoRepositorio.findByOrganiador(usuarioRepositorio.findById(id).get());
//
//		if (comp.size() > 0)
//			return comp;
//
//		throw new NotFoundException("Competição não encontrada");
//	}

	public List<Competicao> consultarCompeticoesDoUsuario(Integer idUsuario) {

		List<Competicao> competicoesDoUsuario = new ArrayList<>();

		List<Competicao> competicoesComoLiderEquipe = competicaoRepositorio.findByEquipe(idUsuario);
		List<Competicao> competicoesComoOrganizador = competicaoRepositorio.findByOrganizador(idUsuario);

		competicoesDoUsuario.addAll(competicoesComoLiderEquipe);

		competicoesDoUsuario.addAll(competicoesComoOrganizador);

		return competicoesDoUsuario;
	}

	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_competicao");
		Page<Competicao> page = competicaoRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public List<Competicao> consultarCompeticoesFaseInscricao(String nomeCompeticao, Integer mes, Integer ano) {
//		Direction sortDirection = Sort.Direction.ASC;
//		Sort sort = Sort.by(sortDirection, "nome_competicao");
		
//		Page<Competicao> page = competicaoRepositorio.findByInscricao(PageRequest.of(--numeroPagina, 6, sort));
		
		return competicaoRepositorioCustom.findByTodasCompeticoesFaseInscricao(nomeCompeticao, mes, ano);
	}

//	public Page<Competicao> consultarCompeticoesPorNomeMesAno(String nomeCompeticao, Integer mes, Integer ano,
//			Integer numeroPagina) {
//		List<Competicao> listaCompeticao = competicaoRepositorio.findByNomeCompeticao(nomeCompeticao);
//		return consultarCompeticoesPorMesAno(nomeCompeticao, mes, ano, numeroPagina, listaCompeticao);
//
//	}

	public List<Competicao> consultarMinhasCompeticoes(String nomeCompeticao, Integer mes, Integer ano) {
		
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
		
		//Usuario usuario = usuarioRepositorio.findByEmail(autenticado.getName()).get();
		
		//return competicaoRepositorioCustom.findByCompeticoesDoUsuario(nomeCompeticao, mes, ano, usuario.getId() );
		return null;
	}

//	private Page<Competicao> consultarCompeticoesPorMesAno(String nomeCompeticao, Integer mes, Integer ano, Integer numeroPagina, List<Competicao> listaCompeticao) {
//		List<Competicao> listaAux = new ArrayList<Competicao>();
//		List<Competicao> listaFinal = new ArrayList<Competicao>();
//		if (ano != null) {
//			for (Competicao competicao : listaCompeticao) {
//				if (competicao.getEtapa().getDataInicio().getYear() >= ano) {
//					listaAux.add(competicao);
//					listaFinal.add(competicao);
//				}
//			}
//		}
//		if (mes != null) {
//			listaFinal.clear();
//			for (Competicao competicao : listaAux) {
//				if (competicao.getEtapa().getDataInicio().getMonthValue() >= mes) {
//					listaFinal.add(competicao);
//				}
//			}
//		}
//		Direction sortDirection = Sort.Direction.ASC;
//		Sort sort = Sort.by(sortDirection, "nome_competicao");
//		return new PageImpl<Competicao>(listaFinal, PageRequest.of(--numeroPagina, 6, sort), listaFinal.size());
//	}

	public void atualizarCompeticao(Integer id, Competicao competicaoTemp) throws NotFoundException {
		Competicao comp = recuperarCompeticaoId(id);

		comp.setNomeCompeticao(competicaoTemp.getNomeCompeticao());
		comp.setArquivoRegulamentoCompeticao(competicaoTemp.getArquivoRegulamentoCompeticao());
		comp.setEtapa(competicaoTemp.getEtapa());
		comp.setDominioCompeticao(competicaoTemp.getDominioCompeticao());
		comp.setQntdMaximaMembrosPorEquipe(competicaoTemp.getQntdMaximaMembrosPorEquipe());
		comp.setQntdMinimaMembrosPorEquipe(competicaoTemp.getQntdMinimaMembrosPorEquipe());
		comp.setTempoMaximoVideo(competicaoTemp.getTempoMaximoVideo());

		competicaoRepositorio.save(comp);

	}

	public void deletarCompeticaoPorId(Integer id) throws NotFoundException {
		Competicao competicao = recuperarCompeticaoId(id);
		competicaoRepositorio.delete(competicao);
	}

}
