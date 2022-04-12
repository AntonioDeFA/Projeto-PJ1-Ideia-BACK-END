package com.ideia.projetoideia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Equipe;
import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.EquipeService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerEquipe {
	@Autowired
	EquipeService equipeService;

	@PostMapping("/equipe")
	public ResponseEntity<?> criarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result) {
		if (!result.hasErrors()) {
			try {
				equipeService.criarEquipe(equipe);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new IdeiaResponseFile("Criada com sucesso", HttpStatus.CREATED));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
						"Não foi possível criar a equipe", e.getMessage(), HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível criar a equipe", result.getFieldErrors(), HttpStatus.BAD_REQUEST));

	}

	@GetMapping("/equipes/{competicaoId}")
	public Page<Equipe> consultarEquipes(@RequestParam("page") Integer numeroPagina,
			@PathVariable("competicaoId") Integer competicaoId) {
		return equipeService.consultarEquipesDeUmaCompeticao(numeroPagina, competicaoId);
	}

	@PutMapping("/equipe/update/{equipeId}")
	public ResponseEntity<?> atualizarEquipe(@Valid @RequestBody Equipe equipe, BindingResult result,
			@PathVariable("equipeId") Integer equipeId) {
		if (!result.hasErrors()) {
			try {
				equipeService.atualizarEquipe(equipe, equipeId);
				return ResponseEntity.status(HttpStatus.OK)
						.body(new IdeiaResponseFile("Atualizado com sucesso", HttpStatus.OK));
			} catch (NotFoundException e) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.NOT_FOUND));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						new IdeiaResponseFile("Não foi possível atualizar", e.getMessage(), HttpStatus.BAD_REQUEST));
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
				"Não foi possível atualizar a equipe", result.getFieldErrors(), HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/equipe/delete/{equipeId}")
	public ResponseEntity<?> deletarEquipe(@PathVariable("equipeId") Integer equipeId) {
		try {
			equipeService.deletarEquipe(equipeId);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Deletado com sucesso", HttpStatus.OK));

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new IdeiaResponseFile("Não foi possível deletar", e.getMessage(), HttpStatus.NOT_FOUND));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new IdeiaResponseFile("Não foi possível deletar", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}

}
