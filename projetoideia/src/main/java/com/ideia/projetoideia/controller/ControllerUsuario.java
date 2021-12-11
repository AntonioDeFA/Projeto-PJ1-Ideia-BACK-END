package com.ideia.projetoideia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.services.UsuarioService;

@RestController
public class ControllerUsuario {
	@Autowired
	UsuarioService usuarioService;

	public List<Usuario> consultarUsuarios() {
		return usuarioService.consultarUsuarios();
	}

	public Page<Usuario> consultarCompeticoes(Integer numeroPagina) {
		return usuarioService.consultarCompeticoes(numeroPagina);
	}

	public Usuario consultarUsuarioPorEmail(String email) throws Exception {
		return usuarioService.consultarUsuarioPorEmail(email);
	}

	public Usuario consultarUsuarioPorId(Integer id) throws Exception {
		return usuarioService.consultarUsuarioPorId(id);
	}

	public void criarUsuario(Usuario user) throws Exception {
		usuarioService.criarUsuario(user);
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		usuarioService.deletarUsuarioPorId(id);
	}

}
