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
import com.ideia.projetoideia.repository.CompeticaoRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

import javassist.NotFoundException;

@Service
public class CompeticaoService {
	@Autowired
	CompeticaoRepositorio competicaoRepositorio;
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

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

		if (comp.size()>0)
			return comp;

		throw new NotFoundException("Competição não encontrada");
	}
	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeCompeticao");
		Page<Competicao> page = competicaoRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}
	
	public Page<Competicao> consultarCompeticoesFaseInscricao(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeCompeticao");
		Page<Competicao> page = competicaoRepositorio.findByInscricao(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public void deletarCompeticaoPorId(Integer id) throws NotFoundException {
		Competicao competicao = recuperarCompeticaoId(id);
		competicaoRepositorio.delete(competicao);
	}

	public void criarCompeticao(Competicao competicao) throws Exception {
		competicaoRepositorio.save(competicao);
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
}
