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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.services.UsuarioService;

@RestController
@RequestMapping("/ideia")
public class ControllerUsuario {
	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/usuario/criar")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Usuário criado com sucesso")
	public void criarUsuario(@Valid @RequestBody Usuario user, BindingResult result) throws Exception {

		if (!result.hasErrors()) {

			usuarioService.criarUsuario(user);

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}

	@GetMapping("/usuario/all")
	public List<Usuario> consultarUsuarios() {
		return usuarioService.consultarUsuarios();
	}

	public Page<Usuario> consultarCompeticoes(Integer numeroPagina) {
		return usuarioService.consultarUsuarios(numeroPagina);
	}

	public Usuario consultarUsuarioPorEmail(String email) throws Exception {
		return usuarioService.consultarUsuarioPorEmail(email);
	}

	public Usuario consultarUsuarioPorId(Integer id) throws Exception {
		return usuarioService.consultarUsuarioPorId(id);
	}

	@PutMapping("/usuario/update/{usuarioId}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Usuario encontrado com sucesso")
	public void atualizarCompeticao(@Valid @RequestBody Usuario user, BindingResult result,
			@PathVariable("usuarioId") Integer usuarioId) {

		if (!result.hasErrors()) {

			try {

				usuarioService.atualizarUsuario(user, usuarioId);
			} catch (Exception e) {

				e.printStackTrace();
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
			}

		} else {

			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
		}
	}

	@DeleteMapping("/usuario/delete/{usuarioId}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Usuario deletado com sucesso")
	public void deletarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) throws Exception {

		try {

			usuarioService.deletarUsuarioPorId(usuarioId);

		} catch (Exception e) {
			e.printStackTrace();

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

}
