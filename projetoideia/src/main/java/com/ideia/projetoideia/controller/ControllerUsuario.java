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
import com.ideia.projetoideia.response.IdeiaResponseFile;
import com.ideia.projetoideia.services.UsuarioService;

import javassist.NotFoundException;
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
				return ResponseEntity.status(HttpStatus.CREATED).body(new IdeiaResponseFile("Criado com sucesso",
						HttpStatus.CREATED));
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new IdeiaResponseFile("Não foi possível cadastrar o usuário.", 
								e.getMessage(),
								HttpStatus.BAD_REQUEST));
			}

		}
	
		return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new IdeiaResponseFile("Não foi possível cadastrar o usuário.",
						result.toString(),
						HttpStatus.BAD_REQUEST));
		

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
				return ResponseEntity.status(HttpStatus.OK).body(new IdeiaResponseFile("Atualizado com sucesso"
						,HttpStatus.OK));
			} catch (Exception e) {

				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile("Erro ao atualizar usuário",
						e.getMessage(),
						HttpStatus.BAD_REQUEST));
			}

		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile("Erro ao atualizar usuário", 
				result.toString(),
				HttpStatus.BAD_REQUEST));

	}

	@DeleteMapping("/usuario/delete/{usuarioId}")
	public ResponseEntity<?> deletarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) throws Exception {

		try {

			usuarioService.deletarUsuarioPorId(usuarioId);
			return ResponseEntity.status(HttpStatus.OK).body(new IdeiaResponseFile("Usuario deletado com sucesso", HttpStatus.OK));

		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new IdeiaResponseFile("Usuário não encontrado ",
					e.getMessage(),
					HttpStatus.NOT_FOUND));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IdeiaResponseFile("ERRO",
					e.getMessage(),
					HttpStatus.BAD_REQUEST));
		}

	}

}
