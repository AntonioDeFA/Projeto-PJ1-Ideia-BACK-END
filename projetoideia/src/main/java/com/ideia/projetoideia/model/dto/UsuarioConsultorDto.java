package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class UsuarioConsultorDto {
	
	private Integer id;
	
	private String nomeConsultor;
	
	private String emailConsultor;

	public UsuarioConsultorDto(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.nomeConsultor = usuario.getNomeUsuario();
		this.emailConsultor = usuario.getEmail();
	}
}
