package com.ideia.projetoideia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.services.CompeticaoService;

@RestController
public class ControllerCompeticao {
	@Autowired
	CompeticaoService competicaoService;

	public List<Competicao> consultarCompeticoes() {
		return competicaoService.consultarCompeticoes();
	}

	public Page<Competicao> consultarCompeticoes(Integer numeroPagina) {
		return consultarCompeticoes(numeroPagina);
	}

	public Competicao consultarCompeticacaoPorId(Integer id) throws Exception {
		return consultarCompeticacaoPorId(id);
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		competicaoService.deletarUsuarioPorId(id);
	}

	public void criarCompeticao(Competicao competicao) throws Exception {
		competicaoService.criarCompeticao(competicao);
	}
}
