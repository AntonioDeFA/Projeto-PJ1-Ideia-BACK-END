package com.ideia.projetoideia.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ideia.projetoideia.model.Usuario;
import com.ideia.projetoideia.repository.UsuarioRepositorio;
import com.ideia.projetoideia.services.utils.GeradorUserToken;

@Service
public class AuthenticacaoService implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(username.contains("token")) {
			//ao enviar o nome da equipe retiramos a parte "token@gmail.com"
			return GeradorUserToken.gerarUsuarioToken(username.replace("token@gmail.com", ""));
		}else {
			Optional<Usuario> user = usuarioRepositorio.findByEmail(username);
			if (user.isPresent()) {
				return user.get();
			}

			throw new UsernameNotFoundException("Usuario não Encontrado");	
		}
	}

}
