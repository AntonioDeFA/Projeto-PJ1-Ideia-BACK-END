package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Competicao;
import com.ideia.projetoideia.model.Perfil;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.PerfilRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	PerfilRepositorio perfilRepositorio;

	public List<Usuario> consultarUsuarios() {
		return usuarioRepositorio.findAll();
	}

	public Page<Usuario> consultarCompeticoes(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeUsuario");
		Page<Usuario> page = usuarioRepositorio.findAll(PageRequest.of(--numeroPagina, 4, sort));
		return page;
	}

	public Usuario consultarUsuarioPorEmail(String email) throws Exception {
		Usuario usuario = usuarioRepositorio.findByEmail(email);
		if (usuario == null) {
			throw new Exception("Usuário não existe");
		}
		return usuario;
	}

	public Usuario consultarUsuarioPorId(Integer id) throws Exception {
		Usuario usuario = usuarioRepositorio.findById(id).get();
		if (usuario == null) {
			throw new Exception("Usuário não existe");
		}
		return usuario;
	}

	public void criarUsuario(Usuario user) throws Exception {
		if (usuarioRepositorio.findByEmail(user.getEmail()) != null) {
			throw new Exception("Usuário já existe");
		}
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));

		this.inicializarPerfil();

		List<Perfil> perfis = new ArrayList<>();
		Perfil perfil = new Perfil();
		perfil.setId(Perfil.PERFIL_USUARIO);
		perfis.add(perfil);

		user.setPerfis(perfis);

		usuarioRepositorio.save(user);
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		Usuario usuario = consultarUsuarioPorId(id);
		usuarioRepositorio.delete(usuario);
	}

	private void inicializarPerfil() {
		List<Perfil> perfis = perfilRepositorio.findAll();

		if (perfis.size() == 0) {
			Perfil perfil = new Perfil();
			perfil.setNomePerfil("USUARIO");
			perfil.setId(1);
			perfilRepositorio.save(perfil);
		}
	}

}
