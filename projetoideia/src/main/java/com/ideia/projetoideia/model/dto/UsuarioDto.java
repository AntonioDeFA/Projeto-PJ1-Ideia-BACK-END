package com.ideia.projetoideia.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ideia.projetoideia.model.Usuario;

import lombok.Data;

@Data
public class UsuarioDto {
	@JsonInclude(value = Include.NON_NULL)
	private Integer id;
	private String email;
	private String nomeUsuario;

	public UsuarioDto() {
		super();
	}

	public UsuarioDto(Usuario usuario) {
		this.email = usuario.getEmail();
		this.id = usuario.getId();
		this.nomeUsuario = usuario.getNomeUsuario();
	}
	
}
