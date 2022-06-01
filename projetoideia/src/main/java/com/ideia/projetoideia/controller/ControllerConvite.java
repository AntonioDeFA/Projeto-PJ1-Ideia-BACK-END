package com.ideia.projetoideia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.dto.ConviteListaDto;
import com.ideia.projetoideia.model.dto.ConviteRespostaDto;
import com.ideia.projetoideia.model.dto.ConvitesquantidadeDto;
import com.ideia.projetoideia.model.enums.TipoConvite;
import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.ConviteService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/ideia")
public class ControllerConvite {

	@Autowired
	private ConviteService conviteService;

	@GetMapping("/convites-consultor")
	public List<ConviteListaDto> listarConvitesConsultor() {
		try {
			return conviteService.listarConvites(TipoConvite.CONSULTOR);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/convites-avaliador")
	public List<ConviteListaDto> listarConvitesAvaliador() {
		try {
			return conviteService.listarConvites(TipoConvite.AVALIADOR);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/convites/{tipoConvite}/quantidade")
	public ConvitesquantidadeDto listarQuantidadeConvites(@PathVariable("tipoConvite") String tipoConvite)
			throws Exception {
		try {
			return conviteService.listarQuantidadeConvites(tipoConvite);
		} catch (NotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/responder-convite")
	public ResponseEntity<?> responderConvite(@RequestBody ConviteRespostaDto conviteRespostaDto) {
		try {
			conviteService.responderConvite(conviteRespostaDto);
			return ResponseEntity.status(HttpStatus.OK)
					.body(new IdeiaResponseFile("Convite respondido", HttpStatus.OK));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile(
					"Não foi possível responder convite", e.getMessage(), HttpStatus.BAD_REQUEST));
		}
	}
}
