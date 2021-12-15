package com.ideia.projetoideia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.services.EquipeService;

import javassist.NotFoundException;

@RestController
public class ControllerEquipe {
	@Autowired
	EquipeService equipeService;

	@PostMapping("/criarEquipe")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Equipe criada com sucesso")
	public void criarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result) throws Exception {
		if (!result.hasErrors()) {

			equipeService.criarEquipe(equipe);

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}

	@GetMapping("/consultarEquipes")
	public Page<Equipe> consultarEquipes(@RequestParam("page") Integer numeroPagina) {
		return equipeService.consultarEquipes(numeroPagina);
	}

	@PutMapping("/editarEquipe/{id}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Equipe encontrada com sucesso")
	public void atualizarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result, @PathVariable("id") Integer id)
			throws Exception {
		equipeService.atualizarEquipe(equipe, id);
	}

	@DeleteMapping("/deletarEquipe/{id}")
	public void deletarEquipe(@PathVariable("id") Integer id) throws NotFoundException {
		try {

			equipeService.deletarEquipe(id);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
