package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Perfil;
import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.model.dto.UsuarioDto;
import com.ideia.projetoideia.model.dto.UsuarioPatchDto;
import com.ideia.projetoideia.repository.PerfilRepositorio;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.utils.EnviarEmail;

import javassist.NotFoundException;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	@Autowired
	PerfilRepositorio perfilRepositorio;

	@Autowired
	EnviarEmail enviarEmail;

	public void criarUsuario(Usuario user) throws Exception {
		if (usuarioRepositorio.findByEmail(user.getEmail()).isPresent()) {
			throw new Exception(
					"Não foi possível criar esta conta, pois já existe um usuário com este email cadastrado");
		}
		if (user.getSenha() == null) {
			throw new Exception("A senha não deve pode ser nula");
		}
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		List<Perfil> perfis = new ArrayList<>();
		Perfil perfil = new Perfil();
		perfil.setId(Perfil.PERFIL_USUARIO);
		perfis.add(perfil);

		user.setPerfis(perfis);

		usuarioRepositorio.save(user);
	}

	public List<Usuario> consultarUsuarios() {
		return usuarioRepositorio.findAll();
	}
	
	public UsuarioDto consultarUsuarioLogado() throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		UsuarioDto dto = new UsuarioDto(this.consultarUsuarioPorEmail(autenticado.getName()));
		return dto;
	}

	public Page<Usuario> consultarUsuarios(Integer numeroPagina) {
		Direction sortDirection = Sort.Direction.ASC;
		Sort sort = Sort.by(sortDirection, "nomeUsuario");
		Page<Usuario> page = usuarioRepositorio.findAll(PageRequest.of(--numeroPagina, 4, sort));
		return page;
	}

	public Usuario consultarUsuarioPorEmail(String email) throws Exception {
		Optional<Usuario> usuario = usuarioRepositorio.findByEmail(email);

		if (usuario.isPresent()) {
			return usuario.get();
		}
		throw new NotFoundException("Não foi encontrado um usuário com esse email");

	}

	public Usuario consultarUsuarioPorId(Integer id) throws Exception {
		Optional<Usuario> user = usuarioRepositorio.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new NotFoundException("Usuario não encontrado");
	}
	
	public void atualizarUsuario(UsuarioPatchDto user) throws Exception {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		Usuario usuario =  this.consultarUsuarioPorEmail(autenticado.getName());
		
		if(user.getSenha()!=null) {
			if(!user.getSenha().equals("")) {
				usuario.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
			}
		}
		if(user.getNomeUsuario()!=null) {
			if(!user.getNomeUsuario().equals("")) {
				usuario.setNomeUsuario(user.getNomeUsuario());
			}
		}
		
		usuarioRepositorio.save(usuario);
	}

	public void deletarUsuarioPorId(Integer id) throws Exception {
		Usuario usuario = consultarUsuarioPorId(id);
		usuarioRepositorio.delete(usuario);
	}

	public void resetarSenhaDoUsuario(String email) throws Exception {
		Usuario user = consultarUsuarioPorEmail(email);
		String novaSenha = "";
		novaSenha += System.currentTimeMillis();

		user.setSenha(new BCryptPasswordEncoder().encode(novaSenha));

		usuarioRepositorio.save(user);

		enviarEmail.enviarEmailDeResetDeSenha(user, novaSenha);
	}

	public void inicializarPerfil() {
		List<Perfil> perfis = perfilRepositorio.findAll();

		if (perfis.size() == 0) {
			Perfil perfil = new Perfil();
			perfil.setNomePerfil("USUARIO");
			perfil.setId(1);
			perfilRepositorio.save(perfil);
		}
	}

}
