package com.ideia.projetoideia.model.dto;

import lombok.Data;


@Data
public class UsuarioPatchDto {
	
	private String nomeUsuario;
	
	private String senha;
	
	public UsuarioPatchDto() {}
	
	public UsuarioPatchDto(String nomeUsuario, String senha){
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
	}
}
