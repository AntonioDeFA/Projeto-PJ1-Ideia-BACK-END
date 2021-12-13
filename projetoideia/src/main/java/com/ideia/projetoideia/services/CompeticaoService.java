package com.ideia.projetoideia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.repository.CompeticaoRepositorio;

@Service
public class CompeticaoService {
	@Autowired
	CompeticaoRepositorio competicaoRepositorio;

	public List<Competicao> consultarCompeticoes() {
		return competicaoRepositorio.findAll();
	}
	

	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeCompeticao");
		Page<Competicao> page = competicaoRepositorio.findAll(PageRequest.of(--numeroPagina, 6, sort));
		return page;
	}

	public Competicao consultarCompeticacaoPorId(Integer id) throws Exception {
		Competicao competicao = competicaoRepositorio.findById(id).get();
		if (competicao == null) {
			throw new Exception("Competição não existe");
		}
		return competicao;
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		Competicao competicao = consultarCompeticacaoPorId(id);
		competicaoRepositorio.delete(competicao);
	}

	public void criarCompeticao(Competicao competicao) throws Exception {
		competicaoRepositorio.save(competicao);
	}
}
