package com.ideia.projetoideia.controller;

import java.util.List;

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

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.services.CompeticaoService;

@RestController
@RequestMapping("/ideia")
public class ControllerCompeticao {
	@Autowired
	CompeticaoService competicaoService;

	@PostMapping("/competicao")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Competição criada com sucesso")
	public void criarCompeticao(@Valid @RequestBody Competicao competicao, BindingResult result) throws Exception {
		if (!result.hasErrors()) {

			competicaoService.criarCompeticao(competicao);

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}
	
	@GetMapping("/competicao/{competicaoId}")
	public  Competicao encontrarCompeticao(@PathVariable("competicaoId") Integer competicaoId)throws Exception {
		try {
			 return  competicaoService.recuperarCompeticaoId(competicaoId);
			 
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/competicoes")
	public List<Competicao> consultarCompeticoes() {
		return competicaoService.consultarCompeticoes();
	}
	
	@GetMapping("/competicoes/usuario/{usuarioId}")
	public List<Competicao> consultarCompeticoesDoUsuario(@PathVariable("usuarioId") Integer usuarioId){
		
		return competicaoService.consultarCompeticoesDoUsuario(usuarioId);
	}


	@GetMapping("/competicoes/inscricoes")
	public Page<Competicao> consultarCompeticoesInscricao(@RequestParam("page") Integer pagina) {
		return competicaoService.consultarCompeticoesFaseInscricao(pagina);
	}

	@PutMapping("/competicao/update/{competicaoId}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Competição encontrada com sucesso")
	public void atualizarCompeticao(@Valid @RequestBody Competicao competicao, BindingResult result,
			@PathVariable("competicaoId") Integer competicaoId) {

		if (!result.hasErrors()) {

			try {

				competicaoService.atualizarCompeticao(competicaoId, competicao);
			} catch (Exception e) {

				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}

	@DeleteMapping("/competicao/delete/{competicaoId}")
	public void deletarCompeticao(@PathVariable("competicaoId") Integer competicaoId) {

		try {

			competicaoService.deletarCompeticaoPorId(competicaoId);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

}
