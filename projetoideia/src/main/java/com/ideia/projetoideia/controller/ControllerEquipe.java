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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.services.EquipeService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerEquipe {
	@Autowired
	EquipeService equipeService;

	@PostMapping("/equipe")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Equipe criada com sucesso")
	public void criarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result) throws Exception {
		if (!result.hasErrors()) {

			equipeService.criarEquipe(equipe);

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}

	@GetMapping("/equipes/{competicaoId}")
	public Page<Equipe> consultarEquipes(@RequestParam("page") Integer numeroPagina,
			@PathVariable("competicaoId") Integer competicaoId) {
		return equipeService.consultarEquipesDeUmaCompeticao(numeroPagina, competicaoId);
	}

	@PutMapping("/equipe/update/{equipeId}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Equipe encontrada com sucesso")
	public void atualizarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result, @PathVariable("equipeId") Integer equipeId)
			throws Exception {
		equipeService.atualizarEquipe(equipe, equipeId);
	}

	@DeleteMapping("/equipe/delete/{equipeId}")
	public void deletarEquipe(@PathVariable("equipeId") Integer equipeId) throws NotFoundException {
		try {

			equipeService.deletarEquipe(equipeId);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}
