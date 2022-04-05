package com.ideia.projetoideia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.response.ResponseFile;
import com.ideia.projetoideia.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/ideia")
public class ControllerUsuario {
	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/usuario")
	public ResponseEntity<?> criarUsuario(@Valid @RequestBody Usuario user, BindingResult result)throws Exception {

		if (!result.hasErrors()) {
			try {
				usuarioService.criarUsuario(user);
				return ResponseEntity.status(HttpStatus.CREATED).body("Criado com Sucesso");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ResponseFile("Não foi possível cadastrar o usuário.", e.getMessage()));
			}

		}
	
		return ResponseEntity.badRequest().body(result.toString());
		

	}

	@GetMapping("/usuarios")
	public List<Usuario> consultarUsuarios() {
		return usuarioService.consultarUsuarios();
	}

	@PutMapping("/usuario/update/{usuarioId}")
	public ResponseEntity<?> atualizarUsuario(@Valid @RequestBody Usuario user, BindingResult result,
			@PathVariable("usuarioId") Integer usuarioId) {

		if (!result.hasErrors()) {

			try {
				usuarioService.atualizarUsuario(user, usuarioId);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseFile("Atualizado com Sucesso", "OK"));
			} catch (Exception e) {

				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseFile("Erro ao atualizar usuário", "ERROR"));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseFile(result.toString(), "ERROR"));

	}

	@DeleteMapping("/usuario/delete/{usuarioId}")
	public ResponseEntity<?> deletarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) throws Exception {

		try {

			usuarioService.deletarUsuarioPorId(usuarioId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseFile("Usuario deletado com sucesso", "OK"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseFile("Usuário Não Encontrado ", "ERROR"));
		}

	}

}
