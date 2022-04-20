package com.ideia.projetoideia.services.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ideia.projetoideia.model.Perfil;
import com.ideia.projetoideia.model.Usuario;

public class GeradorUserToken {
	
	public static Usuario gerarUsuarioToken(String nomeDaEquipe) {
				
		String nome = "User_"+System.currentTimeMillis();
		String email = nomeDaEquipe.replaceAll(" ", "").toLowerCase()+"@gmail.com";
		
		//criando lista de perfíss
		Perfil perfil = new Perfil();
		perfil.setNomePerfil("USUARIO_TOKEN");
		
		List<Perfil> perfils = new ArrayList<Perfil>();
		perfils.add(perfil);
		
		//criando usuário
		Usuario usuario = new Usuario();
		usuario.setId(0);
		usuario.setNomeUsuario(nome);
		usuario.setEmail(email);
		usuario.setSenha(new BCryptPasswordEncoder().encode(gerarSenha(nomeDaEquipe)));
		usuario.setPerfis(perfils);
		
		return usuario;
		
		
	}
		
	public static String gerarSenha(String nomeDaEquipe) {
		
		return "*"+nomeDaEquipe+"token!"; 
	}

}
