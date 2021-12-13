package com.ideia.projetoideia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.services.CompeticaoService;

@RestController
@RequestMapping("/ideia")
public class ControllerCompeticao {
	@Autowired
	CompeticaoService competicaoService;
	
	
	@GetMapping("/competicoesFaseInscricoes")
	public List<Competicao> consultarCompeticoes() {
		return competicaoService.consultarCompeticoes();
	}
	
	
	
	

	
}
