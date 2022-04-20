package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class UsuarioDto {
	
	private Integer id;
	private String email;
	private String nomeUsuario;
	


	
	
	public UsuarioDto(Usuario usuario) {
		this.email = usuario.getEmail();
		this.id = usuario.getId();
		this.nomeUsuario = usuario.getNomeUsuario();
	}
}
