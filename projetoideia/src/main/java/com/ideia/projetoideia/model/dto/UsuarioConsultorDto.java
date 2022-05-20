package com.ideia.projetoideia.model.dto;

import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class UsuarioConsultorDto {
	
	private Integer id;
	
	private String nome_consultor;
	
	private String email_consultor;

	public UsuarioConsultorDto(Usuario usuario) {
		super();
		this.id = usuario.getId();
		this.nome_consultor = usuario.getNomeUsuario();
		this.email_consultor = usuario.getEmail();
	}
}
