package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.CompeticaoEtapaVigenteDto;
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

		List<Etapa> etapas = competicao.getEtapas();
		for (Etapa etapa : etapas) {
			etapa.setCompeticao(null);
			etapaRepositorio.save(etapa);
		}
		if (competicao.getQntdMaximaMembrosPorEquipe() < competicao.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		
		competicaoRepositorio.save(competicao);
		for (Etapa etapa : etapas) {
			etapa.setCompeticao(competicao);
			etapaRepositorio.save(etapa);
		}
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

	public List<CompeticaoEtapaVigenteDto> consultarCompeticoesFaseInscricao(String nomeCompeticao, Integer mes, Integer ano) {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(autenticado);
		
		Usuario usuario = usuarioRepositorio.findByEmail(autenticado.getName()).get();
		
		List<Competicao> competicoes =  competicaoRepositorioCustom.findByTodasCompeticoesFaseInscricao(nomeCompeticao, 
				mes, ano, usuario.getId());
		
		List<CompeticaoEtapaVigenteDto> competicoesDto = new ArrayList<>();
		
		for (Competicao competicao : competicoes) {
			CompeticaoEtapaVigenteDto competicaoEtapaVigenteDto = new CompeticaoEtapaVigenteDto(competicao);
			competicoesDto.add(competicaoEtapaVigenteDto);
		}
		return competicoesDto;
	}

	public List<Competicao> consultarMinhasCompeticoes(String nomeCompeticao, Integer mes, Integer ano) {

		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();

		Usuario usuario = usuarioRepositorio.findByEmail(autenticado.getName()).get();

		return competicaoRepositorioCustom.findByCompeticoesDoUsuario(nomeCompeticao, mes, ano, usuario.getId());
	}

	public void atualizarCompeticao(Integer id, Competicao competicaoTemp) throws Exception, NotFoundException {
		Competicao comp = recuperarCompeticaoId(id);
		if (comp.getQntdMaximaMembrosPorEquipe() < comp.getQntdMinimaMembrosPorEquipe()) {
			throw new Exception("Quantidade mínima de membros não pode ser maior que a quantidade máxima!");
		}
		comp.setNomeCompeticao(competicaoTemp.getNomeCompeticao());
		comp.setArquivoRegulamentoCompeticao(competicaoTemp.getArquivoRegulamentoCompeticao());
		comp.setEtapas(competicaoTemp.getEtapas());
		comp.setDominioCompeticao(competicaoTemp.getDominioCompeticao());
		comp.setQntdMaximaMembrosPorEquipe(competicaoTemp.getQntdMaximaMembrosPorEquipe());
		comp.setQntdMinimaMembrosPorEquipe(competicaoTemp.getQntdMinimaMembrosPorEquipe());
		comp.setTempoMaximoVideoEmSeg(competicaoTemp.getTempoMaximoVideoEmSeg());

		competicaoRepositorio.save(comp);

	}

	public void deletarCompeticaoPorId(Integer id) throws NotFoundException {
		Competicao competicao = recuperarCompeticaoId(id);
		competicaoRepositorio.delete(competicao);
	}

}
