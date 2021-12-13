package com.ideia.projetoideia.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.services.CompeticaoService;

@RestController
@RequestMapping("/ideia")
public class ControllerCompeticao {
	@Autowired
	CompeticaoService competicaoService;
	
	
	@GetMapping("/competicoesFaseInscricoes/{pagina}")
	public Page<Competicao> consultarCompeticoes(@PathVariable("pagina") Integer pagina) {
		if(pagina ==null)
			return competicaoService.consultarCompeticoesFaseInscricao(1);
		
		return competicaoService.consultarCompeticoes(pagina);
	}
	
	@PutMapping("/atualizarCompeticao/{id}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Competição encontrada com sucesso")
	public void atualizarCompeticao(@Valid @RequestBody Competicao competicao,BindingResult result,@PathVariable("id") Integer id) {
	
		if (!result.hasErrors()) {

			try {

				competicaoService.atualizarCompeticao(id, competicao);
			} catch (Exception e) {

				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}
	
	@DeleteMapping("/deletarCompeticao/{id}")
	public void deletarCompeticao(@PathVariable("id") Integer id) { 
		
		try {

			competicaoService.deletarCompeticaoPorId(id);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
	}
	
}
