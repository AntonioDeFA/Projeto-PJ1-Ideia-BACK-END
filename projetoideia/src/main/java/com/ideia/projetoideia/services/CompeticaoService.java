package com.ideia.projetoideia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Etapa;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
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

	public List<Competicao> recuperarCompeticaoPorOrganizador(Integer id) throws NotFoundException {
		List<Competicao> comp = competicaoRepositorio.findByOrganiador(usuarioRepositorio.findById(id).get());

		if (comp.size() > 0)
			return comp;

		throw new NotFoundException("Competição não encontrada");
	}

	public Page<Competicao> consultarCompeticoesDoUsuario(Integer idUsuario, Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_competicao");
		Page<Competicao> page = competicaoRepositorio.findByOrganiador(usuarioRepositorio.findById(idUsuario).get(),
				PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_competicao");
		Page<Competicao> page = competicaoRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public Page<Competicao> consultarCompeticoesFaseInscricao(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nome_competicao");
		Page<Competicao> page = competicaoRepositorio.findByInscricao(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

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
