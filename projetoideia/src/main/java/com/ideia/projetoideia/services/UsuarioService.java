package com.ideia.projetoideia.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	public void criarUsuario(Usuario user) throws Exception {
		
		
		if(usuarioRepositorio.findByEmail(user.getEmail())!=null) {
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
	
	private void inicializarPerfil() {
		List<Perfil> perfis = perfilRepositorio.findAll();
		
		if(perfis.size()==0) {
			Perfil perfil = new Perfil();
			perfil.setNomePerfil("USUARIO");
			perfil.setId(1);
			perfilRepositorio.save(perfil);
		}
		
		
	}
	
}
