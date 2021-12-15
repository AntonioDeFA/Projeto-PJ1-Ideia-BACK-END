package com.ideia.projetoideia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.UsuarioRepositorio;

@Service
public class AuthenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioRepositorio.findByEmail(username);

		if (user != null) {
			return user;
		}

		throw new UsernameNotFoundException("Usuario não Encontrado");
	}

}
